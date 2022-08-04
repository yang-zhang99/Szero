//package com.yang.redis.configure;
//
//import org.springframework.beans.factory.ObjectProvider;
//import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
//import org.springframework.data.redis.connection.RedisClusterConfiguration;
//import org.springframework.data.redis.connection.RedisPassword;
//import org.springframework.data.redis.connection.RedisSentinelConfiguration;
//import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
//import org.springframework.util.StringUtils;
//
//import java.net.URI;
//import java.net.URISyntaxException;
//
///**
// * Redis 配置抽象类 用于 redis 动态数据源切换使用
// * todo 目前没有使用
// */
//abstract class RedisConnectionConfiguration {
//
//
//    // Redis 的配置属性
//    private final RedisProperties properties;
//    // Redis 的集群模式
//    private final RedisSentinelConfiguration sentinelConfiguration;
//    // Redis 的哨兵模式
//    private final RedisClusterConfiguration clusterConfiguration;
//    // Redis 的数据库号
//    private final int database;
//
//    protected RedisConnectionConfiguration(RedisProperties properties, ObjectProvider<RedisSentinelConfiguration> sentinelConfigurationProvider, ObjectProvider<RedisClusterConfiguration> clusterConfigurationProvider, int database) {
//        this.properties = properties;
//        this.sentinelConfiguration = sentinelConfigurationProvider.getIfAvailable();
//        this.clusterConfiguration = clusterConfigurationProvider.getIfAvailable();
//        this.database = database;
//    }
//
//
//    // Redis 单例模式
//    protected final RedisStandaloneConfiguration getStandaloneConfig() {
//        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
//        if (StringUtils.hasText(this.properties.getUrl())) {
//            ConnectionInfo connectionInfo = this.parseUrl(this.properties.getUrl());
//            config.setHostName(connectionInfo.getHostName());
//            config.setPort(connectionInfo.getPort());
//            config.setPassword(RedisPassword.of(connectionInfo.getPassword()));
//        } else {
//            config.setHostName(this.properties.getHost());
//            config.setPort(this.properties.getPort());
//            config.setPassword(RedisPassword.of(this.properties.getPassword()));
//        }
//
//        config.setDatabase(this.database);
//        return config;
//    }
//
//    // Redis 集群模式 哨兵模式配置 系统不使用，不做配置
//
//
//    // url 转移
//    protected ConnectionInfo parseUrl(String url) {
//        try {
//            URI uri = new URI(url);
//            boolean useSsl = url.startsWith("rediss://");
//            String password = null;
//            if (uri.getUserInfo() != null) {
//                password = uri.getUserInfo();
//                int index = password.indexOf(58);
//                if (index >= 0) {
//                    password = password.substring(index + 1);
//                }
//            }
//
//            return new ConnectionInfo(uri, useSsl, password);
//        } catch (URISyntaxException var6) {
//            throw new IllegalArgumentException("Malformed url '" + url + "'", var6);
//        }
//    }
//
//    // 连接信息
//    protected static class ConnectionInfo {
//        private final URI uri;
//        private final boolean useSsl;
//        private final String password;
//
//        public ConnectionInfo(URI uri, boolean useSsl, String password) {
//            this.uri = uri;
//            this.useSsl = useSsl;
//            this.password = password;
//        }
//
//        public boolean isUseSsl() {
//            return this.useSsl;
//        }
//
//        public String getHostName() {
//            return this.uri.getHost();
//        }
//
//        public int getPort() {
//            return this.uri.getPort();
//        }
//
//        public String getPassword() {
//            return this.password;
//        }
//    }
//
//}
