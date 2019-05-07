package com.xunhuan.netty.io;

import java.io.*;

/**
 * @author tianhuan
 * @date 2019-04-28 16:30
 **/
public class Test_01 {

    public static void main(String[] args) throws IOException {
//        listAllFiles(new File("D:\\myself\\daily_plan"));
        copyFile("input.txt","output1.txt");
    }

    public static void listAllFiles(File file) {
        if (file == null || !file.exists()) {
            return;
        }

        if (file.isFile()) {
            System.out.println(file.getName());
            return;
        }
        for (File f : file.listFiles()) {
            listAllFiles(f);
        }
    }

    public static void copyFile(String src,String dist) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(src);
        FileOutputStream fileOutputStream = new FileOutputStream(dist);

        byte[] read = new byte[20 * 1024];
        int cnt;
        while (( cnt = fileInputStream.read(read,0,read.length)) != -1){
            fileOutputStream.write(read,0,cnt);
        }
        fileInputStream.close();
        fileOutputStream.close();
    }
}
