package com.me.core.redis;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
public class RedisConnection {


    @Autowired
    private StringRedisTemplate stringRedisTemplate;



}
