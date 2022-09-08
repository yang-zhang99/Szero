package com.me.oauth;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.runner.RunWith;

import java.lang.reflect.Executable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TtdoOauthApplication.class)
public class TtdoOauthApplicationTests {


    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Test
    public void xxx() {

        ExecutorService service = Executors.newCachedThreadPool();


        for (int i = 0; i < 5; i++) {
            service.execute(() -> {
                while (true) {
                    for (int j = 0; j < 1000000000; j++) {
                        //                    System.out.println(zxAliOSSProperties.getBucket());
                        String x = stringRedisTemplate.opsForValue().get("DYNAMIC_CACHE:OSS_CONFIG:bucket");
                        System.out.println(x + " " + j +" " + Thread.currentThread().getName());
                    }

                }
            });
        }
        for (; ; ) ;
    }


}