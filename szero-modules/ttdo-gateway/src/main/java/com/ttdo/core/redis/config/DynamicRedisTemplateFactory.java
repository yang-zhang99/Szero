package com.ttdo.core.redis.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.data.redis.JedisClientConfigurationBuilderCustomizer;
import org.springframework.boot.autoconfigure.data.redis.LettuceClientConfigurationBuilderCustomizer;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.Assert;

import java.util.List;


/**
 * 动态 Redis 模板工厂模式
 *
 * @param <K>
 * @param <V>
 */
public class DynamicRedisTemplateFactory<K, V> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DynamicRedisTemplateFactory.class);
    private RedisProperties properties;
    private RedisSentinelConfiguration sentinelConfiguration;
    private RedisClusterConfiguration clusterConfiguration;
    private List<JedisClientConfigurationBuilderCustomizer> jedisBuilderCustomizers;
    private List<LettuceClientConfigurationBuilderCustomizer> lettuceBuilderCustomizers;

    private static final String REDIS_CLIENT_LETTUCE = "lettuce";
    private static final String REDIS_CLIENT_JEDIS = "jedis";

    public DynamicRedisTemplateFactory(RedisProperties properties, RedisSentinelConfiguration sentinelConfiguration, RedisClusterConfiguration clusterConfiguration, List<JedisClientConfigurationBuilderCustomizer> jedisBuilderCustomizers, List<LettuceClientConfigurationBuilderCustomizer> lettuceBuilderCustomizers) {
        this.properties = properties;
        this.sentinelConfiguration = sentinelConfiguration;
        this.clusterConfiguration = clusterConfiguration;
        this.jedisBuilderCustomizers = jedisBuilderCustomizers;
        this.lettuceBuilderCustomizers = lettuceBuilderCustomizers;
    }

    public RedisTemplate<K, V> createRedisTemplate(int database) {
        RedisConnectionFactory redisConnectionFactory = null;
        switch (this.getRedisClientType()) {
            case "lettuce":
                LettuceConnectionConfigure lettuceConnectionConfigure = new LettuceConnectionConfigure(this.properties, this.sentinelConfiguration, this.clusterConfiguration, this.lettuceBuilderCustomizers, database);
                redisConnectionFactory = lettuceConnectionConfigure.redisConnectionFactory();
                break;
            case "jedis":
                JedisConnectionConfigure jedisConnectionConfigure = new JedisConnectionConfigure(this.properties, this.sentinelConfiguration, this.clusterConfiguration, database, this.jedisBuilderCustomizers);
                redisConnectionFactory = jedisConnectionConfigure.redisConnectionFactory();
        }

        Assert.notNull(redisConnectionFactory, "redisConnectionFactory is null.");
        return this.createRedisTemplate(redisConnectionFactory);
    }

    private RedisTemplate<K, V> createRedisTemplate(RedisConnectionFactory factory) {
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        RedisTemplate<K, V> redisTemplate = new RedisTemplate();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setStringSerializer(stringRedisSerializer);
        redisTemplate.setDefaultSerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(stringRedisSerializer);
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    private String getRedisClientType() {
        try {
            Class.forName("io.lettuce.core.RedisClient");
            return REDIS_CLIENT_LETTUCE;
        } catch (ClassNotFoundException var3) {
            LOGGER.debug("Not Lettuce redis client");

            try {
                Class.forName("redis.clients.jedis.Jedis");
                return REDIS_CLIENT_JEDIS;
            } catch (ClassNotFoundException var2) {
                LOGGER.debug("Not Jedis redis client");
                throw new RuntimeException("redis client not found.");
            }
        }
    }

}
