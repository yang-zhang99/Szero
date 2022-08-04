package com.yang.redis.handler;


public interface IQueueHandler {
    void process(String message);
}
