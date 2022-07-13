package com.ttdo.core.redis.config;

import io.lettuce.core.resource.ClientResources;
import io.lettuce.core.resource.DefaultClientResources;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.data.redis.LettuceClientConfigurationBuilderCustomizer;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.util.StringUtils;

public class LettuceConnectionConfigure extends RedisConnectionConfiguration {

    private final RedisProperties properties;
    private final List<LettuceClientConfigurationBuilderCustomizer> builderCustomizers;
    private final ClientResources clientResources;

    LettuceConnectionConfigure(RedisProperties properties,
                               RedisSentinelConfiguration sentinelConfigurationProvider,
                               RedisClusterConfiguration clusterConfigurationProvider,
                               List<LettuceClientConfigurationBuilderCustomizer> builderCustomizers,
                               int database) {
        super(properties, sentinelConfigurationProvider, clusterConfigurationProvider, database);
        this.properties = properties;
//        this.builderCustomizers = builderCustomizers.getIfAvailable(Collections::emptyList);
        this.builderCustomizers = builderCustomizers;
        this.clientResources = DefaultClientResources.create();
    }

    public LettuceConnectionFactory redisConnectionFactory() {
        LettuceClientConfiguration clientConfig = this.getLettuceClientConfiguration(this.clientResources, this.properties.getLettuce().getPool());
        return this.createLettuceConnectionFactory(clientConfig);
    }

    private LettuceConnectionFactory createLettuceConnectionFactory(LettuceClientConfiguration clientConfiguration) {
        LettuceConnectionFactory lettuceConnectionFactory;
        if (this.getSentinelConfig() != null) {
            lettuceConnectionFactory = new LettuceConnectionFactory(this.getSentinelConfig(), clientConfiguration);
        } else if (this.getClusterConfiguration() != null) {
            lettuceConnectionFactory = new LettuceConnectionFactory(this.getClusterConfiguration(), clientConfiguration);
        } else {
            lettuceConnectionFactory = new LettuceConnectionFactory(this.getStandaloneConfig(), clientConfiguration);
        }

        lettuceConnectionFactory.afterPropertiesSet();
        return lettuceConnectionFactory;
    }

    private LettuceClientConfiguration getLettuceClientConfiguration(ClientResources clientResources, RedisProperties.Pool pool) {
        LettuceClientConfiguration.LettuceClientConfigurationBuilder builder = this.createBuilder(pool);
        this.applyProperties(builder);
        if (StringUtils.hasText(this.properties.getUrl())) {
            this.customizeConfigurationFromUrl(builder);
        }

        builder.clientResources(clientResources);
        this.customize(builder);
        return builder.build();
    }

    private LettuceClientConfiguration.LettuceClientConfigurationBuilder createBuilder(RedisProperties.Pool pool) {
        return pool == null ? LettuceClientConfiguration.builder() : (new PoolBuilderFactory()).createBuilder(pool);
    }

    private LettuceClientConfiguration.LettuceClientConfigurationBuilder applyProperties(LettuceClientConfiguration.LettuceClientConfigurationBuilder builder) {
        if (this.properties.isSsl()) {
            builder.useSsl();
        }

        if (this.properties.getTimeout() != null) {
            builder.commandTimeout(this.properties.getTimeout());
        }

        if (this.properties.getLettuce() != null) {
            RedisProperties.Lettuce lettuce = this.properties.getLettuce();
            if (lettuce.getShutdownTimeout() != null && !lettuce.getShutdownTimeout().isZero()) {
                builder.shutdownTimeout(this.properties.getLettuce().getShutdownTimeout());
            }
        }

        return builder;
    }

    private void customizeConfigurationFromUrl(LettuceClientConfiguration.LettuceClientConfigurationBuilder builder) {
        RedisConnectionConfiguration.ConnectionInfo connectionInfo = this.parseUrl(this.properties.getUrl());
        if (connectionInfo.isUseSsl()) {
            builder.useSsl();
        }

    }

    private void customize(LettuceClientConfiguration.LettuceClientConfigurationBuilder builder) {
        Iterator var2 = this.builderCustomizers.iterator();

        while (var2.hasNext()) {
            LettuceClientConfigurationBuilderCustomizer customizer = (LettuceClientConfigurationBuilderCustomizer) var2.next();
            customizer.customize(builder);
        }

    }

    private static class PoolBuilderFactory {
        private PoolBuilderFactory() {
        }

        public LettuceClientConfiguration.LettuceClientConfigurationBuilder createBuilder(RedisProperties.Pool properties) {
            return LettucePoolingClientConfiguration.builder().poolConfig(this.getPoolConfig(properties));
        }

        private GenericObjectPoolConfig getPoolConfig(RedisProperties.Pool properties) {
            GenericObjectPoolConfig config = new GenericObjectPoolConfig();
            config.setMaxTotal(properties.getMaxActive());
            config.setMaxIdle(properties.getMaxIdle());
            config.setMinIdle(properties.getMinIdle());
            if (properties.getMaxWait() != null) {
                config.setMaxWaitMillis(properties.getMaxWait().toMillis());
            }

            return config;
        }
    }
}
