package com.sanshengshui.Netty_In_Action.chapter11;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

/**
 * @ClassName HttpPipelineInitializer
 * @author 穆书伟
 * @description 添加HTTP支持
 * @date 2017/6/23 13:57
 */
public class HttpPipelineInitializer extends ChannelInitializer<Channel>{
    private final boolean client;

    public HttpPipelineInitializer(boolean client){
        this.client = client;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        if (client){
            //如果是客户端，则添加 HttpResponseDecoder以处理来自服务器的响应
            pipeline.addLast("decoder",new HttpResponseDecoder());
            //如果是客户端，则添加HttpRequestEncoder以向服务器发送请求
            pipeline.addLast("encoder",new HttpRequestEncoder());
        }else {
            //如果是服务器，则添加HttpRequestDecoder以接受来自客户端的请求
            pipeline.addLast("decoder",new HttpRequestEncoder());
            //如果是服务器，则添加HttpResponseEncoder以向客户端发送响应
            pipeline.addLast("encoder",new HttpResponseEncoder());
        }
    }
}
