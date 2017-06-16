package com.sanshengshui.Netty_In_Action.chapter08;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.oio.OioDatagramChannel;

import java.net.InetSocketAddress;

/**
 * @ClassName BootstrapDatagramChannel
 * @author 穆书伟
 * @description 使用Bootstrap和DatagramChannel
 * @date 2017/6/16 15:04
 */
public class BootstrapDatagramChannel {

    public void bootstrap(){
        //创建一个Bootstrap的实例以创建和绑定新的数据报Channel
        Bootstrap bootstrap = new Bootstrap();
        //设置EventLoopGroup，其提供了用以处理Channel事件的EventLoop
        bootstrap.group(new OioEventLoopGroup()).channel(
                //指定Channel的实现
                OioDatagramChannel.class
        ).handler(
                //设置用以处理Channel的I/O以及数据的ChannelInboundHandler
                new SimpleChannelInboundHandler<DatagramPacket>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket) throws Exception {
                        //Do something with the packet
                    }
                }
        );
        //调用bind()方法，因为该协议是无连接的
        ChannelFuture future = bootstrap.bind(new InetSocketAddress(0));
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if(channelFuture.isSuccess()){
                    System.out.println("Channel bound");
                }else {
                    System.err.println("Bind attempt failed");
                    channelFuture.cause().printStackTrace();
                }
            }
        });

    }
}
