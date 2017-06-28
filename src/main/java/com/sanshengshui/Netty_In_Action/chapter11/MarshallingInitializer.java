package com.sanshengshui.Netty_In_Action.chapter11;

import io.netty.channel.*;
import io.netty.handler.codec.marshalling.MarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallingDecoder;
import io.netty.handler.codec.marshalling.MarshallingEncoder;
import io.netty.handler.codec.marshalling.UnmarshallerProvider;

import java.io.Serializable;

/**
 * @ClassName MarshallingInitializer
 * @author 穆书伟
 * @description 使用JBoss Marshalling
 * @date 2017/6/28 14:58:00
 */
public class MarshallingInitializer extends ChannelInitializer<Channel> {
    private final MarshallerProvider marshallerProvider;
    private final UnmarshallerProvider unmarshallerProvider;

    public MarshallingInitializer(
            UnmarshallerProvider unmarshallerProvider,
            MarshallerProvider marshallerProvider) {
        this.marshallerProvider = marshallerProvider;
        this.unmarshallerProvider = unmarshallerProvider;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        //添加 MarshallingDecoder 以将 ByteBuf 转换为 POJO
        pipeline.addLast(new MarshallingDecoder(unmarshallerProvider));
        //添加 MarshallingEncoder 以将POJO 转换为 ByteBuf
        pipeline.addLast(new MarshallingEncoder(marshallerProvider));
        //添加 ObjectHandler，以处理普通的实现了Serializable 接口的 POJO
        pipeline.addLast(new ObjectHandler());
    }

    public static final class ObjectHandler
            extends SimpleChannelInboundHandler<Serializable> {
        @Override
        public void channelRead0(
                ChannelHandlerContext channelHandlerContext,
                Serializable serializable) throws Exception {
            // Do something
        }
    }
}
