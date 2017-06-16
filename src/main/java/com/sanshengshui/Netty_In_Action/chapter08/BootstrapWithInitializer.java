package com.sanshengshui.Netty_In_Action.chapter08;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;

import java.net.InetSocketAddress;

/**
 * @ClassName BootstrapWithInitializer
 * @author 穆书伟
 * @description 引导和使用ChannelInitializer
 * @date 2017/6/16 14:16
 */
public class BootstrapWithInitializer {
    /**
     * 代码清单 8-6 引导和使用ChannelInitializer
     */
    public void bootstrap() throws InterruptedException{
        //创建ServerBootstrap 以创建和绑定新的Channel
        ServerBootstrap bootstrap = new ServerBootstrap();
        //设置EventLoopGroup，其将提供用以处理Channel事件的EventLoop
        bootstrap.group(new NioEventLoopGroup(),new NioEventLoopGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializerImpl());
        //绑定地址
        ChannelFuture future = bootstrap.bind(new InetSocketAddress(8080));
        future.sync();
    }

    final class ChannelInitializerImpl extends ChannelInitializer<Channel>{
        @Override
        protected void initChannel(Channel channel) throws Exception {
            ChannelPipeline pipeline =channel.pipeline();
            pipeline.addLast(new HttpClientCodec());
            pipeline.addLast(new HttpObjectAggregator(Integer.MAX_VALUE));
        }
    }
}
