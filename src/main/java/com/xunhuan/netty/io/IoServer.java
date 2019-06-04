package com.xunhuan.netty.io;

import java.net.ServerSocket;
import java.net.Socket;

public class IoServer {

    public static void main(String[] args) throws Exception {
        int port = 8080;
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("this time server start in port : " + port);
            Socket socket = null;
            while (true) {
                socket = serverSocket.accept();
                new Thread(new IoServerHandler(socket)).start();
            }
        } finally {
            if (serverSocket != null) {
                serverSocket.close();
            }
        }
    }
}
