//package com.yang.redis.configure;
//
//import org.springframework.beans.factory.ObjectProvider;
//import org.springframework.boot.autoconfigure.data.redis.JedisClientConfigurationBuilderCustomizer;
//import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
//import org.springframework.data.redis.connection.RedisClusterConfiguration;
//import org.springframework.data.redis.connection.RedisSentinelConfiguration;
//import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.util.StringUtils;
//import redis.clients.jedis.JedisPoolConfig;
//
//import java.time.Duration;
//import java.util.Collections;
//import java.util.List;
//
///**
// * Jedis 的相关配置
// *   todo 目前没有使用
// */
//
//public class JedisConnectionConfigure extends RedisConnectionConfiguration {
//
//    private final RedisProperties properties;
//    private final List<JedisClientConfigurationBuilderCustomizer> builderCustomizers;
//
//    JedisConnectionConfigure(RedisProperties properties, ObjectProvider<RedisSentinelConfiguration> sentinelConfiguration, ObjectProvider<RedisClusterConfiguration> clusterConfiguration, ObjectProvider<List<JedisClientConfigurationBuilderCustomizer>> builderCustomizers, int database) {
//        super(properties, sentinelConfiguration, clusterConfiguration, database);
//        this.properties = properties;
//        this.builderCustomizers = builderCustomizers.getIfAvailable(Collections::emptyList);
//    }
//
//    // 获取 Redis 的配置文件
//    public JedisConnectionFactory redisConnectionFactory() {
//        return this.createJedisConnectionFactory();
//    }
//
//    // 创建 Jedis 的配置
//    private JedisConnectionFactory createJedisConnectionFactory() {
//        JedisClientConfiguration clientConfiguration = this.getJedisClientConfiguration();
//        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(this.getStandaloneConfig(), clientConfiguration);
//        jedisConnectionFactory.afterPropertiesSet();
//        return jedisConnectionFactory;
//
//    }
//
//    // 创建 Jedis 的配置
//    private JedisClientConfiguration getJedisClientConfiguration() {
//        JedisClientConfiguration.JedisClientConfigurationBuilder builder = this.applyProperties(JedisClientConfiguration.builder());
//        RedisProperties.Pool pool = this.properties.getJedis().getPool();
//        if (pool != null) {
//            this.applyPooling(pool, builder);
//        }
//
//        if (StringUtils.hasText(this.properties.getUrl())) {
//            this.customizeConfigurationFromUrl(builder);
//        }
//
//        this.customize(builder);
//        return builder.build();
//    }
//
//    // Jedis 的配置文件
//    private JedisClientConfiguration.JedisClientConfigurationBuilder applyProperties(JedisClientConfiguration.JedisClientConfigurationBuilder builder) {
//        if (this.properties.isSsl()) {
//            builder.useSsl();
//        }
//        if (this.properties.getTimeout() != null) {
//            Duration timeout = this.properties.getTimeout();
//            builder.readTimeout(timeout).connectTimeout(timeout);
//        }
//        return builder;
//    }
//
//    private void applyPooling(RedisProperties.Pool pool, JedisClientConfiguration.JedisClientConfigurationBuilder builder) {
//        builder.usePooling().poolConfig(this.jedisPoolConfig(pool));
//    }
//
//
//    private JedisPoolConfig jedisPoolConfig(RedisProperties.Pool pool) {
//        JedisPoolConfig config = new JedisPoolConfig();
//        config.setMaxTotal(pool.getMaxActive());
//        config.setMaxIdle(pool.getMaxIdle());
//        config.setMinIdle(pool.getMinIdle());
//        if (pool.getMaxWait() != null) {
//            config.setMaxWaitMillis(pool.getMaxWait().toMillis());
//        }
//        return config;
//    }
//
//    private void customizeConfigurationFromUrl(JedisClientConfiguration.JedisClientConfigurationBuilder builder) {
//        RedisConnectionConfiguration.ConnectionInfo connectionInfo = this.parseUrl(this.properties.getUrl());
//        if (connectionInfo.isUseSsl()) {
//            builder.useSsl();
//        }
//    }
//
//    private void customize(JedisClientConfiguration.JedisClientConfigurationBuilder builder) {
//        builderCustomizers.forEach(i -> {
//            i.customize(builder);
//        });
//    }
//}
