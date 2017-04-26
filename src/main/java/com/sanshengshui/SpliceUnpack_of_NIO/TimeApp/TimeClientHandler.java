package com.sanshengshui.SpliceUnpack_of_NIO.TimeApp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.log4j.Logger;
/**
 * @author 穆书伟
 * @description Netty权威指南读书笔记
 * @date 2017/4/26  22:01
 */
public class TimeClientHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger=Logger.getLogger(TimeClientHandler.class.getName());
    private int counter;
    private byte[] req;

    /**
     *
     * Creates a client-side handler
     */
    public TimeClientHandler(){
        req=("QUERY TIME ORDER"+System.getProperty("line.separator")).getBytes();
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf message=null;
        for(int i=0;i<100;i++){
            message= Unpooled.buffer(req.length);
            message.writeBytes(req);
            ctx.writeAndFlush(message);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf=(ByteBuf)msg;
        byte[] req=new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body=new String(req,"UTF-8");
        System.out.println("Now is :"+body+";the counter is :"+ ++counter);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.warn("Unexpected exception from downstream :"+cause.getMessage());
        ctx.close();
    }
}
