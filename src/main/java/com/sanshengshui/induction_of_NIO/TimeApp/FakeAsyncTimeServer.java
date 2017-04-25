package com.sanshengshui.induction_of_NIO.TimeApp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by 123 on 2017/4/25.
 */
public class FakeAsyncTimeServer
{
    /**
     * @param args
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException{
        int port=8080;
        if(args!=null && args.length>0){
            try {
                port = Integer.valueOf(args[0]);
            }catch (NumberFormatException e){
                //采用默认值
            }
        }
        ServerSocket server=null;
        try {
            server=new ServerSocket(port);
            System.out.println("The time server is start in port:"+port);
            Socket socket=null;
            FakeAsyncTimeServerHandlerExecutePool singleExecutor=new
                    FakeAsyncTimeServerHandlerExecutePool(50,10000);
            while (true){
                socket=server.accept();
                singleExecutor.execute(new TimeServerHandler(socket));
            }

        }finally {
            if(server!=null){
                System.out.println("The time server close");
                server.close();
                server=null;
            }
        }
    }
}
