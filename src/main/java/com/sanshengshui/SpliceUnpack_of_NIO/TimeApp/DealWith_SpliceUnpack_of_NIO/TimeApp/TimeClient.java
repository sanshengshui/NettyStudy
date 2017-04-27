package com.sanshengshui.SpliceUnpack_of_NIO.TimeApp.DealWith_SpliceUnpack_of_NIO.TimeApp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;


/**
 * @author 穆书伟
 * @description Netty权威指南读书笔记 >+< 通过添加半包解码器和字符解码来解决问题
 * @date 2017/4/27  19：37
 */
public class TimeClient {
    public void connect(int port,String host) throws Exception{
        //配置客户端NIO线程组
        EventLoopGroup group=new NioEventLoopGroup();
        try {
            Bootstrap b=new Bootstrap();
           b.group(group).channel(NioSocketChannel.class)
                   .option(ChannelOption.TCP_NODELAY,true)
                   .handler(new ChannelInitializer<SocketChannel>() {
                       @Override
                       protected void initChannel(SocketChannel socketChannel) throws Exception {
                           socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
                           socketChannel.pipeline().addLast(new StringDecoder());
                           socketChannel.pipeline().addLast(new TimeClientHandler());
                       }
                   });
           //发起异步连接操作
            ChannelFuture f=b.connect(host,port).sync();
            //等待客户端链路关闭
            f.channel().closeFuture().sync();

        }finally {
            //优雅退出，释放NIO线程组
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args)throws Exception{
        int port=8080;
        if(args!=null && args.length>0){
            try {
                port= Integer.valueOf(args[0]);
            }catch (NumberFormatException e){
                //采用默认值
            }
        }
        new TimeClient().connect(port,"127.0.0.1");
    }
}
