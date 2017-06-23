package com.sanshengshui.Netty_In_Action.chapter11;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @ClassName HttpCompressionInitializer
 * @author 穆书伟
 * @description 自动压缩HTTP消息
 * @date 2017/6/23 14:23
 */
public class HttpCompressionInitializer  extends ChannelInitializer<Channel>{
    private final boolean isClient;




    public HttpCompressionInitializer(boolean isClient){
        this.isClient = isClient;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        if(isClient){
            //如果是客户端，则添加HttpClientCodec
            pipeline.addLast("codec",new HttpClientCodec());
            //如果是客户端，则添加HttpContentDecompressor以处理来自服务器的内容
            pipeline.addLast("decompressor",new HttpContentDecompressor());
        }else {
            //如果是服务器，则添加HttpServerCodec
            pipeline.addLast("codec",new HttpServerCodec());
            //如果是服务器，则添加HttpContentCompressor来压缩数据(如果客户端支持它)
            pipeline.addLast("compressor",new HttpContentCompressor());
        }

    }
}
