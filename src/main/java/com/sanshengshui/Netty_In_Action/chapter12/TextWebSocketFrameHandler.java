package com.sanshengshui.Netty_In_Action.chapter12;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

/**
 * @ClassName TextWebSocketFrameHandler
 * @author 穆书伟
 * @description 处理文本帧
 * @date 2017/7/2 14:01
 */
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    private final ChannelGroup group;

    public TextWebSocketFrameHandler(ChannelGroup group){
        this.group = group;
    }

    //重写userEventTriggered()方法以处理自定义事件
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        //如果该事件表示握手成功，则从该ChannelPineline中移除HttpRequest-Handler,因为将不会接收到任何HTTP消息了
        if(evt == WebSocketServerProtocolHandler.ServerHandshakeStateEvent.HANDSHAKE_COMPLETE) {
            ctx.pipeline().remove(HttpRequestHandler.class);
            //(1)通知所有已经连接的WebSocket客户端新的客户端已经连接上了
            group.writeAndFlush(new TextWebSocketFrame("Client" + ctx.channel() + " joined"));
            //(2)将新的WebSocket Channel添加到ChannelGroup中，以便它可以接受到所有的消息
            group.add(ctx.channel());
        }else {
            super.userEventTriggered(ctx, evt);
        }

    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        //(3)增加消息的引用计数
        group.writeAndFlush(textWebSocketFrame.retain());
    }
}
