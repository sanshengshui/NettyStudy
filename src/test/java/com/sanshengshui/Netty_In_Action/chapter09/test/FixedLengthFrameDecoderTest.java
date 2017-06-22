package com.sanshengshui.Netty_In_Action.chapter09.test;


import com.sanshengshui.Netty_In_Action.chapter09.FixedLengthFrameDecoder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @ClassName FixedLengthFrameDecoderTest
 * @author 穆书伟
 * @description 测试FixedLengthFrameDecoder
 * @date 2017/6/22 8:17
 */
public class FixedLengthFrameDecoderTest {

    //使用了注解@Test,因此JUnit将会执行该方法
    @Test
    public void testFramesDecoded(){
        //创建一个ByteBuf,并存储9字节
        ByteBuf buf = Unpooled.buffer();
        for (int i = 0;i < 9; i++){
        buf.writeByte(i);
        }
        ByteBuf input = buf.duplicate();
        //创建一个EmbeddedChannel,并添加一个FixedLengthFrameDecoder,其将以3字节的帧长度被测试
        EmbeddedChannel channel = new EmbeddedChannel(new FixedLengthFrameDecoder(3));
        //write bytes
        //将数据写入EmbeddedChannel
        assertTrue(channel.writeInbound(input.retain()));
        //标记Channel为已完成状态
        assertTrue(channel.finish());

        //read message
        //读取所生成的消息，并且验证是否有3帧(切片),其中没帧(切片)都为3字节
        ByteBuf read = (ByteBuf) channel.readInbound();
        assertEquals(buf.readSlice(3),read);
        read.release();

        read = (ByteBuf) channel.readInbound();
        assertEquals(buf.readSlice(3),read);
        read.release();

        read = (ByteBuf) channel.readInbound();
        assertEquals(buf.readSlice(3),read);
        read.release();

        assertNull(channel.readInbound());
        buf.release();


    }

    @Test
    //第二个测试方法:testFramesDecoded2()
    public void testFramesDecoded2(){
        ByteBuf buf = Unpooled.buffer();
        for (int i =0;i<9;i++){
            buf.writeByte(i);
        }
        ByteBuf input = buf.duplicate();

        EmbeddedChannel channel = new EmbeddedChannel(
                new FixedLengthFrameDecoder(3)
        );
        //返回false,因为没有一个完整的可供读取的帧
        assertFalse(channel.writeInbound(input.readBytes(2)));
        assertTrue(channel.writeInbound(input.readBytes(7)));

        assertTrue(channel.finish());
        ByteBuf read = (ByteBuf) channel.readInbound();
        assertEquals(buf.readSlice(3), read);
        read.release();

        read = (ByteBuf) channel.readInbound();
        assertEquals(buf.readSlice(3), read);
        read.release();

        read = (ByteBuf) channel.readInbound();
        assertEquals(buf.readSlice(3), read);
        read.release();

        assertNull(channel.readInbound());
        buf.release();

    }

}
