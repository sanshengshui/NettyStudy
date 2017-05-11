package com.sanshengshui.JBoss_Marshalling_codeAndEncode;


import io.netty.handler.codec.marshalling.*;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;

/**
 * @author 穆书伟
 * @decription Netty权威指南 >+< 第九章 JBoss Marshalling编解码
 * @date 2017/5/11 21:05
 */
public class MarShallingCodeCFactory {
    /**
     * 创建JBoss Marshalling解码器MarshallingDecoder
     * @return
     */
    public static MarshallingDecoder buildMarshallingDecoder(){
        final MarshallerFactory marshallerFactory= Marshalling.getProvidedMarshallerFactory("serial");
        final MarshallingConfiguration configuration =new MarshallingConfiguration();
        configuration.setVersion(5);
        UnmarshallerProvider provider = new DefaultUnmarshallerProvider(marshallerFactory,configuration);
        MarshallingDecoder decoder= new MarshallingDecoder(provider,1024);
        return decoder;

    }

    /**
     * 创建JBoss Marshalling编码器MarshallingEncoder
     * return
     */
    public static MarshallingEncoder buildMarshallingEncoder(){
        final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
        final MarshallingConfiguration configuration=new MarshallingConfiguration();
        configuration.setVersion(5);
        MarshallerProvider provider= new DefaultMarshallerProvider(marshallerFactory,configuration);
        MarshallingEncoder encoder= new MarshallingEncoder(provider);
        return encoder;
    }
}
