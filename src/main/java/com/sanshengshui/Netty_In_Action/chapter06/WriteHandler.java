package com.sanshengshui.Netty_In_Action.chapter06;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author 穆书伟
 * @description Netty in action 第六章 ChannelHandler和ChannelHandlerContext的高级用法
 * @date 2017/6/5 9:35
 */
public class WriteHandler extends ChannelHandlerAdapter{
    private ChannelHandlerContext ctx;
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        //存储到 ChannelHandlerContext的引用以供稍后使用
        this.ctx = ctx;
    }
    public void send(String msg) {
        //使用之前存储的到 ChannelHandlerContext的引用来发送消息
        ctx.writeAndFlush(msg);
    }

}
