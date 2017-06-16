package com.sanshengshui.Netty_In_Action.chapter08;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * @ClassName BootstrapServer
 * @author 穆书伟
 * @description 引导服务器
 * @date 2017/6/16
 */
public class BootstrapServer {
    /**
     * 代码清单 8-4 引导服务器
     */
    public void bootstrap(){
        NioEventLoopGroup group = new NioEventLoopGroup();
        //创建Server Bootstrap
        ServerBootstrap bootstrap = new ServerBootstrap();
        //设置EventLoopGroup,其提供了用于处理Channel事件的EventLoop
        bootstrap.group(group)
                //指定要使用的Channel实现
                .channel(NioServerSocketChannel.class)
                //设置用于处理已被接受的子Channel的I/O及数据的ChannelInboundHandler
                .childHandler(new SimpleChannelInboundHandler<ByteBuf>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
                        System.out.println("Received date");
                    }
                });
        //通过配置好的ServerBootStrap的实例绑定该Channel
        ChannelFuture future = bootstrap.bind(new InetSocketAddress(8080));
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if(channelFuture.isSuccess()){
                    System.out.println("Server bound");
                }else {
                    System.err.println("Bind attempt failed");
                    channelFuture.cause().printStackTrace();
                }
            }
        });

    }
}
