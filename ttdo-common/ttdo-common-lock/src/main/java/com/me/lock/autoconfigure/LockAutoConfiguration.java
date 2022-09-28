package com.me.lock.autoconfigure;


import com.me.lock.LockAspectHandler;
import com.me.lock.LockInfoProvider;
import com.me.lock.configure.LockConfigProperties;
import com.me.lock.configure.LockConfigProperties.*;
import com.me.lock.enums.ServerPattern;
import com.me.lock.factory.LockServiceFactory;
import com.me.lock.factory.ServerPatternFactory;
import com.me.lock.service.impl.*;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.*;
import org.redisson.connection.balancer.LoadBalancer;
import org.redisson.connection.balancer.RandomLoadBalancer;
import org.redisson.connection.balancer.RoundRobinLoadBalancer;
import org.redisson.connection.balancer.WeightedRoundRobinBalancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;
import org.springframework.util.Assert;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yang99
 * 自动装配 分布式锁
 */
@Configuration
@AutoConfigureAfter({RedisAutoConfiguration.class})
@EnableConfigurationProperties(LockConfigProperties.class)
@Import({LockAspectHandler.class})
public class LockAutoConfiguration {
    @Autowired
    private LockConfigProperties lockConfig;

    @Bean(
            name = {"lockRedissonClient"},
            destroyMethod = "shutdown"
    )
    @ConditionalOnMissingBean
    RedissonClient redisson() throws Exception {
        Config config = new Config();
        config.setThreads(this.lockConfig.getThreads());
        config.setNettyThreads(this.lockConfig.getNettyThreads());
        config.setLockWatchdogTimeout(this.lockConfig.getLockWatchdogTimeout());
        config.setKeepPubSubOrder(this.lockConfig.getKeepPubSubOrder());
        config.setUseScriptCache(this.lockConfig.getUseScriptCache());
        ServerPattern serverPattern = ServerPatternFactory.getServerPattern(this.lockConfig.getPattern());
        switch (serverPattern) {
            case SINGLE:
                SingleServerConfig singleServerConfig = config.useSingleServer();
                this.initSingleConfig(singleServerConfig);
                break;
            case CLUSTER:
                ClusterServersConfig clusterConfig = config.useClusterServers();
                this.initClusterConfig(clusterConfig);
                break;
            case MASTER_SLAVE:
                MasterSlaveServersConfig masterSlaveConfig = config.useMasterSlaveServers();
                this.initMasterSlaveConfig(masterSlaveConfig);
                break;
            case REPLICATED:
                ReplicatedServersConfig replicatedServersConfig = config.useReplicatedServers();
                this.initReplicatedServersConfig(replicatedServersConfig);
                break;
            case SENTINEL:
                SentinelServersConfig sentinelServersConfig = config.useSentinelServers();
                this.initSentinelServersConfig(sentinelServersConfig);
        }

        return Redisson.create(config);
    }

    @Bean
    public LockInfoProvider lockInfoProvider() {
        return new LockInfoProvider();
    }

    @Bean
    public LockServiceFactory lockFactory() {
        return new LockServiceFactory();
    }

    @Bean
    @Scope("prototype")
    public ReentrantLockServiceImpl reentrantLockServiceImpl() {
        return new ReentrantLockServiceImpl();
    }

    @Bean
    @Scope("prototype")
    public FairLockServiceImpl fairLockServiceImpl() {
        return new FairLockServiceImpl();
    }

    @Bean
    @Scope("prototype")
    public ReadLockServiceImpl readLockServiceImpl() {
        return new ReadLockServiceImpl();
    }

    @Bean
    @Scope("prototype")
    public WriteLockServiceImpl writeLockServiceImpl() {
        return new WriteLockServiceImpl();
    }

    @Bean
    @Scope("prototype")
    public MultiLockServiceImpl multiLockService() {
        return new MultiLockServiceImpl();
    }

    @Bean
    @Scope("prototype")
    public RedLockServiceImpl redLockService() {
        return new RedLockServiceImpl();
    }

    private void initSingleConfig(SingleServerConfig singleServerConfig) throws URISyntaxException, MalformedURLException {
        SingleConfig singleConfig = this.lockConfig.getSingleServer();
        singleServerConfig.setAddress(String.format("%s%s%s%s", "redis://", singleConfig.getAddress(), ":", singleConfig.getPort()));
        singleServerConfig.setClientName(this.lockConfig.getClientName());
        singleServerConfig.setConnectionMinimumIdleSize(singleConfig.getConnMinIdleSize());
        singleServerConfig.setConnectionPoolSize(singleConfig.getConnPoolSize());
        singleServerConfig.setConnectTimeout(singleConfig.getConnTimeout());
        singleServerConfig.setDatabase(singleConfig.getDatabase());
        singleServerConfig.setDnsMonitoringInterval((long) singleConfig.getDnsMonitoringInterval());
        singleServerConfig.setIdleConnectionTimeout(singleConfig.getIdleConnTimeout());
        singleServerConfig.setKeepAlive(singleConfig.isKeepAlive());
        if (StringUtils.isNotBlank(singleConfig.getPassword())) {
            singleServerConfig.setPassword(singleConfig.getPassword());
        }

        singleServerConfig.setRetryAttempts(singleConfig.getRetryAttempts());
        singleServerConfig.setRetryInterval(singleConfig.getRetryInterval());
        singleServerConfig.setSslEnableEndpointIdentification(this.lockConfig.isSslEnableEndpointIdentification());
        if (this.lockConfig.getSslKeystore() != null) {
            singleServerConfig.setSslKeystore(new URL(this.lockConfig.getSslKeystore()));
        }

        if (this.lockConfig.getSslKeystorePassword() != null) {
            singleServerConfig.setSslKeystorePassword(this.lockConfig.getSslKeystorePassword());
        }

        singleServerConfig.setSslProvider("JDK".equalsIgnoreCase(this.lockConfig.getSslProvider()) ? SslProvider.JDK : SslProvider.OPENSSL);
    }

    private void initClusterConfig(ClusterServersConfig clusterServerConfig) {
        ClusterConfig clusterConfig = this.lockConfig.getClusterServer();
        String[] addressArr = clusterConfig.getNodeAddresses().split(",");
        Arrays.asList(addressArr).forEach((address) -> {
            clusterServerConfig.addNodeAddress(new String[]{String.format("%s%s", "redis://", address)});
        });
        clusterServerConfig.setScanInterval(clusterConfig.getScanInterval());
        ReadMode readMode = this.getReadMode(clusterConfig.getReadMode());
        Assert.notNull(readMode, "Unknown load balancing mode type for read operations");
        clusterServerConfig.setReadMode(readMode);
        SubscriptionMode subscriptionMode = this.getSubscriptionMode(clusterConfig.getSubMode());
        Assert.notNull(subscriptionMode, "The type of load balancing pattern for an unknown subscription operation");
        clusterServerConfig.setSubscriptionMode(subscriptionMode);
        LoadBalancer loadBalancer = this.getLoadBalancer(clusterConfig.getLoadBalancer(), clusterConfig.getWeightMaps(), clusterConfig.getDefaultWeight());
        Assert.notNull(loadBalancer, "Unknown type of load balancing algorithm");
        clusterServerConfig.setLoadBalancer(loadBalancer);
        clusterServerConfig.setSubscriptionConnectionMinimumIdleSize(clusterConfig.getSubConnMinIdleSize());
        clusterServerConfig.setSubscriptionConnectionPoolSize(clusterConfig.getSubConnPoolSize());
        clusterServerConfig.setSlaveConnectionMinimumIdleSize(clusterConfig.getSlaveConnMinIdleSize());
        clusterServerConfig.setSlaveConnectionPoolSize(clusterConfig.getSlaveConnPoolSize());
        clusterServerConfig.setMasterConnectionMinimumIdleSize(clusterConfig.getMasterConnMinIdleSize());
        clusterServerConfig.setMasterConnectionPoolSize(clusterConfig.getMasterConnPoolSize());
        clusterServerConfig.setIdleConnectionTimeout(clusterConfig.getIdleConnTimeout());
        clusterServerConfig.setConnectTimeout(clusterConfig.getConnTimeout());
        clusterServerConfig.setTimeout(clusterConfig.getTimeout());
        clusterServerConfig.setRetryAttempts(clusterConfig.getRetryAttempts());
        clusterServerConfig.setRetryInterval(clusterConfig.getRetryInterval());
        if (StringUtils.isNotBlank(clusterConfig.getPassword())) {
            clusterServerConfig.setPassword(clusterConfig.getPassword());
        }

        clusterServerConfig.setSubscriptionsPerConnection(clusterConfig.getSubPerConn());
        clusterServerConfig.setClientName(this.lockConfig.getClientName());
    }

    private void initSentinelServersConfig(SentinelServersConfig sentinelServersConfig) throws URISyntaxException, MalformedURLException {
        SentinelConfig sentinelConfig = this.lockConfig.getSentinelServer();
        String[] addressArr = sentinelConfig.getSentinelAddresses().split(",");
        Arrays.asList(addressArr).forEach((address) -> {
            sentinelServersConfig.addSentinelAddress(new String[]{String.format("%s%s", "redis://", address)});
        });
        ReadMode readMode = this.getReadMode(sentinelConfig.getReadMode());
        Assert.notNull(readMode, "Unknown load balancing mode type for read operations");
        sentinelServersConfig.setReadMode(readMode);
        SubscriptionMode subscriptionMode = this.getSubscriptionMode(sentinelConfig.getSubMode());
        Assert.notNull(subscriptionMode, "The type of load balancing pattern for an unknown subscription operation");
        sentinelServersConfig.setSubscriptionMode(subscriptionMode);
        LoadBalancer loadBalancer = this.getLoadBalancer(sentinelConfig.getLoadBalancer(), sentinelConfig.getWeightMaps(), sentinelConfig.getDefaultWeight());
        Assert.notNull(loadBalancer, "Unknown type of load balancing algorithm");
        sentinelServersConfig.setLoadBalancer(loadBalancer);
        sentinelServersConfig.setMasterName(sentinelConfig.getMasterName());
        sentinelServersConfig.setDatabase(sentinelConfig.getDatabase());
        sentinelServersConfig.setSlaveConnectionPoolSize(sentinelConfig.getSlaveConnectionPoolSize());
        sentinelServersConfig.setMasterConnectionPoolSize(sentinelConfig.getMasterConnectionPoolSize());
        sentinelServersConfig.setSubscriptionConnectionPoolSize(sentinelConfig.getSubscriptionConnectionPoolSize());
        sentinelServersConfig.setSlaveConnectionMinimumIdleSize(sentinelConfig.getSlaveConnectionMinimumIdleSize());
        sentinelServersConfig.setMasterConnectionMinimumIdleSize(sentinelConfig.getMasterConnectionMinimumIdleSize());
        sentinelServersConfig.setSubscriptionConnectionMinimumIdleSize(sentinelConfig.getSubscriptionConnectionMinimumIdleSize());
        sentinelServersConfig.setDnsMonitoringInterval(sentinelConfig.getDnsMonitoringInterval());
        sentinelServersConfig.setSubscriptionsPerConnection(sentinelConfig.getSubscriptionsPerConnection());
        if (StringUtils.isNotBlank(sentinelConfig.getPassword())) {
            sentinelServersConfig.setPassword(sentinelConfig.getPassword());
        }

        sentinelServersConfig.setRetryAttempts(sentinelConfig.getRetryAttempts());
        sentinelServersConfig.setRetryInterval(sentinelConfig.getRetryInterval());
        sentinelServersConfig.setTimeout(sentinelConfig.getTimeout());
        sentinelServersConfig.setConnectTimeout(sentinelConfig.getConnectTimeout());
        sentinelServersConfig.setIdleConnectionTimeout(sentinelConfig.getIdleConnectionTimeout());
        this.setLockSslConfigAndClientName(sentinelServersConfig);
    }

    private void initReplicatedServersConfig(ReplicatedServersConfig replicatedServersConfig) throws URISyntaxException, MalformedURLException {
        ReplicatedConfig replicatedConfig = this.lockConfig.getReplicatedServer();
        String[] addressArr = replicatedConfig.getNodeAddresses().split(",");
        Arrays.asList(addressArr).forEach((address) -> {
            replicatedServersConfig.addNodeAddress(new String[]{String.format("%s%s", "redis://", address)});
        });
        ReadMode readMode = this.getReadMode(replicatedConfig.getReadMode());
        Assert.notNull(readMode, "Unknown load balancing mode type for read operations");
        replicatedServersConfig.setReadMode(readMode);
        SubscriptionMode subscriptionMode = this.getSubscriptionMode(replicatedConfig.getSubscriptionMode());
        Assert.notNull(subscriptionMode, "The type of load balancing pattern for an unknown subscription operation");
        replicatedServersConfig.setSubscriptionMode(subscriptionMode);
        LoadBalancer loadBalancer = this.getLoadBalancer(replicatedConfig.getLoadBalancer(), replicatedConfig.getWeightMaps(), replicatedConfig.getDefaultWeight());
        Assert.notNull(loadBalancer, "Unknown type of load balancing algorithm");
        replicatedServersConfig.setLoadBalancer(loadBalancer);
        replicatedServersConfig.setScanInterval(replicatedConfig.getScanInterval());
        replicatedServersConfig.setDatabase(replicatedConfig.getDatabase());
        replicatedServersConfig.setSlaveConnectionPoolSize(replicatedConfig.getSlaveConnectionPoolSize());
        replicatedServersConfig.setMasterConnectionPoolSize(replicatedConfig.getMasterConnectionPoolSize());
        replicatedServersConfig.setSubscriptionConnectionPoolSize(replicatedConfig.getSubscriptionConnectionPoolSize());
        replicatedServersConfig.setSlaveConnectionMinimumIdleSize(replicatedConfig.getSlaveConnectionMinimumIdleSize());
        replicatedServersConfig.setMasterConnectionMinimumIdleSize(replicatedConfig.getMasterConnectionMinimumIdleSize());
        replicatedServersConfig.setSubscriptionConnectionMinimumIdleSize(replicatedConfig.getSubscriptionConnectionMinimumIdleSize());
        replicatedServersConfig.setDnsMonitoringInterval(replicatedConfig.getDnsMonitoringInterval());
        replicatedServersConfig.setSubscriptionsPerConnection(replicatedConfig.getSubscriptionsPerConnection());
        if (StringUtils.isNotBlank(replicatedConfig.getPassword())) {
            replicatedServersConfig.setPassword(replicatedConfig.getPassword());
        }

        replicatedServersConfig.setRetryAttempts(replicatedConfig.getRetryAttempts());
        replicatedServersConfig.setRetryInterval(replicatedConfig.getRetryInterval());
        replicatedServersConfig.setTimeout(replicatedConfig.getTimeout());
        replicatedServersConfig.setConnectTimeout(replicatedConfig.getConnectTimeout());
        replicatedServersConfig.setIdleConnectionTimeout(replicatedConfig.getIdleConnectionTimeout());
        this.setLockSslConfigAndClientName(replicatedServersConfig);
    }

    private void initMasterSlaveConfig(MasterSlaveServersConfig masterSlaveServersConfig) throws URISyntaxException, MalformedURLException {
        MasterSlaveConfig masterSlaveConfig = this.lockConfig.getMasterSlaveServer();
        masterSlaveServersConfig.setMasterAddress(String.format("%s%s", "redis://", masterSlaveConfig.getMasterAddress()));
        String[] addressArr = masterSlaveConfig.getSlaveAddresses().split(",");
        Arrays.asList(addressArr).forEach((address) -> {
            masterSlaveServersConfig.addSlaveAddress(new String[]{String.format("%s%s", "redis://", address)});
        });
        ReadMode readMode = this.getReadMode(masterSlaveConfig.getReadMode());
        Assert.notNull(readMode, "Unknown load balancing mode type for read operations");
        masterSlaveServersConfig.setReadMode(readMode);
        SubscriptionMode subscriptionMode = this.getSubscriptionMode(masterSlaveConfig.getSubMode());
        Assert.notNull(subscriptionMode, "The type of load balancing pattern for an unknown subscription operation");
        masterSlaveServersConfig.setSubscriptionMode(subscriptionMode);
        LoadBalancer loadBalancer = this.getLoadBalancer(masterSlaveConfig.getLoadBalancer(), masterSlaveConfig.getWeightMaps(), masterSlaveConfig.getDefaultWeight());
        Assert.notNull(loadBalancer, "Unknown type of load balancing algorithm");
        masterSlaveServersConfig.setLoadBalancer(loadBalancer);
        masterSlaveServersConfig.setDatabase(masterSlaveConfig.getDatabase());
        masterSlaveServersConfig.setSlaveConnectionPoolSize(masterSlaveConfig.getSlaveConnectionPoolSize());
        masterSlaveServersConfig.setMasterConnectionPoolSize(masterSlaveConfig.getMasterConnectionPoolSize());
        masterSlaveServersConfig.setSubscriptionConnectionPoolSize(masterSlaveConfig.getSubscriptionConnectionPoolSize());
        masterSlaveServersConfig.setSlaveConnectionMinimumIdleSize(masterSlaveConfig.getSlaveConnectionMinimumIdleSize());
        masterSlaveServersConfig.setMasterConnectionMinimumIdleSize(masterSlaveConfig.getMasterConnectionMinimumIdleSize());
        masterSlaveServersConfig.setSubscriptionConnectionMinimumIdleSize(masterSlaveConfig.getSubscriptionConnectionMinimumIdleSize());
        masterSlaveServersConfig.setDnsMonitoringInterval(masterSlaveConfig.getDnsMonitoringInterval());
        masterSlaveServersConfig.setSubscriptionsPerConnection(masterSlaveConfig.getSubscriptionsPerConnection());
        if (StringUtils.isNotBlank(masterSlaveConfig.getPassword())) {
            masterSlaveServersConfig.setPassword(masterSlaveConfig.getPassword());
        }

        masterSlaveServersConfig.setRetryAttempts(masterSlaveConfig.getRetryAttempts());
        masterSlaveServersConfig.setRetryInterval(masterSlaveConfig.getRetryInterval());
        masterSlaveServersConfig.setTimeout(masterSlaveConfig.getTimeout());
        masterSlaveServersConfig.setConnectTimeout(masterSlaveConfig.getConnectTimeout());
        masterSlaveServersConfig.setIdleConnectionTimeout(masterSlaveConfig.getIdleConnectionTimeout());
        this.setLockSslConfigAndClientName(masterSlaveServersConfig);
    }

    private LoadBalancer getLoadBalancer(String loadBalancerType, String customerWeightMaps, int defaultWeight) {
        if ("RandomLoadBalancer".equals(loadBalancerType)) {
            return new RandomLoadBalancer();
        } else if ("RoundRobinLoadBalancer".equals(loadBalancerType)) {
            return new RoundRobinLoadBalancer();
        } else if ("WeightedRoundRobinBalancer".equals(loadBalancerType)) {
            Map<String, Integer> weights = new HashMap(16);
            String[] weightMaps = customerWeightMaps.split(";");
            Arrays.asList(weightMaps).forEach((weightMap) -> {
                Integer var10000 = (Integer) weights.put("redis://" + weightMap.split(",")[0], Integer.parseInt(weightMap.split(",")[1]));
            });
            return new WeightedRoundRobinBalancer(weights, defaultWeight);
        } else {
            return null;
        }
    }

    private ReadMode getReadMode(String readModeType) {
        if ("SLAVE".equals(readModeType)) {
            return ReadMode.SLAVE;
        } else if ("MASTER".equals(readModeType)) {
            return ReadMode.MASTER;
        } else {
            return "MASTER_SLAVE".equals(readModeType) ? ReadMode.MASTER_SLAVE : null;
        }
    }

    private SubscriptionMode getSubscriptionMode(String subscriptionModeType) {
        if ("SLAVE".equals(subscriptionModeType)) {
            return SubscriptionMode.SLAVE;
        } else {
            return "MASTER".equals(subscriptionModeType) ? SubscriptionMode.MASTER : null;
        }
    }

    private <T extends BaseMasterSlaveServersConfig> void setLockSslConfigAndClientName(T lockAutoConfig) throws URISyntaxException, MalformedURLException {
        if (this.lockConfig.isSslEnableEndpointIdentification()) {
            lockAutoConfig.setClientName(this.lockConfig.getClientName());
            lockAutoConfig.setSslEnableEndpointIdentification(this.lockConfig.isSslEnableEndpointIdentification());
            if (this.lockConfig.getSslKeystore() != null) {
                lockAutoConfig.setSslKeystore(new URL(this.lockConfig.getSslKeystore()));
            }

            if (this.lockConfig.getSslKeystorePassword() != null) {
                lockAutoConfig.setSslKeystorePassword(this.lockConfig.getSslKeystorePassword());
            }

            if (this.lockConfig.getSslTruststore() != null) {
                lockAutoConfig.setSslTruststore(new URL(this.lockConfig.getSslTruststore()));
            }

            if (this.lockConfig.getSslTruststorePassword() != null) {
                lockAutoConfig.setSslTruststorePassword(this.lockConfig.getSslTruststorePassword());
            }

            lockAutoConfig.setSslProvider("JDK".equalsIgnoreCase(this.lockConfig.getSslProvider()) ? SslProvider.JDK : SslProvider.OPENSSL);
        }

    }
}
