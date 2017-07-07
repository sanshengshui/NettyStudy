package com.sanshengshui.Netty_In_Action.chapter13;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @ClassName LogEventHandler
 * @author 穆书伟
 * @description LogEventHandler
 * @date 2017/7/7 13:52
 */
public class LogEventHandler extends SimpleChannelInboundHandler<LogEvent> {
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //当异常发生时，打印栈跟踪消息，并关闭对应的Channel
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LogEvent logEvent) throws Exception {
        //创建StringBuilder,并且构建输出的字符串
        StringBuilder builder = new StringBuilder();
        builder.append(logEvent.getReceivedTimestamp());
        builder.append(" [");
        builder.append(logEvent.getSource().toString());
        builder.append("] [");
        builder.append(logEvent.getLogfile());
        builder.append("] : ");
        builder.append(logEvent.getMsg());
        //打印LogEvent的数据
        System.out.println(builder.toString());
    }
}
