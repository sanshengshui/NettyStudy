package com.sanshengshui.Google_Protobuf_CoderAndDecoder.ProtobufVersion_Server2Client;

import com.sanshengshui.Google_Protobuf_CoderAndDecoder.SubscribeRespProto;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

/**
 * @author 穆书伟
 * @description Netty权威指南--Google Protobuf编解码
 * @date 2017/5/3 22:19
 * 错误类型是：Protocol message contained an invalid tag (zero).
 */

public class SubReqClient {
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
                            socketChannel.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
                            socketChannel.pipeline().addLast(new ProtobufDecoder(SubscribeRespProto.SubscribeResp.getDefaultInstance()));
                            socketChannel.pipeline().addLast(new ProtobufEncoder());
                            socketChannel.pipeline().addLast(new SubReqClientHandler());
                        }
                    });
            //发起异步连接操作
            ChannelFuture f=b.connect(host,port).sync();
            //等待客户端链路关闭
            f.channel().closeFuture().sync();

        }finally {
            //优雅推出，释放NIO线程组
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port =8080;
        if(args!=null &&args.length>0){
            try {
                port=Integer.valueOf(args[0]);

            }catch (NumberFormatException e){
                //采用默认值
            }
        }
        new SubReqClient().connect(port,"127.0.0.1");
    }
}
