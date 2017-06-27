package com.sanshengshui.Netty_In_Action.chapter11;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @ClassName LengthBasedInitializer
 * @author 穆书伟
 * @description 解码器基于长度的协议
 * @date 2017/6/27 12:54
 */
public class LengthBasedInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(
                //使用LengthFieldBasedFrameDecoder解码将帧长度编码到帧起始的前8个字节中的消息
                new LengthFieldBasedFrameDecoder(64*1024,0,8)
        );
        //添加FrameHandler以处理每个帧
        pipeline.addLast(new FrameHandler());
    }

    public static final class FrameHandler
            extends SimpleChannelInboundHandler<ByteBuf>{
        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
            //Do something with the frame
            //处理帧的数据
        }
    }
}
