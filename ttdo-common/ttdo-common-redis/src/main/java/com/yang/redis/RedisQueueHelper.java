package com.yang.redis;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Redis 队列帮助类
 */
public class RedisQueueHelper {

    private static final String PREFIX = "wlhy-queue:";

    private final RedisHelper redisHelper;

    private final WlhyRedisProperties properties;

    public RedisQueueHelper(RedisHelper redisHelper, WlhyRedisProperties redisProperties) {
        this.redisHelper = redisHelper;
        this.properties = redisProperties;
    }

    public void push(String key, String message) {
        this.redisHelper.setCurrentDatabase(this.properties.getQueueDb());
        this.redisHelper.lstRightPush(PREFIX+ key, message);
        this.redisHelper.clearCurrentDatabase();
    }

    public void pushAll(String key, Collection<String> messages) {
        this.redisHelper.setCurrentDatabase(this.properties.getQueueDb());
        this.redisHelper.lstRightPushAll(PREFIX+ key, messages);
        this.redisHelper.clearCurrentDatabase();
    }

    public void push(String key, String message, boolean right) {
        this.redisHelper.setCurrentDatabase(this.properties.getQueueDb());
        if (right) {
            this.redisHelper.lstRightPush(PREFIX+ key, message);
        } else {
            this.redisHelper.lstLeftPush(PREFIX+ key, message);
        }

        this.redisHelper.clearCurrentDatabase();
    }

    public void pushAll(String key, Collection<String> messages, boolean right) {
        this.redisHelper.setCurrentDatabase(this.properties.getQueueDb());
        if (right) {
            this.redisHelper.lstRightPushAll(PREFIX+ key, messages);
        } else {
            this.redisHelper.lstLeftPushAll(PREFIX+ key, messages);
        }

        this.redisHelper.clearCurrentDatabase();
    }

    public String pull(String key) {
        this.redisHelper.setCurrentDatabase(this.properties.getQueueDb());
        String str = this.redisHelper.lstLeftPop(PREFIX+ key);
        this.redisHelper.clearCurrentDatabase();
        return str;
    }

    public List<String> pullAll(String key) {
        this.redisHelper.setCurrentDatabase(this.properties.getQueueDb());
        List<String> result = new ArrayList();

        while(true) {
            String str = this.pull(key);
            if (str == null) {
                this.redisHelper.clearCurrentDatabase();
                return result;
            }

            result.add(str);
        }
    }

    public List<String> pullAll(String key, int maxSize) {
        this.redisHelper.setCurrentDatabase(this.properties.getQueueDb());
        List<String> result = new ArrayList();

        while(true) {
            String str = this.pull(key);
            if (str == null || result.size() > maxSize) {
                this.redisHelper.clearCurrentDatabase();
                return result;
            }

            result.add(str);
        }
    }

    public String pull(String key, boolean left) {
        this.redisHelper.setCurrentDatabase(this.properties.getQueueDb());
        String str;
        if (left) {
            str = this.redisHelper.lstLeftPop(PREFIX+ key);
        } else {
            str = this.redisHelper.lstRightPop(PREFIX+ key);
        }

        this.redisHelper.clearCurrentDatabase();
        return str;
    }

    public List<String> pullAll(String key, boolean left) {
        this.redisHelper.setCurrentDatabase(this.properties.getQueueDb());
        List<String> result = new ArrayList();

        while(true) {
            String str = this.pull(key, left);
            if (str == null) {
                this.redisHelper.clearCurrentDatabase();
                return result;
            }

            result.add(str);
        }
    }

    public List<String> pullAll(String key, boolean left, int max) {
        this.redisHelper.setCurrentDatabase(this.properties.getQueueDb());
        List<String> result = new ArrayList();

        while(true) {
            String str = this.pull(key, left);
            if (str == null || result.size() > max) {
                this.redisHelper.clearCurrentDatabase();
                return result;
            }

            result.add(str);
        }
    }
}
