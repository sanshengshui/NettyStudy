package com.sanshengshui.Google_Protobuf_CoderAndDecoder.ProtobufVersion_Server2Client;

import com.sanshengshui.Google_Protobuf_CoderAndDecoder.SubscribeReqProto;
import com.sanshengshui.Google_Protobuf_CoderAndDecoder.SubscribeRespProto;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author 穆书伟
 * @description Netty权威指南--Google Protobuf编解码
 * @date 2017/5/3 21:48
 */

public class SubReqServerHandler extends ChannelInboundHandlerAdapter{

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SubscribeReqProto.SubscribeReq req= (SubscribeReqProto.SubscribeReq)msg;
        if("mushuwei".equalsIgnoreCase(req.getUserName())){
            System.out.println("Service accept client subscribe req: ["+req.toString()+"]");
            ctx.writeAndFlush(resp(req.getSubReqID()));
        }
    }
    private SubscribeRespProto.SubscribeResp resp(int subReqID){
        SubscribeRespProto.SubscribeResp.Builder builder = SubscribeRespProto.SubscribeResp.newBuilder();
        builder.setSubReqID(subReqID);
        builder.setRespCode(0);
        builder.setDesc("Netty book order succeed,3 days later,sent to the designated address");
        return builder.build();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
