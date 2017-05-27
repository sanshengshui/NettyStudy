package com.sanshengshui.Netty_In_Action.chapter02.chapter10;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.TooLongFrameException;

import java.util.List;

/**
 * @author 穆书伟
 * @description 由于Netty是一个异步框架，所以需要在字节可以编码之前在内存中缓冲它们。因此，不能让解码器缓冲大量的数据
 * 以至于耗尽可用的内存。为了解除这个常见的顾虑，Netty提供了TooLongFrameException类,其将由解码器在帧超出指定的大小限制时抛出。
 * @date 2017/5/27 17:46
 */
public class SafeByteToMessageDecoder extends ByteToMessageDecoder {
    private static final int MAX_FRAME_SIZE =1024;
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int readable = byteBuf.readableBytes();
        if(readable > MAX_FRAME_SIZE){
            byteBuf.skipBytes(readable);
            throw new TooLongFrameException("Frame too big!");
        }

    }
}
