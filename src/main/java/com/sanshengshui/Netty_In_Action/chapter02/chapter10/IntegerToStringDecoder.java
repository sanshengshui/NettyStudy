package com.sanshengshui.Netty_In_Action.chapter02.chapter10;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * @author 穆书伟
 * @description 在2个消息格式之间进行转换(例如,从一种POJO类型转换成另一种)
 * @date 2017/5/27 17:37
 */
public class IntegerToStringDecoder extends MessageToMessageDecoder<Integer>{
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, Integer integer, List<Object> list) throws Exception {
        list.add(String.valueOf(integer));
    }
}
