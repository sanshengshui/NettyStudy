package com.sanshengshui.Netty_In_Action.chapter11;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;

/**
 * @ClassName SslChannelInitializer
 * @author 穆书伟
 * @description 添加了SSL/TLS支持
 * @date 2017/6/23 10:44
 */
public class SslChannelInitializer  extends ChannelInitializer<Channel>{
    private final SslContext context;
    private final boolean startTls;

    public SslChannelInitializer(SslContext context,boolean startTls){
        this.context = context;
        this.startTls = startTls;
    }
    //传入要使用的的SslContext
    //如果设置为true,第一个写入的消息将不会被加密(客户端应该设置为true)

    @Override
    protected void initChannel(Channel channel) throws Exception {
        //对于每个SslHandler实例，都使用Channel的ByteBufAllocator从SslContext获取一个新的SslEngine
        SSLEngine engine =context.newEngine(channel.alloc());
        //将SslHandler作为第一个ChannelHandler添加到ChannelPieline中
        channel.pipeline().addFirst("ssl",new SslHandler(engine,startTls));
    }
}
