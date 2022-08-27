package com.me.redis.handler;


public interface IQueueHandler {
    void process(String message);
}
