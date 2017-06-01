package com.sanshengshui.Netty_In_Action.chapter10;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.handler.codec.http.websocketx.*;

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
        ByteBuf payload=myWebSocketFrame.getData().duplicate().retain();
        switch (myWebSocketFrame.getType()){
            case BINARY:
                list.add(new BinaryWebSocketFrame(payload));
                break;
            case TEXT:
                list.add(new TextWebSocketFrame(payload));
                break;
            case CLOSE:
                list.add(new CloseWebSocketFrame(true,0,payload));
                break;
            case CONTINUATION:
                list.add(new ContinuationWebSocketFrame(payload));
                break;
            case PONG:
                list.add(new PongWebSocketFrame(payload));
                break;
            case PING:
                list.add(new PingWebSocketFrame(payload));
                break;
            default:
                throw new IllegalStateException(
                        "Unsupported websocket msg "+ myWebSocketFrame
                );
        }
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, WebSocketFrame webSocketFrame, List<Object> list) throws Exception {
        ByteBuf payload=webSocketFrame.content().duplicate().retain();
        if(webSocketFrame instanceof BinaryWebSocketFrame){
            list.add(new MyWebSocketFrame(MyWebSocketFrame.FrameType.BINARY,payload));
        }else
        if (webSocketFrame instanceof CloseWebSocketFrame){
           list.add(new MyWebSocketFrame(
                   MyWebSocketFrame.FrameType.CLOSE,payload));
        }else
        if (webSocketFrame instanceof PingWebSocketFrame){
            list.add(new MyWebSocketFrame(MyWebSocketFrame.FrameType.PING,payload));
        }else
        if (webSocketFrame instanceof PongWebSocketFrame){
            list.add(new MyWebSocketFrame(MyWebSocketFrame.FrameType.PONG,payload));
        }else
        if (webSocketFrame instanceof TextWebSocketFrame){
            list.add(new MyWebSocketFrame(MyWebSocketFrame.FrameType.TEXT,payload));
        }else
        if (webSocketFrame instanceof ContinuationWebSocketFrame){
            list.add(new MyWebSocketFrame(MyWebSocketFrame.FrameType.CONTINUATION,payload));
        }else
        {
            throw new IllegalStateException("Unsupported websocket msg"+ webSocketFrame);
        }
    }

    public static final class MyWebSocketFrame{
        //声明WebSocketConvertHandler所使用的OUTBOUND_IN类型
        //定义拥有被包装的有效负载的WebSocketFrame的类型
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
