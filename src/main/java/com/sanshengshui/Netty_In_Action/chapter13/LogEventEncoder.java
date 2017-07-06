package com.sanshengshui.Netty_In_Action.chapter13;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @ClassName LogEventEncoder
 * @author 穆书伟
 * @description LogEvent编码器
 * @date 2017/7/6 15:59
 */
public class LogEventEncoder extends MessageToMessageEncoder<LogEvent> {
    private final InetSocketAddress remoteAddress;

    //LogEventEncoder创建了即将被发送到指定的InetSocketAddress的DatagramPacket消息
    public LogEventEncoder(InetSocketAddress remoteAddress){
        this.remoteAddress = remoteAddress;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, LogEvent logEvent, List<Object> list) throws Exception {
        byte[] file = logEvent.getLogfile().getBytes(CharsetUtil.UTF_8);
        byte[] msg = logEvent.getMsg().getBytes(CharsetUtil.UTF_8);
        ByteBuf buf = channelHandlerContext.alloc()
                .buffer(file.length + msg.length + 1);
        //将文件名写入到ByteBuf中
        buf.writeBytes(file);
        //添加一个SEPARATOR
        buf.writeByte(LogEvent.SEPARATOR);
        //将日志消息写入ByteBuf中
        buf.writeBytes(msg);
        //将一个拥有数据和目的地地址的新DatagramPacket添加到出站的消息列表中
        list.add(new DatagramPacket(buf,remoteAddress));
    }
}
