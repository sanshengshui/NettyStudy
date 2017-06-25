package com.sanshengshui.Netty_In_Action.chapter11;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName IdleStateHandlerInitializer
 * @author 穆书伟
 * @description 发送心跳
 * @date 2017/6/25 18:45
 */
public class IdleStateHandlerInitializer  extends ChannelInitializer<Channel>{
    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(
                //(1)IdleStateHandler将在被触发时发送一个IdleStateEvent事件
                new IdleStateHandler(0,0,60, TimeUnit.SECONDS)
        );
        //将一个HeartbeatHandler添加到ChannelPipeline中
        pipeline.addLast(new HeartbeatHandler());
    }

    //实现userEventTriggered()方法以发送消息
    public static final class HeartbeatHandler extends ChannelInboundHandlerAdapter{
        //发送到远程节点的心跳消息
        private static final ByteBuf HEARTBEAT_SEQUENCE =
                Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("HEARTBEAT", CharsetUtil.ISO_8859_1));

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            //发送心跳消息，并在发送失败时关闭该连接
            if(evt instanceof IdleStateEvent){
                ctx.writeAndFlush(HEARTBEAT_SEQUENCE.duplicate())
                        .addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            }else {
                //不是IdleStateEvent事件，所以将它传递给下一个ChannelInboundHandler
                super.userEventTriggered(ctx,evt);
            }
        }
    }
}
