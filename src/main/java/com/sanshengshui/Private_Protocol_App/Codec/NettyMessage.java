package com.sanshengshui.Private_Protocol_App.Codec;

/**
 * @author 穆书伟
 * @decription Netty权威指南第12章 私有协议栈开发>+< Netty消息定义
 * @date 2017/5/11 20:23
 */
public class NettyMessage {
    private Header header;//消息头
    private Object body;//消息体

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "NettyMessage{" +
                "header=" + header +
                ", body=" + body +
                '}';
    }
}
