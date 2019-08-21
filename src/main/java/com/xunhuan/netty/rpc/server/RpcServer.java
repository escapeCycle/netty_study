package com.xunhuan.netty.rpc.server;

import com.xunhuan.netty.rpc.handler.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;

public class RpcServer {

    private String ip;
    private int port;
    private int ioThreads; //用来处理网络的读写线程
    private int workerThreads; //用于业务处理的计算线程


    public RpcServer(String ip, int port, int ioThreads, int workerThreads) {
        this.ip = ip;
        this.port = port;
        this.ioThreads = ioThreads;
        this.workerThreads = workerThreads;
    }

    private ServerBootstrap bootstrap;
    private EventLoopGroup group;
    private MessageCollector collector;
    private Channel serverChannel;

    // 注册服务的快捷方式
    public RpcServer service(String type, Class<?> reqClass, IMessageHandler<?> handler) {
        MessageRegistry.register(type, reqClass);
        MessageHandlers.register(type, handler);
        return this;
    }

    public void start() {
        ServerBootstrap bootstrap = new ServerBootstrap();

        NioEventLoopGroup group = new NioEventLoopGroup(ioThreads);

        bootstrap.group(group);

        collector = new MessageCollector(workerThreads);
        MessageEncoder encoder = new MessageEncoder();
        MessageDecoder decoder = new MessageDecoder();

        bootstrap.channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                // 如果客户端60秒没有任何请求，就关闭客户端链接
                pipeline.addLast(new ReadTimeoutHandler(60));
                pipeline.addLast(encoder);
                pipeline.addLast(decoder);
                pipeline.addLast(collector);
            }
        });
        bootstrap.option(ChannelOption.SO_BACKLOG, 100)  // 客户端套件字接受队列大小
                .option(ChannelOption.SO_REUSEADDR, true) // reuse addr，避免端口冲突
                .option(ChannelOption.TCP_NODELAY, true) // 关闭小流合并，保证消息的及时性
                .childOption(ChannelOption.SO_KEEPALIVE, true); // 长时间没动静的链接自动关闭

        serverChannel = bootstrap.bind(ip, port).channel();
        System.out.printf("server started @ %s:%d\n", ip, port);
    }

    public void stop() {
        //先关闭服务端套接字
        serverChannel.close();
        //斩断消息来源，停止io线程池
        group.shutdownGracefully();
        //停止业务线程
        collector.closeGracefully();
    }

}
