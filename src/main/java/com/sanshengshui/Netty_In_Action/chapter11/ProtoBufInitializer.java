package com.sanshengshui.Netty_In_Action.chapter11;

import com.google.protobuf.MessageLite;
import io.netty.channel.*;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;

/**
 * @ClassName ProtoBufInitializer
 * @author 穆书伟
 * @description 使用protobuf
 * @date 2017/6/28 15:28:34
 */
public class ProtoBufInitializer extends ChannelInitializer<Channel> {
    private final MessageLite lite;

    public ProtoBufInitializer(MessageLite lite) {
        this.lite = lite;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //添加 ProtobufVarint32FrameDecoder 以分隔帧
        pipeline.addLast(new ProtobufVarint32FrameDecoder());
        //添加 ProtobufEncoder 以处理消息的编码
        pipeline.addLast(new ProtobufEncoder());
        //添加 ProtobufDecoder 以解码消息
        pipeline.addLast(new ProtobufDecoder(lite));
        //添加 ObjectHandler 以处理解码消息
        pipeline.addLast(new ObjectHandler());
    }

    public static final class ObjectHandler
            extends SimpleChannelInboundHandler<Object> {
        @Override
        public void channelRead0(ChannelHandlerContext ctx, Object msg)
                throws Exception {
            // Do something with the object
        }
    }
}
