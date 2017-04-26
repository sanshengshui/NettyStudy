package com.sanshengshui.inducApp_of_NIO.TimeApp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.log4j.Logger;


/**
 * @author 穆书伟
 * @description Netty权威指南读书笔记
 * @date 2017/4/26 20:04
 */
public class TimeClientHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger=Logger.getLogger(TimeClientHandler.class);

    private final ByteBuf firstMessage;

    /**
     * Creates a client-side handler
     */
    public TimeClientHandler(){
        byte[] req="QUERY TIME ORDER".getBytes();
        firstMessage= Unpooled.buffer(req.length);
        firstMessage.writeBytes(req);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(firstMessage);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf=(ByteBuf)msg;
        byte[] req=new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body=new String(req,"UTF-8");
        System.out.println("Now is:"+body);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
       //释放资源
        logger.warn("Unexpected exception from downstream :"+cause.getMessage());
        ctx.close();
    }
}
