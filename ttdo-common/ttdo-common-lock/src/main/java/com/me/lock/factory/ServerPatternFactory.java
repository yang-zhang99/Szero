package com.me.lock.factory;

import com.me.core.exception.CommonException;
import com.me.lock.enums.ServerPattern;

import java.util.HashMap;
import java.util.Map;

public class ServerPatternFactory {
    private static Map<String, ServerPattern> serverPatternMap = new HashMap();

    public ServerPatternFactory() {
    }

    public static ServerPattern getServerPattern(String pattern) throws CommonException {
        ServerPattern serverPattern = (ServerPattern) serverPatternMap.get(pattern);
        if (serverPattern == null) {
            throw new CommonException("没有找到相应的服务器模式,请检测参数是否正常,pattern的值为:" + pattern, new Object[0]);
        } else {
            return serverPattern;
        }
    }

    static {
        serverPatternMap.put(ServerPattern.SINGLE.getPattern(), ServerPattern.SINGLE);
        serverPatternMap.put(ServerPattern.CLUSTER.getPattern(), ServerPattern.CLUSTER);
        serverPatternMap.put(ServerPattern.MASTER_SLAVE.getPattern(), ServerPattern.MASTER_SLAVE);
        serverPatternMap.put(ServerPattern.REPLICATED.getPattern(), ServerPattern.REPLICATED);
        serverPatternMap.put(ServerPattern.SENTINEL.getPattern(), ServerPattern.SENTINEL);
    }
}
