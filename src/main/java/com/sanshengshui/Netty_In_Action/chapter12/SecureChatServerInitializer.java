package com.sanshengshui.Netty_In_Action.chapter12;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;

/**
 * @ClassName SecureChatServerInitializer
 * @description 为ChannelPipeline添加加密
 * @author 穆书伟
 * @date 2017/7/3 17:29:31
 */
public class SecureChatServerInitializer extends ChatServerInitializer {
    private final SslContext context;

    public SecureChatServerInitializer(ChannelGroup group,
                                       SslContext context) {
        super(group);
        this.context = context;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        //调用父类的 initChannel() 方法
        super.initChannel(ch);
        SSLEngine engine = context.newEngine(ch.alloc());
        engine.setUseClientMode(false);
        //将 SslHandler 添加到 ChannelPipeline 中
        ch.pipeline().addFirst(new SslHandler(engine));
    }

}
