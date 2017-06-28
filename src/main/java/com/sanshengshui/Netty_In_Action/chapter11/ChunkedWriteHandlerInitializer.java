package com.sanshengshui.Netty_In_Action.chapter11;

import io.netty.channel.*;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedStream;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.io.File;
import java.io.FileInputStream;

/**
 * @ClassName ChunkedWriteHandlerInitializer
 * @author 穆书伟
 * @description 使用ChunkedStream传输文件内容
 * @date 2017/6/28 14:24:42
 */
public class ChunkedWriteHandlerInitializer extends ChannelInitializer<Channel> {

    private final File file;
    private final SslContext sslContext;
    public ChunkedWriteHandlerInitializer(File file,SslContext sslContext){
        this.file = file;
        this.sslContext = sslContext;
    }
    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        //将SslHandler添加到ChannelPipeline中
        pipeline.addLast(new SslHandler(sslContext.newEngine(channel.alloc())));
        //添加ChunkedWriteHandler以处理作为ChunkedInput传入的数据
        pipeline.addLast(new ChunkedWriteHandler());
        //一旦连接建立，WriteStreamHandler就开始写文件数据
        pipeline.addLast(new WriteStreamHandler());
    }

    public final class WriteStreamHandler extends ChannelInboundHandlerAdapter{
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            super.channelActive(ctx);
            ctx.writeAndFlush(
                    new ChunkedStream(new FileInputStream(file))
            );
        }
    }
}
