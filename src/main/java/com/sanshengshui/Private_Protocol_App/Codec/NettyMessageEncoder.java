package com.sanshengshui.Private_Protocol_App.Codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;


import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author 穆书伟
 * @decription Netty权威指南第12章 私有协议栈开发>+< Netty消息编解器
 * @date 2017/5/11 20:23
 */
public class NettyMessageEncoder extends MessageToMessageEncoder<NettyMessage> {
    MarshallingEncoder marshallingEncoder;
    public NettyMessageEncoder() throws IOException{
        this.marshallingEncoder=new MarshallingEncoder();
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, NettyMessage nettyMessage, List<Object> list) throws Exception {
        if(nettyMessage == null || nettyMessage.getHeader() == null)
            throw new Exception("The encode message is null");
        ByteBuf sendBuf= Unpooled.buffer();
        sendBuf.writeInt((nettyMessage.getHeader().getCrcCode()));
        sendBuf.writeInt((nettyMessage.getHeader().getLength()));
        sendBuf.writeLong((nettyMessage.getHeader().getSessionID()));
        sendBuf.writeByte((nettyMessage.getHeader().getType()));
        sendBuf.writeByte((nettyMessage.getHeader().getPriority()));
        sendBuf.writeInt((nettyMessage.getHeader()).getAttachment().size());
        String key = null;
        byte[] keyArray = null;
        for(Map.Entry<String,Object> param:nettyMessage.getHeader().getAttachment().entrySet()){
            key= param.getKey();
            keyArray =key.getBytes("UTF-8");
            sendBuf.writeInt(keyArray.length);
            sendBuf.writeBytes(keyArray);

        }



    }
}
