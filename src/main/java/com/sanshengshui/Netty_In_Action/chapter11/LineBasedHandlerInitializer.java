package com.sanshengshui.Netty_In_Action.chapter11;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.codec.LineBasedFrameDecoder;

/**
 * @ClassName LineBasedHandlerInitializer
 * @author 穆书伟
 * @description 处理由行尾符分割的的帧
 * @date 2017/6/27 10:58
 */
public class LineBasedHandlerInitializer extends ChannelInitializer<Channel>{
    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline =channel.pipeline();
        //该LineBasedFrameDecoder将提取的帧转发给下一个ChannelInboundHandler
        pipeline.addLast(new LineBasedFrameDecoder(64*1024));
        //添加FrameHandler以接受帧
        pipeline.addLast(new FrameHandler());
    }
    public static final class FrameHandler
                     extends SimpleChannelInboundHandler<ByteBuf>{
        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
            //Do something with the data extracted from the frame
        }
    }
}
