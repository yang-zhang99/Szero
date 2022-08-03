package com.ttdo.core.redis;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
public class RedisConnection {


    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Test
    void contextLoads() {

        System.out.println(stringRedisTemplate.opsForSet().add("test","redis"));


    }

}
