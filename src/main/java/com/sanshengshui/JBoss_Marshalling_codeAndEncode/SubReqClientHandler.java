package com.sanshengshui.JBoss_Marshalling_codeAndEncode;


import com.sanshengshui.Google_Protobuf_CoderAndDecoder.SubscribeReqProto;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;


/**
 * @author 穆书伟
 * @decription Netty权威指南 >+< 第九章 JBoss Marshalling编解码
 * @date 2017/5/11 21:43
 */
public class SubReqClientHandler  extends ChannelInboundHandlerAdapter{
    public SubReqClientHandler(){

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        /*
        for (int i=0;i<10;i++){
            ctx.write(subReq(i));
        }
        */
        ctx.flush();
    }



    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Receive server response :["+ msg +"]");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.flush();
    }
}
