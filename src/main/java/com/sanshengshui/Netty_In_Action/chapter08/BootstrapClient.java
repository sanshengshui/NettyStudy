package com.sanshengshui.Netty_In_Action.chapter08;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * @ClassName BootstrapClient
 * @author 穆书伟
 * @description 引导一个客户端
 * @date 2017/6/15 8:58
 */


public class BootstrapClient {
    public static void main(String args[]){
        BootstrapClient client = new BootstrapClient();
        client.bootstrap();
    }
    /**
     * 代码清单 8-1 引导一个客户端
     */
    public void bootstrap(){
        //设置EventLoopGroup,提供用于处理Channel事件的EventLoop
        EventLoopGroup group = new NioEventLoopGroup();
        //创建一个Bootstrap类的实例以创建和连接新的客户端Channel
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                //指定要使用的Channel实现
                .channel(NioSocketChannel.class)
                //设置用于Channel事件和数据的ChannelInboundHandler
                .handler(new SimpleChannelInboundHandler<ByteBuf>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
                       System.out.println("Received data");
                    }
                });
        //连接到远程主机
        ChannelFuture future = bootstrap.connect(
                new InetSocketAddress("www.baidu.com",80)
        );
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if(channelFuture.isSuccess()){
                    System.out.println("Connection established");
                }else {
                    System.err.println("Connection attempt failed");
                    channelFuture.cause().printStackTrace();
                }
            }
        });

    }
}
