package com.sanshengshui.Netty_In_Action.chapter09;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.TooLongFrameException;

import java.util.List;

/**
 * @ClassName FrameChunkDecoder
 * @author 穆书伟
 * @description 以将入站字节解码为消息
 * @date 2017/6/22 11:05
 */
//扩展ByteToMessageDecoder以将入站字节解码为消息
public class FrameChunkDecoder extends ByteToMessageDecoder {
    private final  int  maxFrameSize;

    //指定将要产生的帧的最大允许大小
    public FrameChunkDecoder(int maxFrameSize){
        this.maxFrameSize = maxFrameSize;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int readableBytes = byteBuf.readableBytes();
        if(readableBytes > maxFrameSize){
            //discard the bytes
            //如果该帧太大，则丢弃它并抛出一个TooLongFrameException...
            byteBuf.clear();
            throw new TooLongFrameException();
        }
        //...,从ByteBuf中读取一个新的帧
        ByteBuf buf = byteBuf.readBytes(readableBytes);
        list.add(buf);
    }
}
