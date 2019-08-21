package com.xunhuan.netty.rpc.handler;

import java.util.HashMap;
import java.util.Map;

public class MessageHandlers {

    private static Map<String, IMessageHandler<?>> handlers = new HashMap<>();
    public static DefaultHandler defaultHandler = new DefaultHandler();

    public static void register(String type, IMessageHandler<?> handler) {
        handlers.put(type, handler);
    }

    public static IMessageHandler<?> get(String type){
        return handlers.get(type);
    }
}
