package com.sanshengshui.induction_of_NIO.TimeApp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author  jamesmsw
 * @date 2017/4/24
 * @version  2017.04
 * 同步阻塞式I/O的TimeServer
 */
public class TimeServer {
    public static void main(String[] args) throws IOException {
        int port=8080;
        if(args!=null && args.length>0){
            try {
                port = Integer.valueOf(args[0]);
            }catch (NumberFormatException e){
                e.printStackTrace();
            }

        }
        ServerSocket server=null;
        try {
            server = new ServerSocket(port);
            System.out.println("The time server is start in port:" + port);
            Socket socket=null;
            while (true){
                socket=server.accept();
                new Thread(new TimeServerHandler(socket)).start();
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
