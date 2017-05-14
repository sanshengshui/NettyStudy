package com.sanshengshui.Netty_In_Action.chapter02;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @author jamesmsw
 * @description Netty In action
 * @date 2017/5/15 00:39
 */
@ChannelHandler.Sharable
//标示一个ChannelHandler可以被多个Channel安全地共享
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in=(ByteBuf)msg;
        System.out.println(
                "Server received: " + in.toString(CharsetUtil.UTF_8)
        );
        //将消息记录到控制台
        ctx.write(in);
        //将接收到的消息写给发送者,而不冲刷出站消息
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
                .addListener(ChannelFutureListener.CLOSE);
    }
    //将未决消息冲刷到远程节点,并且关闭远程节点

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        //打印远程栈更踪
        ctx.close();
        //关闭Channel
    }
}
