package com.sanshengshui.Netty_In_Action.chapter02.chapter10;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @author 穆书伟
 * @description 假设你接受了一个包含简单int的字节流，每个int都需要被单独处理。
 * 在这种情况下，你需要从入站ByteBuf中读取每个int，并将它传递给ChannelPipeline中的下一个
 * ChannelInboundHandler。为了解码这个字节流，你要扩展ByteToMessageDecoder类。
 *
 * @date 2017/5/27 17:21
 */
public class ToIntegerDecoder2 extends ReplayingDecoder<Void>{
    /*
    这个类的完整声明是:
    public abstract class ReplayingDecoder<S> extends ByteToMessageDecoder
    类型参数S指定了用于状态管理的类型，其中void代表不需要状态管理。
     */

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        list.add(byteBuf.readInt());
    }
}
