package com.sanshengshui.Private_Protocol_App.Codec;


import com.sanshengshui.JBoss_Marshalling_codeAndEncode.MarShallingCodeCFactory;
import org.jboss.marshalling.Marshaller;

import java.io.IOException;

/**
 * @author 穆书伟
 * @decription Netty权威指南第12章 私有协议栈开发>+< Netty消息定义
 * @date 2017/5/13 23:14
 */

public class MarshallingEncoder  {
    private static final byte[] LENGTH_PLACEHOLDER =new byte[4];
    Marshaller marshaller;
    public MarshallingEncoder() throws IOException{


    }
}
