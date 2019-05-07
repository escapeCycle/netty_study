package com.xunhuan.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 *
 * 多客户端聊天-服务端
 * 收到客户端请求后,将消息推送至所有建立连接的客户端
 * @author tianhuan
 * @date 2019-04-27 21:12
 **/
public class NioServer {
    public static Map<String, SocketChannel> clientMap = new HashMap<>();

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        ServerSocket serverSocket = serverSocketChannel.socket();

        serverSocket.bind(new InetSocketAddress(8899));

        Selector selector = Selector.open();
        /**
         * 注册serverChannel到selector上,可以注册多个channel
         *
         * 关注连接事件
         */
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            /**
             * 阻塞住 等到真的有时间发生的时候 ,返回所关注的事件的数量
             */
            int select = selector.select();

            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            selectionKeys.forEach(selectionKey -> {
                System.out.println(selectionKey.channel().getClass() + ":" + selectionKey.isAcceptable() + ": " + selectionKey.isReadable());
                final SocketChannel client;
                try {
                    /**
                     * 发起的连接
                     */
                    if (selectionKey.isAcceptable()) {
                        ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
                        client = server.accept();
                        client.configureBlocking(false);
                        /**
                         * 关注读取事件
                         */
                        client.register(selector, SelectionKey.OP_READ);
                        /**
                         * 将客户端的信息放到服务器端保存
                         */
                        String key = "[" + UUID.randomUUID().toString() + "]";
                        clientMap.put(key, client);
                    } else if (selectionKey.isReadable()) {
                        client = (SocketChannel) selectionKey.channel();

                        ByteBuffer readBuffer = ByteBuffer.allocate(512);

                        int read = client.read(readBuffer);

                        if (read > 0) {
                            readBuffer.flip();
                            Charset charset = Charset.forName("UTF-8");
                            String receiveMessage = String.valueOf(charset.decode(readBuffer).array());
                            System.out.println(client + ": " + receiveMessage);
                            String sendKey = null;
                            for (Map.Entry<String, SocketChannel> entry : clientMap.entrySet()) {
                                if (client == entry.getValue()) {
                                    sendKey = entry.getKey();
                                    break;
                                }
                            }
                            for (Map.Entry<String, SocketChannel> entry : clientMap.entrySet()) {
                                SocketChannel socketChannel = entry.getValue();
                                ByteBuffer byteBuffer = ByteBuffer.allocate(512);
                                byteBuffer.put((sendKey + ": " + receiveMessage).getBytes());

                                byteBuffer.flip();
                                socketChannel.write(byteBuffer);
                            }
                        }
                    }
                    selectionKeys.clear();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
