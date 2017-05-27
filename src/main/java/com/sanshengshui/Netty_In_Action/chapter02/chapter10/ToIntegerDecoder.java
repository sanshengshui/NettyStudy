package com.sanshengshui.Netty_In_Action.chapter02.chapter10;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author 穆书伟
 * @description 假设你接受了一个包含简单int的字节流，每个int都需要被单独处理。
 * 在这种情况下，你需要从入站ByteBuf中读取每个int，并将它传递给ChannelPipeline中的下一个
 * ChannelInboundHandler。为了解码这个字节流，你要扩展ByteToMessageDecoder类。
 * @date 2017/5/27 17:21
 */
public class ToIntegerDecoder  extends ByteToMessageDecoder{
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if(byteBuf.readableBytes() >=4){
            //检查是否至少有4字节可读(一个int的字节长度)
            list.add(byteBuf.readInt());
            //从入站ByteBuf中读取一个int,并将其添加到解码消息的List中
        }
    }
}
