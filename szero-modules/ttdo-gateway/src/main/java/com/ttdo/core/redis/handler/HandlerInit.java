package com.ttdo.core.redis.handler;


import com.ttdo.core.convertor.ApplicationContextHelper;
import com.ttdo.core.redis.RedisQueueHelper;
import com.ttdo.core.redis.YRedisProperties;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.util.ProxyUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class HandlerInit implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(HandlerInit.class);
    private final YRedisProperties redisProperties;
    private final RedisQueueHelper redisQueueHelper;

    public HandlerInit(RedisQueueHelper redisQueueHelper, YRedisProperties redisProperties) {
        this.redisQueueHelper = redisQueueHelper;
        this.redisProperties = redisProperties;
    }

    public void run(String... args) throws Exception {
        if (this.redisProperties != null && this.redisProperties.isRedisQueue()) {
            this.scanQueueHandler();
            ScheduledExecutorService register = new ScheduledThreadPoolExecutor(1, (new BasicThreadFactory.Builder()).namingPattern("redis-queue-consumer").daemon(true).build());
            register.scheduleAtFixedRate(new Listener(this.redisQueueHelper), 0L, this.redisProperties.getIntervals(), TimeUnit.SECONDS);
        }
    }

    private void scanQueueHandler() {
        Map<String, Object> map = ApplicationContextHelper.getContext().getBeansWithAnnotation(QueueHandler.class);
        Iterator<Object> value = map.values().iterator();
        while (true) {
            Object service;
            do {
                if (!value.hasNext()) {
                    return;
                }
                service = value.next();
            } while (!(service instanceof IQueueHandler) && !(service instanceof IBatchQueueHandler));

            QueueHandler queueHandler = ProxyUtils.getUserClass(service).getAnnotation(QueueHandler.class);
            if (ObjectUtils.isEmpty(queueHandler)) {
                logger.debug("could not get target bean , queueHandler : {}", service);
            } else {
                HandlerRegistry.addHandler(queueHandler.value(), service);
            }
        }
    }

    static class Consumer implements Runnable {
        private final String key;
        private final RedisQueueHelper redisQueueHelper;
        public Consumer(String key, RedisQueueHelper redisQueueHelper) {
            this.key = key;
            this.redisQueueHelper = redisQueueHelper;
        }

        public void run() {
            Object handler = HandlerRegistry.getHandler(this.key);
            if (handler != null) {
                if (handler instanceof IQueueHandler) {
                    while (true) {
                        String message = this.redisQueueHelper.pull(this.key);
                        if (StringUtils.isBlank(message)) {
                            return;
                        }
                        ((IQueueHandler) handler).process(message);
                    }
                } else {
                    if (handler instanceof IBatchQueueHandler) {
                        IBatchQueueHandler batchQueueHandler = (IBatchQueueHandler) handler;
                        int size = batchQueueHandler.getSize();
                        if (size > 0) {
                            while (true) {
                                List<String> list = this.redisQueueHelper.pullAll(this.key, size);
                                if (CollectionUtils.isEmpty(list)) {
                                    return;
                                }
                                batchQueueHandler.process(list);
                            }
                        }
                        batchQueueHandler.process(this.redisQueueHelper.pullAll(this.key));
                    }

                }
            }
        }
    }

    static class Listener implements Runnable {
        private final RedisQueueHelper redisQueueHelper;

        Listener(RedisQueueHelper redisQueueHelper) {
            this.redisQueueHelper = redisQueueHelper;
        }

        public void run() {
            Set<String> keys = HandlerRegistry.getKeySet();
            if (!CollectionUtils.isEmpty(keys)) {
                keys.forEach((key) -> {
                    HandlerRegistry.getThreadPool(key).execute(new Consumer(key, this.redisQueueHelper));
                });
            }
        }
    }
}
