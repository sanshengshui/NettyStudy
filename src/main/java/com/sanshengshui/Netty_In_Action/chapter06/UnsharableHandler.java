package com.sanshengshui.Netty_In_Action.chapter06;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author 穆书伟
 * @dscription Netty in action 第六章 不正确可共享的ChannelHandler
 * @date 2017/6/5 10:51
 */
public class UnsharableHandler extends ChannelInboundHandlerAdapter {
    private int count;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        count++;
        System.out.println("channelRead(...) called the"+ count +" time");
        ctx.fireChannelRead(msg);
    }
}
