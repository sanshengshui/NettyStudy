package com.sanshengshui.Netty_In_Action.chapter11;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.codec.LineBasedFrameDecoder;

/**
 * @ClassName CmdHandlerInitializer
 * @author 穆书伟
 * @description 使用ChannelInitializer安装解码器
 * @date 2017/6/27 11:17
 */
public class CmdHandlerInitializer extends ChannelInitializer<Channel> {
    private static final byte SPACE =(byte)' ';

    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        //添加 CmdDecoder 以提取 Cmd 对象，并将它转发给下一个 ChannelInboundHandler
        pipeline.addLast(new CmdDecoder(64 * 1024));
        //添加 CmdHandler 以接收和处理 Cmd 对象
        pipeline.addLast(new CmdHandler());
    }

    //Cmd POJO
    public static final class Cmd{
        private final ByteBuf name;
        private final ByteBuf args;

        public Cmd(ByteBuf name,ByteBuf args){
            this.name = name;
            this.args = args;
        }

        public ByteBuf name(){
            return name;
        }
        public ByteBuf args(){
            return args;
        }
    }

    public static final class CmdDecoder extends LineBasedFrameDecoder{
        public CmdDecoder(int maxLength){
            super(maxLength);
        }

        @Override
        protected Object decode(ChannelHandlerContext ctx, ByteBuf buffer)
                throws Exception {
            //从 ByteBuf 中提取由行尾符序列分隔的帧
            ByteBuf frame = (ByteBuf) super.decode(ctx, buffer);
            if (frame == null) {
                //如果输入中没有帧，则返回 null

                return null;
            }
            //查找第一个空格字符的索引。前面是命令名称，接着是参数
            int index = frame.indexOf(frame.readerIndex(),
                    frame.writerIndex(), SPACE);
            //使用包含有命令名称和参数的切片创建新的 Cmd 对象
            return new Cmd(frame.slice(frame.readerIndex(), index),
                    frame.slice(index + 1, frame.writerIndex()));
        }
    }

    public static final class CmdHandler extends SimpleChannelInboundHandler<Cmd>{

        @Override
        public void channelRead0(ChannelHandlerContext ctx, Cmd msg)
                throws Exception {
            // Do something with the command
            //处理传经 ChannelPipeline 的 Cmd 对象
        }
    }
}
