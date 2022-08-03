package com.ttdo.core.redis.handler;


public interface IQueueHandler {
    void process(String message);
}
