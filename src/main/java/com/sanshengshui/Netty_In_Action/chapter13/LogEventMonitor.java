package com.sanshengshui.Netty_In_Action.chapter13;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.net.InetSocketAddress;

/**
 * @ClassName LogEventMonitor
 * @author 穆书伟
 * @description LogEventMonitor
 * @date 2017/7/7 14:23
 */
public class LogEventMonitor {
    private final EventLoopGroup group;
    private final Bootstrap bootstrap;

    public LogEventMonitor(InetSocketAddress address){
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        //引导该NioDatagramChannel
        bootstrap.group(group)
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST,true)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        ChannelPipeline pipeline = channel.pipeline();
                        pipeline.addLast(new LogEventDecoder());
                        pipeline.addLast(new LogEventHandler());
                    }
                })
                .localAddress(address);
    }
    public Channel bind(){
        //绑定Channel。注意，DatagramChannel是无连接的
        return bootstrap.bind().syncUninterruptibly().channel();
    }
    public void stop(){
        group.shutdownGracefully();
    }

    public static void main(String[] args){
        if(args.length!=1){
            throw new IllegalArgumentException("Usage: LogEventMonitor<port>");
        }
        //构造一个新的LogEventMonitor
        LogEventMonitor monitor =new LogEventMonitor(
                new InetSocketAddress(Integer.parseInt(args[0]))
        );
        try {
            Channel channel = monitor.bind();
            System.out.println("LogEventMonitor running");
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            monitor.stop();
        }
    }
}
