package com.sanshengshui.Netty_In_Action.chapter12;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.util.SelfSignedCertificate;

import javax.net.ssl.SSLException;
import java.net.InetSocketAddress;
import java.security.cert.CertificateException;

/**
 * @ClassName SecureChatServer
 * @author 穆书伟
 * @description 向ChatServer添加加密
 * @date 2017/7/3 17:17:21
 */
public class SecureChatServer extends ChatServer {
    private final SslContext context;
    public SecureChatServer(SslContext context){
        this.context = context;
    }

    @Override
    protected ChannelInitializer<Channel> createInitializer(ChannelGroup group) {
        return new SecureChatServerInitializer(group,context);
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Please give port as argument");
            System.exit(1);
        }
        int port = Integer.parseInt(args[0]);
        SelfSignedCertificate cert = null;
        try {
            cert = new SelfSignedCertificate();
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        SslContext context = null;
        try {
            context = SslContext.newServerContext(
                    cert.certificate(), cert.privateKey());
        } catch (SSLException e) {
            e.printStackTrace();
        }
        final SecureChatServer endpoint = new SecureChatServer(context);
        ChannelFuture future = endpoint.start(new InetSocketAddress(port));
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                endpoint.destroy();
            }
        });
        future.channel().closeFuture().syncUninterruptibly();
    }
}
