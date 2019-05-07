package com.xunhuan.netty.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 多客户端聊天-客户端
 *
 * @author tianhuan
 * @date 2019-04-27 22:54
 **/
public class NioClient {

    public static void main(String[] args) throws IOException {
        try {
            SocketChannel socketChannel = SocketChannel.open();

            socketChannel.configureBlocking(false);

            Selector selector = Selector.open();

            socketChannel.register(selector, SelectionKey.OP_CONNECT);

            socketChannel.connect(new InetSocketAddress("127.0.0.1", 8899));

            while (true) {
                selector.select();

                Set<SelectionKey> selectionKeys = selector.selectedKeys();

                selectionKeys.forEach(selectionKey -> {
                    try {
                        if (selectionKey.isConnectable()) {
                            SocketChannel client = (SocketChannel) selectionKey.channel();

                            if (client.isConnectionPending()) {

                                client.finishConnect();
                                ByteBuffer byteBuffer = ByteBuffer.allocate(512);
                                byteBuffer.put((LocalDateTime.now() + " 连接成功").getBytes());

                                byteBuffer.flip();

                                client.write(byteBuffer);

                                ExecutorService executorService = Executors.newSingleThreadExecutor(Executors.defaultThreadFactory());
                                executorService.submit(() -> {
                                    while (true) {
                                        try {
                                            byteBuffer.clear();
                                            InputStreamReader input = new InputStreamReader(System.in);

                                            BufferedReader reader = new BufferedReader(input);
                                            String message = reader.readLine();

                                            byteBuffer.put(message.getBytes());
                                            byteBuffer.flip();
                                            client.write(byteBuffer);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                            }
                            client.register(selector, SelectionKey.OP_READ);
                        } else if (selectionKey.isReadable()) {
                            SocketChannel client = (SocketChannel) selectionKey.channel();
                            ByteBuffer readBuffer = ByteBuffer.allocate(512);

                            int read = client.read(readBuffer);

                            if (read > 0) {
                                String message = new String(readBuffer.array(), 0, read);
                                System.out.println(message);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                selectionKeys.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
