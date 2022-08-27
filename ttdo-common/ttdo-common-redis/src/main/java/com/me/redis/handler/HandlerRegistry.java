package com.me.redis.handler;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class HandlerRegistry {
    private static Map<String, Object> handlerMap = new ConcurrentHashMap();
    private static Map<String, ThreadPoolExecutor> threadMap = new ConcurrentHashMap();

    private HandlerRegistry() {
    }

    public static void addHandler(String key, Object handler) {
        if (handlerMap.containsKey(key)) {
            if (handlerMap.get(key) instanceof IQueueHandler) {
                handlerMap.put(key, handler);
            }
        } else {
            handlerMap.put(key, handler);
        }

    }

    public static Object getHandler(String key) {
        return handlerMap.get(key);
    }

    public static Set<String> getKeySet() {
        return handlerMap.keySet();
    }

    public static ThreadPoolExecutor getThreadPool(String key) {
        if (!threadMap.containsKey(key)) {
            ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.SECONDS, new ArrayBlockingQueue(1), new ThreadPoolExecutor.DiscardPolicy());
            threadMap.put(key, executor);
        }

        return (ThreadPoolExecutor) threadMap.get(key);
    }
}
