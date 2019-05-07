package com.xunhuan.netty.zerocopy;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Auther: tianhuan
 * @Date: 2019/5/7 17:18
 * @Description:
 */
public class OldIOServer {

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(8899);

        while (true){
            Socket socket = serverSocket.accept();
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

            try {

                byte[] byteArray = new byte[4096];

                while (true){
                    int read = dataInputStream.read(byteArray, 0, byteArray.length);
                    if(read == -1){
                        break;
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
