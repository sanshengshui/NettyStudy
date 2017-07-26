package com.sanshengshui.separator_and_fixedlength_decoder;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;


/**
 * @author 穆书伟
 * @description Netty权威指南  - -分隔符和定长解码器的应用
 */
public class EchoClient {
    public void connect(int port,String host) throws Exception{
        //配置客户端NIO线程组
        EventLoopGroup group=new NioEventLoopGroup();
        try {
            Bootstrap b=new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            ByteBuf delimiter= Unpooled.copiedBuffer("$_".getBytes());
                            //channel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,delimiter));
                            channel.pipeline().addLast(new StringDecoder());
                            channel.pipeline().addLast(new EchoClientHandler());
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

    public static void main(String[] args) throws Exception{
        int port=8080;
        if(args!=null && args.length>0){
            try {
                port=Integer.valueOf(args[0]);
            }catch (NumberFormatException e){
                //采用默认值
            }
        }
        new EchoClient().connect(port,"127.0.0.1");
    }
}






