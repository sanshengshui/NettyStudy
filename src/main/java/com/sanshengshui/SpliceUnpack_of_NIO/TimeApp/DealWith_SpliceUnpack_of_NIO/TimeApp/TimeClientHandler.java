package com.sanshengshui.SpliceUnpack_of_NIO.TimeApp.DealWith_SpliceUnpack_of_NIO.TimeApp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.log4j.Logger;

/**
 * @author 穆书伟
 * @description Netty权威指南读书笔记 >+< 通过添加半包解码器和字符解码来解决问题
 * @date 2017/4/27  19：37
 */
public class TimeClientHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger=Logger.getLogger(TimeClientHandler.class.getName());

    private int counter;

    private byte[] req;

    public TimeClientHandler(){
        req= ("QUERY TIME ORDER" + System.getProperty("line.separator")).getBytes();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf message=null;
        for(int i=0;i<100;i++){
            message = Unpooled.buffer(req.length);
            message.writeBytes(req);
            ctx.writeAndFlush(message);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body=(String)msg;
        System.out.println("Now is :"+ body +"; the counter is :"+ ++counter);
    }

    //释放资源
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
       ctx.close();
    }
}
