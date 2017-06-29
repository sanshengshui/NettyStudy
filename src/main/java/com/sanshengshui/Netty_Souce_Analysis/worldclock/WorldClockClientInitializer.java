package com.sanshengshui.Netty_Souce_Analysis.worldclock;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.ssl.SslContext;

/**
 * @ClassName WorldClockClientInitializer
 * @author 穆书伟
 * @date 2017/6/28 15:28:51
 */
public class WorldClockClientInitializer  extends ChannelInitializer<Channel>{
    private final SslContext sslCtx;

    public WorldClockClientInitializer(SslContext sslCtx){
        this.sslCtx = sslCtx;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {

    }
}
