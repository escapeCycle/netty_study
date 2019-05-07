package com.xunhuan.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * 关于Buffer的Scattering和Gathering
 *
 * @author tianhuan
 * @date 2019-04-18 22:25
 **/
public class NioTest11 {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel socketChannel = ServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress(8899);
        socketChannel.socket().bind(address);

        int messageLength = 9;

        ByteBuffer[] buffers = new ByteBuffer[3];

        buffers[0] = ByteBuffer.allocate(2);
        buffers[1] = ByteBuffer.allocate(3);
        buffers[2] = ByteBuffer.allocate(4);

        SocketChannel channel = socketChannel.accept();

        while (true) {
            int byteRead = 0;
            while (byteRead < messageLength) {
                long read = channel.read(buffers);
                byteRead += read;

                System.out.println("byteRead:"+byteRead);
                Arrays.stream(buffers).
                        map(buffer -> "position:" + buffer.position() + ",limit:" + buffer.limit()).
                        forEach(System.out::println);
            }
            Arrays.asList(buffers).forEach(Buffer::flip);
            long bytesWritten = 0;
            while (bytesWritten < messageLength) {
                long write = channel.write(buffers);
                bytesWritten += write;
            }
            Arrays.asList(buffers).forEach(Buffer::clear);
            System.out.println("bytesRead:" + byteRead + ",bytesWritten:" + bytesWritten + ",messageLength:" + messageLength);
        }
    }
}
