//package com.me.core.redis;
//
//
//import com.me.iam.TtdoIamApplication;
//import io.netty.util.concurrent.ThreadPerTaskExecutor;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.Date;
//import java.util.concurrent.Executor;
//import java.util.concurrent.Executors;
//import java.util.concurrent.TimeUnit;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = TtdoIamApplication.class)
//class RedisConnection {
//
//    @Autowired
//    private StringRedisTemplate redisTemplate;
//
//    @Test
//    void contextLoads() {
//        Executor executor = Executors.newCachedThreadPool();
//
//        for (int i = 0; i < 1; i++) {
//            executor.execute(() -> {
//                for (int j = 0; j < 10000; j++) {
//                    try {
//                        TimeUnit.SECONDS.sleep(1);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//                    try {
//                        String x = redisTemplate.opsForValue().get("list");
//                        System.out.println(new Date() + " " +Thread.currentThread().getName() + " " + x);
//                    } catch (Exception e) {
//                        System.out.println("exception" + e);
//                    }
//
//                }
//            });
//        }
//        for (;;);
//    }
//
//}
