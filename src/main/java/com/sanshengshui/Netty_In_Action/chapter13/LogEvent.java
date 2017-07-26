package com.sanshengshui.Netty_In_Action.chapter13;

import java.net.InetSocketAddress;

/**
 * @ClassName LogEvent
 *
 *
 *
 * @author 穆书伟
 * @description LogEvent消息
 * @date 2017/7/6 13:48
 */
public class LogEvent {
    public static final byte SEPARATOR =(byte) ':';
    private final InetSocketAddress source;
    private final String logfile;
    private final String msg;
    private final long received;

    //用于传出消息的构造函数
    public LogEvent(String logfile, String msg) {
        this(null, -1, logfile, msg);
    }

    //用于传入消息的构造函数
    public LogEvent(InetSocketAddress source, long received,
                    String logfile, String msg) {
        this.source = source;
        this.logfile = logfile;
        this.msg = msg;
        this.received = received;
    }

    //返回发送 LogEvent 的源的 InetSocketAddress
    public InetSocketAddress getSource() {
        return source;
    }

    //返回所发送的 LogEvent 的日志文件的名称
    public String getLogfile() {
        return logfile;
    }

    //返回消息内容
    public String getMsg() {
        return msg;
    }

    //返回接收 LogEvent 的时间
    public long getReceivedTimestamp() {
        return received;
    }
}
