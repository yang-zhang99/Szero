package com.yang.redis.handler;

import java.util.List;

public interface IBatchQueueHandler {
    default int getSize() {
        return 500;
    }

    void process(List<String> messages);
}
