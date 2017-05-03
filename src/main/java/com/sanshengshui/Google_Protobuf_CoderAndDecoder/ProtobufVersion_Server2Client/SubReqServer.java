package com.sanshengshui.Google_Protobuf_CoderAndDecoder.ProtobufVersion_Server2Client;

import com.sanshengshui.Google_Protobuf_CoderAndDecoder.SubscribeReqProto;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author 穆书伟
 * @description Netty权威指南--Google Protobuf编解码
 * @date 2017/5/3 21:58
 */
public class SubReqServer {
    public void bind(int port) throws Exception{
        //配置服务器的NIO线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup=new NioEventLoopGroup();
        try {
            ServerBootstrap b=new ServerBootstrap();
            b.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,100)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            socketChannel.pipeline().addLast(
                                    new ProtobufVarint32FrameDecoder()
                            );
                            socketChannel.pipeline().addLast(
                                    new ProtobufDecoder(
                                            SubscribeReqProto.SubscribeReq.getDefaultInstance()
                                    )
                            );
                            socketChannel.pipeline().addLast(
                                    new ProtobufVarint32LengthFieldPrepender()
                            );
                            socketChannel.pipeline().addLast(
                                    new ProtobufEncoder()
                            );
                            socketChannel.pipeline().addLast(
                                    new SubReqServerHandler()
                            );
                        }
                    });
            //绑定端口，同步等待成功
            ChannelFuture f=b.bind(port).sync();
            //等待服务端监听端口关闭
            f.channel().closeFuture().sync();

        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception{
        int port =8080;
        if(args!=null&&args.length>0){
            try {
                port=Integer.valueOf(args[0]);
            }catch (NumberFormatException e){
                //采用默认值
            }
        }
        new SubReqServer().bind(port);
    }
}
