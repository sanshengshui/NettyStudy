package com.sanshengshui.JBoss_Marshalling_codeAndEncode;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;


/**
 * @author 穆书伟
 * @decription Netty权威指南 >+< 第九章 JBoss Marshalling编解码
 * @date 2017/5/11 21:27
 */
public class SubReqClient {
    public void connect(int port,String host) throws Exception{
        //配置客户端NIO线程组
        EventLoopGroup group= new NioEventLoopGroup();
        try{
            Bootstrap b=new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(MarShallingCodeCFactory.buildMarshallingEncoder());
                            socketChannel.pipeline().addLast(MarShallingCodeCFactory.buildMarshallingDecoder());


                        }
                    });
            //发起异步操作
            ChannelFuture f=b.connect(host,port).sync();
            //等待客户端链路关闭
            f.channel().closeFuture().sync();
        }finally {
            //优雅退出,释放NIO线程
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args)throws Exception{
        int port=8080;
        if(args!=null && args.length>0){
            try {
                port=Integer.valueOf(args[0]);
            }catch (NumberFormatException e){
                //采用默认值
            }
        }
        new SubReqClient().connect(port,"127.0.0.1");

    }
}
