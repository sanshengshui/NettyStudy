package com.sanshengshui.Netty_In_Action.chapter13;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;

/**
 * @ClassName LogEventBroadcaster
 * @author 穆书伟
 * @description LogEventBroadcaster
 * @date 2017/7/6 16:23
 */
public class LogEventBroadcaster {
    private final EventLoopGroup group;
    private final Bootstrap bootstrap;
    private final File file;

    public LogEventBroadcaster(InetSocketAddress address,File file){
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        //引导该NioDatagramChannel(无连接的)
        bootstrap.group(group).channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST,true)
                .handler(new LogEventEncoder(address));
        this.file = file;
    }
    public void run() throws Exception{
        //绑定Channel
        Channel ch = bootstrap.bind(0).sync().channel();
        long pointer = 0;
        for (;;){
            long len =file.length();
            if(len < pointer){
                //file was reset
                //如果有必要，将文件指针设置到该文件的最后一个字节
                pointer = len;
            }else  if (len > pointer){
                //Content was added
                RandomAccessFile raf =new RandomAccessFile(file,"r");
                //设置当前的文件指针，以确保没有任何的旧日志被发送
                raf.seek(pointer);
                String line;
                while ((line = raf.readLine())!=null){
                    //对于每个日志条目，写入一个LogEvent到Channel中
                    ch.writeAndFlush(new LogEvent(null,-1,file.getAbsolutePath(),line));
                }
                //存储其在文件中的当前位置
                pointer = raf.getFilePointer();
                raf.close();
            }
            try {
                //休眠1秒，如果被中断，则退出循环；要不然重新处理它
                Thread.sleep(1000);
            }catch (InterruptedException e){
                Thread.interrupted();
                break;
            }
        }

    }
    public void stop(){
        group.shutdownGracefully();
    }
    public static void main(String[] args){
        if(args.length != 2){
            throw new IllegalArgumentException();
        }
        //创建并启动一个新的LogEventBroadcaster的实例
        LogEventBroadcaster broadcaster =new LogEventBroadcaster(
                new InetSocketAddress("255.255.255.255", Integer.parseInt(args[0])),new File(args[1])
        );
        try {
            broadcaster.run();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            broadcaster.stop();
        }
    }

}
