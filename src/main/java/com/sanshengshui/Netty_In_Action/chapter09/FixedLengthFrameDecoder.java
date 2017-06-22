package com.sanshengshui.Netty_In_Action.chapter09;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @ClassName FixedLengthFrameDecoder
 * @author 穆书伟
 * @description 这个特定的解码器将产生固定为3字节大小的帧。
 * @date 2017/6/22 8:06
 */

//扩展ByteToMessageDecoder以处理入站字节，并将它们解码为消息
public class FixedLengthFrameDecoder extends ByteToMessageDecoder{
    private final int frameLength;

    //指定要生成的帧的长度
    public FixedLengthFrameDecoder(int frameLength) {
        if (frameLength <= 0) {
            throw new IllegalArgumentException(
                    "frameLength must be a positive integer: " + frameLength);
        }
        this.frameLength = frameLength;
    }
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        //检查是否有足够的字节可以被读取，以生成下一个帧
        while (byteBuf.readableBytes() >= frameLength){
            //从ByteBuf中读取一个新帧
            ByteBuf buf = byteBuf.readBytes(frameLength);
            //将该帧添加到已被解码的消息列表中
            list.add(buf);
        }

    }
}
