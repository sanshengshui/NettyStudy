package com.sanshengshui.Netty_In_Action.chapter06;

import io.netty.channel.*;

/**
 * @author 穆书伟
 * @description 添加ChannelFutureListener到ChannelPromise
 * @date 2017/6/5 11:32
 */

public class OutboundExceptionHandler extends ChannelOutboundHandlerAdapter {
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        promise.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (!channelFuture.isSuccess()){
                    channelFuture.cause().printStackTrace();
                    channelFuture.channel().close();
                }
            }
        });
    }
}
