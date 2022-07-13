package com.ttdo.core.redis.config;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.data.redis.connection.*;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public abstract class RedisConnectionConfiguration {


    // Redis 的配置属性
    private final RedisProperties properties;
    // Redis 的集群模式
    private final RedisSentinelConfiguration sentinelConfiguration;
    // Redis 的哨兵模式
    private final RedisClusterConfiguration clusterConfiguration;
    // Redis 的数据库号
    private final int database;

    protected RedisConnectionConfiguration(RedisProperties properties, RedisSentinelConfiguration sentinelConfiguration, RedisClusterConfiguration clusterConfiguration, int database) {
        this.properties = properties;
        this.sentinelConfiguration = sentinelConfiguration;
        this.clusterConfiguration = clusterConfiguration;
        this.database = database;
    }

    // Redis 单例模式
    protected final RedisStandaloneConfiguration getStandaloneConfig() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        if (StringUtils.hasText(this.properties.getUrl())) {
            ConnectionInfo connectionInfo = this.parseUrl(this.properties.getUrl());
            config.setHostName(connectionInfo.getHostName());
            config.setPort(connectionInfo.getPort());
            config.setPassword(RedisPassword.of(connectionInfo.getPassword()));
        } else {
            config.setHostName(this.properties.getHost());
            config.setPort(this.properties.getPort());
            config.setPassword(RedisPassword.of(this.properties.getPassword()));
        }

        config.setDatabase(this.database);
        return config;
    }

    // Redis 集群模式
    protected final RedisSentinelConfiguration getSentinelConfig() {
        if (this.sentinelConfiguration != null) {
            return this.sentinelConfiguration;
        } else {
            RedisProperties.Sentinel sentinelProperties = this.properties.getSentinel();
            if (sentinelProperties != null) {
                RedisSentinelConfiguration config = new RedisSentinelConfiguration();
                config.master(sentinelProperties.getMaster());
                config.setSentinels(this.createSentinels(sentinelProperties));
                if (this.properties.getPassword() != null) {
                    config.setPassword(RedisPassword.of(this.properties.getPassword()));
                }

                config.setDatabase(this.database);
                return config;
            } else {
                return null;
            }
        }
    }

    // 哨兵模式配置
    protected final RedisClusterConfiguration getClusterConfiguration() {
        if (this.clusterConfiguration != null) {
            return this.clusterConfiguration;
        } else if (this.properties.getCluster() == null) {
            return null;
        } else {
            RedisProperties.Cluster clusterProperties = this.properties.getCluster();
            RedisClusterConfiguration config = new RedisClusterConfiguration(clusterProperties.getNodes());
            if (clusterProperties.getMaxRedirects() != null) {
                config.setMaxRedirects(clusterProperties.getMaxRedirects());
            }
            if (this.properties.getPassword() != null) {
                config.setPassword(RedisPassword.of(this.properties.getPassword()));
            }
            return config;
        }
    }

    // 集群其他节点
    private List<RedisNode> createSentinels(RedisProperties.Sentinel sentinel) {
        List<RedisNode> nodes = new ArrayList();

        sentinel.getNodes().forEach(node -> {
            try {
                String[] parts = StringUtils.split(node, ":");
                Assert.state(parts.length == 2, "Must be defined as 'host:port'");
                nodes.add(new RedisNode(parts[0], Integer.parseInt(parts[1])));
            } catch (RuntimeException var6) {
                throw new IllegalStateException("Invalid redis sentinel property '" + node + "'", var6);
            }
        });
        return nodes;
    }

    // url 转移
    protected ConnectionInfo parseUrl(String url) {
        try {
            URI uri = new URI(url);
            boolean useSsl = url.startsWith("rediss://");
            String password = null;
            if (uri.getUserInfo() != null) {
                password = uri.getUserInfo();
                int index = password.indexOf(58);
                if (index >= 0) {
                    password = password.substring(index + 1);
                }
            }

            return new ConnectionInfo(uri, useSsl, password);
        } catch (URISyntaxException var6) {
            throw new IllegalArgumentException("Malformed url '" + url + "'", var6);
        }
    }

    // 连接信息
    protected static class ConnectionInfo {
        private final URI uri;
        private final boolean useSsl;
        private final String password;

        public ConnectionInfo(URI uri, boolean useSsl, String password) {
            this.uri = uri;
            this.useSsl = useSsl;
            this.password = password;
        }

        public boolean isUseSsl() {
            return this.useSsl;
        }

        public String getHostName() {
            return this.uri.getHost();
        }

        public int getPort() {
            return this.uri.getPort();
        }

        public String getPassword() {
            return this.password;
        }
    }
}
