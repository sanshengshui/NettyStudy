package com.sanshengshui.Netty_In_Action.chapter11;

import io.netty.channel.*;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.File;
import java.io.FileInputStream;

/**
 * @ClassName FileRegionWriteHandler
 * @author 穆书伟
 * @description 使用FileRegion传输文件的内容
 * @date 2017/6/28 13:34:28
 */
public class FileRegionWriteHandler extends ChannelInboundHandlerAdapter {
    private static final Channel CHANNEL_FROM_SOMEWHERE = new NioSocketChannel();
    private static final File FILE_FROM_SOMEWHERE = new File("");

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        File file = FILE_FROM_SOMEWHERE;//get reference from somewhere
        Channel channel = CHANNEL_FROM_SOMEWHERE;//get reference from somewhere
        //...
        //创建一个FileInputStream
        FileInputStream in = new FileInputStream(file);
        //以该文件的完整长度创建一个新的DefaultFileRegion
        FileRegion region = new DefaultFileRegion(
                in.getChannel(),0,file.length());
        //发送该DefaultFileRegion,并注册一个ChannelFutureListener
        channel.writeAndFlush(region).addListener(
                new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture channelFuture) throws Exception {
                        if(!channelFuture.isSuccess()){
                            //处理失败
                            Throwable cause = channelFuture.cause();
                            //Do something
                        }
                    }
                }
        );
    }
}
