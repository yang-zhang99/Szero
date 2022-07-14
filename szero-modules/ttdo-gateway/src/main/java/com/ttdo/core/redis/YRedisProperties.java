package com.ttdo.core.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(
        prefix = "y.redis"
)
public class YRedisProperties {

    public static final String PREFIX = "y.redis";
    private boolean dynamicDatabase = true;
    private boolean redisQueue = false;
    private int queueDb = 1;
    private int intervals = 5;


    public boolean isDynamicDatabase() {
        return this.dynamicDatabase;
    }

    public void setDynamicDatabase(boolean dynamicDatabase) {
        this.dynamicDatabase = dynamicDatabase;
    }

    public int getQueueDb() {
        return this.queueDb;
    }

    public void setQueueDb(int queueDb) {
        this.queueDb = queueDb;
    }

    public boolean isRedisQueue() {
        return this.redisQueue;
    }

    public YRedisProperties setRedisQueue(boolean redisQueue) {
        this.redisQueue = redisQueue;
        return this;
    }

    public int getIntervals() {
        return this.intervals;
    }

    public YRedisProperties setIntervals(int intervals) {
        this.intervals = intervals;
        return this;
    }

}
