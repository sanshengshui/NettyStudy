package com.sanshengshui.Netty_In_Action.chapter11;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @ClassName HttpAggregatorInitializer
 * @@author 穆书伟
 * @description 自动聚合HTTP的消息分段
 * @date 2017/6/23
 */
public class HttpAggregatorInitializer extends ChannelInitializer<Channel>{

    private final boolean isClient;

    public HttpAggregatorInitializer(boolean isClient){
        this.isClient = isClient;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        if(isClient) {
            //如果是客户端，则添加HttpClientCodec
            pipeline.addLast("codec",new HttpClientCodec());
        }else {
            //如果是服务器，则添加HttpServerCodec
            pipeline.addLast("codec",new HttpServerCodec());
        }
        //将最大的消息大小为512KB的HttpAggregatorInitializer添加到ChannelPipeline
        pipeline.addLast("aggregator",new HttpObjectAggregator( 512 *1024));
    }
}
