package com.sanshengshui.Google_Protobuf_CoderAndDecoder.ProtobufVersion_Server2Client;

import com.sanshengshui.Google_Protobuf_CoderAndDecoder.SubscribeReqProto;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 穆书伟
 * @description Netty权威指南--Google Protobuf编解码
 * @date 2017/5/3 22:11
 */

public class SubReqClientHandler extends ChannelInboundHandlerAdapter {
    public SubReqClientHandler(){

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
       for (int i=0;i<10;i++){
           ctx.write(subReq(i));
       }
       ctx.flush();
    }

    private SubscribeReqProto.SubscribeReq subReq(int i){
        SubscribeReqProto.SubscribeReq.Builder builder=SubscribeReqProto.SubscribeReq
                .newBuilder();
        builder.setSubReqID(i);
        builder.setUserName("mushuwei");
        builder.setProductName("Netty book For Protobuf");
        List<String> address =new ArrayList<>();
        address.add("SuZhou ShiZiLin");
        address.add("SuZhou LiuYuan");
        address.add("SuZhou ZhuoZhengYuan");
        builder.addAllAddress(address);
        return builder.build();
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
