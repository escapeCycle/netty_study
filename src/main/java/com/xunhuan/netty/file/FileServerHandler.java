package com.xunhuan.netty.file;

import io.netty.channel.*;

import java.io.File;
import java.io.RandomAccessFile;

/**
 * @Auther: tianhuan
 * @Date: 2019/6/10 15:46
 * @Description:
 */
public class FileServerHandler extends SimpleChannelInboundHandler<String> {

    private static final String CR = System.getProperty("line.separator");

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        File file = new File(msg);
        if (file.exists()) {
            if (!file.isFile()) {
                ctx.writeAndFlush("NOT a file : " + file + CR);
                return;
            }
            ctx.write(file + " " + file.length() + CR);
            RandomAccessFile r = new RandomAccessFile(msg, "r");
            FileRegion fileRegion = new DefaultFileRegion(r.getChannel(), 0, r.length());
            ctx.write(fileRegion);
            ctx.writeAndFlush(CR);
            r.close();
        }else {
            ctx.writeAndFlush("File Not found : " + file + CR);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    public static void main(String[] args) {
        File file = new File("C:\\Users\\DELL\\Desktop\\a.txt");
        System.out.println(file.length());
        System.out.println(file.isFile());
        System.out.println(file.exists());
    }
}
