package com.xunhuan.netty.zerocopy;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * @Auther: tianhuan
 * @Date: 2019/5/7 17:31
 * @Description:
 */
public class OldIOClient {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost",8899);
        String fileName = "D:\\下载文件\\CentOS-7-x86_64-Minimal-1810.iso";
        InputStream inputStream = new FileInputStream(fileName);

        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        byte[] buffer = new byte[4096];
        long readCount,total = 0;

        long startTime = System.currentTimeMillis();

        while ((readCount = inputStream.read(buffer,0,buffer.length)) >= 0){
            total += readCount;
            dataOutputStream.write(buffer);
        }

        System.out.println("发送的总字节数: "+total+", 耗时: " +(System.currentTimeMillis() - startTime));

        dataOutputStream.close();
        socket.close();
        inputStream.close();
    }
}
