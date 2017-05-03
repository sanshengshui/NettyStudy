package com.sanshengshui.Google_Protobuf_CoderAndDecoder;

import com.google.protobuf.InvalidProtocolBufferException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 穆书伟
 * @description Netty权威指南--Google Protobuf编解码
 * @date 2017/5/3 20:38
 */
public class TestSubscribeReqProto {
    private static byte[] encode(SubscribeReqProto.SubscribeReq req){
        return req.toByteArray();
    }
    private static SubscribeReqProto.SubscribeReq decode(byte[] body) throws InvalidProtocolBufferException {
        return SubscribeReqProto.SubscribeReq.parseFrom(body);
    }
    private static SubscribeReqProto.SubscribeReq createSubscribeReq(){
        SubscribeReqProto.SubscribeReq.Builder builder= SubscribeReqProto.SubscribeReq.newBuilder();
        builder.setSubReqID(1);
        builder.setUserName("mushuwei");
        builder.setProductName("Netty book");
        List<String> address = new ArrayList<>();
        address.add("SuZhou ShiZiLin");
        address.add("SuZhou zhuozhengyuan");
        address.add("SuZhou liuyuan");
        builder.addAllAddress(address);
        return builder.build();


    }
    public static void main(String[] args) throws InvalidProtocolBufferException {
        SubscribeReqProto.SubscribeReq req=createSubscribeReq();
        System.out.println("Before encode　："+req.toString());
        SubscribeReqProto.SubscribeReq req2 =decode(encode(req));
        System.out.println("After decode :"+req2.toString());
        System.out.println("Assert equal :--->"+req2.equals(req));
    }
}
