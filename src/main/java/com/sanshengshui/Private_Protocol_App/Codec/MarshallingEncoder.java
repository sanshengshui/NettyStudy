package com.sanshengshui.Private_Protocol_App.Codec;


import org.jboss.marshalling.Marshaller;

import java.io.IOException;

/**
 * Created by 123 on 2017/5/11.
 */
public class MarshallingEncoder  {
    private static final byte[] LENGTH_PLACEHOLDER =new byte[4];
    Marshaller marshaller;
    public MarshallingEncoder() throws IOException{

    }
}
