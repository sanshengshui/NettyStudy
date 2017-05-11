package com.sanshengshui.JBoss_Marshalling_codeAndEncode;

import com.sanshengshui.Google_Protobuf_CoderAndDecoder.ProtobufVersion_Server2Client.SubReqServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author 穆书伟
 * @decription Netty权威指南 >+< 第九章 JBoss Marshalling编解码
 * @date 2017/5/11 21:05
 */
public class SubReqServer {
    public void bind(int port )throws Exception{
        //配置服务端的NIO线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b =new ServerBootstrap();
            b.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,100)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(MarShallingCodeCFactory.buildMarshallingDecoder());
                            socketChannel.pipeline().addLast(MarShallingCodeCFactory.buildMarshallingEncoder());
                            socketChannel.pipeline().addLast(new SubReqServerHandler());

                        }
                    });
            //绑定端口，同步等待成功
            ChannelFuture f=b.bind(port).sync();

            //等待服务端监听端口关闭
            f.channel().closeFuture().sync();

        }finally {
            //优雅退出，释放线程池资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception{
        int port = 8080;
        if(args!=null && args.length>0){
            try {
                port=Integer.valueOf(args[0]);

            }catch (NumberFormatException e){
                //采用默认值
            }
        }
    }
}
