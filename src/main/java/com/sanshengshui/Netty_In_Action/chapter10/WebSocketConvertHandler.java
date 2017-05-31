package com.sanshengshui.Netty_In_Action.chapter10;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

import java.util.List;

/**
 * @author 穆书伟
 * @description Netty in Action 第10章
 * @date 2017/6/1 00:20
 */
public class WebSocketConvertHandler extends MessageToMessageCodec
<WebSocketFrame,WebSocketConvertHandler.MyWebSocketFrame>
{

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, MyWebSocketFrame myWebSocketFrame, List<Object> list) throws Exception {
        
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, WebSocketFrame webSocketFrame, List<Object> list) throws Exception {

    }

    public static final class MyWebSocketFrame{
        public enum FrameType {
            BINARY,
            CLOSE,
            PING,
            PONG,
            TEXT,
            CONTINUATION

        }

        private final FrameType type;
        private final ByteBuf data;

        public MyWebSocketFrame(FrameType type,ByteBuf data){
            this.type=type;
            this.data=data;

        }

        public FrameType getType() {
            return type;
        }

        public ByteBuf getData() {
            return data;
        }
    }
}
