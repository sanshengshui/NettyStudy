package com.sanshengshui.Netty_In_Action.chapter06;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author 穆书伟
 * @dscription Netty in action 第六章 正确可共享的ChannelHandler
 * @date 2017/6/5 10:48
 */
public class SharableHandler extends ChannelInboundHandlerAdapter{
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
       System.out.println("Channel read message: "+msg);
       ctx.fireChannelRead(msg);
    }
}
