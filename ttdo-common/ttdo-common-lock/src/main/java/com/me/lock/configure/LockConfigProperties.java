package com.me.lock.configure;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(
        prefix = "common.lock"
)
public class LockConfigProperties {

    public static final String PREFIX = "common.lock";

    private String pattern;

    // 单节点模式
    private LockConfigProperties.SingleConfig singleServer;
    // 集群模式
    private LockConfigProperties.ClusterConfig clusterServer;
    // 云托管模式
    private LockConfigProperties.ReplicatedConfig replicatedServer;
    // 哨兵模式
    private LockConfigProperties.SentinelConfig sentinelServer;
    // 主从模式
    private LockConfigProperties.MasterSlaveConfig masterSlaveServer;


    private String clientName;

    private boolean sslEnableEndpointIdentification;

    private String sslProvider;

    private String sslTruststore;

    private String sslTruststorePassword;

    private String sslKeystore;

    private String sslKeystorePassword;

    private int threads;

    private int nettyThreads;

    private long lockWatchdogTimeout;

    private boolean keepPubSubOrder;

    private boolean useScriptCache;

    public LockConfigProperties() {
//        this.pattern = ServerPattern.SINGLE.getPattern();
        this.clientName = "Lock";
        this.sslEnableEndpointIdentification = true;
        this.sslProvider = "JDK";
        this.threads = 0;
        this.nettyThreads = 0;
        this.lockWatchdogTimeout = 30000L;
        this.keepPubSubOrder = true;
        this.useScriptCache = false;
    }

    public String getClientName() {
        return this.clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public boolean isSslEnableEndpointIdentification() {
        return this.sslEnableEndpointIdentification;
    }

    public void setSslEnableEndpointIdentification(boolean sslEnableEndpointIdentification) {
        this.sslEnableEndpointIdentification = sslEnableEndpointIdentification;
    }

    public String getSslProvider() {
        return this.sslProvider;
    }

    public void setSslProvider(String sslProvider) {
        this.sslProvider = sslProvider;
    }

    public String getSslTruststore() {
        return this.sslTruststore;
    }

    public void setSslTruststore(String sslTruststore) {
        this.sslTruststore = sslTruststore;
    }

    public String getSslTruststorePassword() {
        return this.sslTruststorePassword;
    }

    public void setSslTruststorePassword(String sslTruststorePassword) {
        this.sslTruststorePassword = sslTruststorePassword;
    }

    public String getSslKeystore() {
        return this.sslKeystore;
    }

    public void setSslKeystore(String sslKeystore) {
        this.sslKeystore = sslKeystore;
    }

    public String getSslKeystorePassword() {
        return this.sslKeystorePassword;
    }

    public void setSslKeystorePassword(String sslKeystorePassword) {
        this.sslKeystorePassword = sslKeystorePassword;
    }

    public String getPattern() {
        return this.pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public LockConfigProperties.SingleConfig getSingleServer() {
        return this.singleServer;
    }

    public void setSingleServer(LockConfigProperties.SingleConfig singleServer) {
        this.singleServer = singleServer;
    }

    public LockConfigProperties.ClusterConfig getClusterServer() {
        return this.clusterServer;
    }

    public void setClusterServer(LockConfigProperties.ClusterConfig clusterServer) {
        this.clusterServer = clusterServer;
    }

    public LockConfigProperties.ReplicatedConfig getReplicatedServer() {
        return this.replicatedServer;
    }

    public void setReplicatedServer(LockConfigProperties.ReplicatedConfig replicatedServer) {
        this.replicatedServer = replicatedServer;
    }

    public LockConfigProperties.SentinelConfig getSentinelServer() {
        return this.sentinelServer;
    }

    public void setSentinelServer(LockConfigProperties.SentinelConfig sentinelServer) {
        this.sentinelServer = sentinelServer;
    }

    public LockConfigProperties.MasterSlaveConfig getMasterSlaveServer() {
        return this.masterSlaveServer;
    }

    public void setMasterSlaveServer(LockConfigProperties.MasterSlaveConfig masterSlaveServer) {
        this.masterSlaveServer = masterSlaveServer;
    }

    public int getThreads() {
        return this.threads;
    }

    public void setThreads(int threads) {
        this.threads = threads;
    }

    public int getNettyThreads() {
        return this.nettyThreads;
    }

    public void setNettyThreads(int nettyThreads) {
        this.nettyThreads = nettyThreads;
    }

    public long getLockWatchdogTimeout() {
        return this.lockWatchdogTimeout;
    }

    public void setLockWatchdogTimeout(long lockWatchdogTimeout) {
        this.lockWatchdogTimeout = lockWatchdogTimeout;
    }

    public boolean getKeepPubSubOrder() {
        return this.keepPubSubOrder;
    }

    public void setKeepPubSubOrder(boolean keepPubSubOrder) {
        this.keepPubSubOrder = keepPubSubOrder;
    }

    public boolean getUseScriptCache() {
        return this.useScriptCache;
    }

    public void setUseScriptCache(boolean useScriptCache) {
        this.useScriptCache = useScriptCache;
    }

    public static class SingleConfig {
        private String address;
        private int port = 6379;
        private int subConnMinIdleSize = 1;
        private int subConnPoolSize = 50;
        private int connMinIdleSize = 32;
        private int connPoolSize = 64;
        private boolean dnsMonitoring = false;
        private int dnsMonitoringInterval = 5000;
        private int idleConnTimeout = 10000;
        private boolean keepAlive = false;
        private int connTimeout = 10000;
        private int timeout = 3000;
        private int retryAttempts = 3;
        private int retryInterval = 1500;
        private int database = 0;
        private String password;
        private int subPerConn = 5;

        public SingleConfig() {
        }

        public String getAddress() {
            return this.address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getPort() {
            return this.port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public int getSubConnMinIdleSize() {
            return this.subConnMinIdleSize;
        }

        public void setSubConnMinIdleSize(int subConnMinIdleSize) {
            this.subConnMinIdleSize = subConnMinIdleSize;
        }

        public int getSubConnPoolSize() {
            return this.subConnPoolSize;
        }

        public void setSubConnPoolSize(int subConnPoolSize) {
            this.subConnPoolSize = subConnPoolSize;
        }

        public int getConnMinIdleSize() {
            return this.connMinIdleSize;
        }

        public void setConnMinIdleSize(int connMinIdleSize) {
            this.connMinIdleSize = connMinIdleSize;
        }

        public int getConnPoolSize() {
            return this.connPoolSize;
        }

        public void setConnPoolSize(int connPoolSize) {
            this.connPoolSize = connPoolSize;
        }

        public boolean isDnsMonitoring() {
            return this.dnsMonitoring;
        }

        public void setDnsMonitoring(boolean dnsMonitoring) {
            this.dnsMonitoring = dnsMonitoring;
        }

        public int getDnsMonitoringInterval() {
            return this.dnsMonitoringInterval;
        }

        public void setDnsMonitoringInterval(int dnsMonitoringInterval) {
            this.dnsMonitoringInterval = dnsMonitoringInterval;
        }

        public int getIdleConnTimeout() {
            return this.idleConnTimeout;
        }

        public void setIdleConnTimeout(int idleConnTimeout) {
            this.idleConnTimeout = idleConnTimeout;
        }

        public int getConnTimeout() {
            return this.connTimeout;
        }

        public void setConnTimeout(int connTimeout) {
            this.connTimeout = connTimeout;
        }

        public int getTimeout() {
            return this.timeout;
        }

        public void setTimeout(int timeout) {
            this.timeout = timeout;
        }

        public int getRetryAttempts() {
            return this.retryAttempts;
        }

        public void setRetryAttempts(int retryAttempts) {
            this.retryAttempts = retryAttempts;
        }

        public int getRetryInterval() {
            return this.retryInterval;
        }

        public void setRetryInterval(int retryInterval) {
            this.retryInterval = retryInterval;
        }

        public int getDatabase() {
            return this.database;
        }

        public void setDatabase(int database) {
            this.database = database;
        }

        public String getPassword() {
            return this.password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public int getSubPerConn() {
            return this.subPerConn;
        }

        public void setSubPerConn(int subPerConn) {
            this.subPerConn = subPerConn;
        }

        public boolean isKeepAlive() {
            return this.keepAlive;
        }

        public void setKeepAlive(boolean keepAlive) {
            this.keepAlive = keepAlive;
        }
    }

    public static class ClusterConfig {
        private String nodeAddresses;
        private int scanInterval = 1000;
        private String readMode = "SLAVE";
        private String subMode = "SLAVE";
        private String loadBalancer = "RoundRobinLoadBalancer";
        private int defaultWeight = 0;
        private String weightMaps;
        private int subConnMinIdleSize = 1;
        private int subConnPoolSize = 50;
        private int slaveConnMinIdleSize = 32;
        private int slaveConnPoolSize = 64;
        private int masterConnMinIdleSize = 32;
        private int masterConnPoolSize = 64;
        private int idleConnTimeout = 10000;
        private int connTimeout = 10000;
        private int timeout = 3000;
        private int retryAttempts = 3;
        private int retryInterval = 1500;
        private String password;
        private int subPerConn = 5;

        public ClusterConfig() {
        }

        public String getNodeAddresses() {
            return this.nodeAddresses;
        }

        public void setNodeAddresses(String nodeAddresses) {
            this.nodeAddresses = nodeAddresses;
        }

        public int getScanInterval() {
            return this.scanInterval;
        }

        public void setScanInterval(int scanInterval) {
            this.scanInterval = scanInterval;
        }

        public String getReadMode() {
            return this.readMode;
        }

        public void setReadMode(String readMode) {
            this.readMode = readMode;
        }

        public String getSubMode() {
            return this.subMode;
        }

        public void setSubMode(String subMode) {
            this.subMode = subMode;
        }

        public String getLoadBalancer() {
            return this.loadBalancer;
        }

        public void setLoadBalancer(String loadBalancer) {
            this.loadBalancer = loadBalancer;
        }

        public int getSubConnMinIdleSize() {
            return this.subConnMinIdleSize;
        }

        public void setSubConnMinIdleSize(int subConnMinIdleSize) {
            this.subConnMinIdleSize = subConnMinIdleSize;
        }

        public int getSubConnPoolSize() {
            return this.subConnPoolSize;
        }

        public void setSubConnPoolSize(int subConnPoolSize) {
            this.subConnPoolSize = subConnPoolSize;
        }

        public int getSlaveConnMinIdleSize() {
            return this.slaveConnMinIdleSize;
        }

        public void setSlaveConnMinIdleSize(int slaveConnMinIdleSize) {
            this.slaveConnMinIdleSize = slaveConnMinIdleSize;
        }

        public int getSlaveConnPoolSize() {
            return this.slaveConnPoolSize;
        }

        public void setSlaveConnPoolSize(int slaveConnPoolSize) {
            this.slaveConnPoolSize = slaveConnPoolSize;
        }

        public int getMasterConnMinIdleSize() {
            return this.masterConnMinIdleSize;
        }

        public void setMasterConnMinIdleSize(int masterConnMinIdleSize) {
            this.masterConnMinIdleSize = masterConnMinIdleSize;
        }

        public int getMasterConnPoolSize() {
            return this.masterConnPoolSize;
        }

        public void setMasterConnPoolSize(int masterConnPoolSize) {
            this.masterConnPoolSize = masterConnPoolSize;
        }

        public int getIdleConnTimeout() {
            return this.idleConnTimeout;
        }

        public void setIdleConnTimeout(int idleConnTimeout) {
            this.idleConnTimeout = idleConnTimeout;
        }

        public int getConnTimeout() {
            return this.connTimeout;
        }

        public void setConnTimeout(int connTimeout) {
            this.connTimeout = connTimeout;
        }

        public int getTimeout() {
            return this.timeout;
        }

        public void setTimeout(int timeout) {
            this.timeout = timeout;
        }

        public int getRetryAttempts() {
            return this.retryAttempts;
        }

        public void setRetryAttempts(int retryAttempts) {
            this.retryAttempts = retryAttempts;
        }

        public int getRetryInterval() {
            return this.retryInterval;
        }

        public void setRetryInterval(int retryInterval) {
            this.retryInterval = retryInterval;
        }

        public String getPassword() {
            return this.password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public int getSubPerConn() {
            return this.subPerConn;
        }

        public void setSubPerConn(int subPerConn) {
            this.subPerConn = subPerConn;
        }

        public int getDefaultWeight() {
            return this.defaultWeight;
        }

        public void setDefaultWeight(int defaultWeight) {
            this.defaultWeight = defaultWeight;
        }

        public String getWeightMaps() {
            return this.weightMaps;
        }

        public void setWeightMaps(String weightMaps) {
            this.weightMaps = weightMaps;
        }
    }

    public static class ReplicatedConfig {
        private String nodeAddresses;
        private int scanInterval = 1000;
        private long dnsMonitoringInterval = 5000L;
        private String readMode = "SLAVE";
        private String subscriptionMode = "SLAVE";
        private String loadBalancer = "RoundRobinLoadBalancer";
        private int defaultWeight = 0;
        private String weightMaps;
        private int subscriptionConnectionMinimumIdleSize = 1;
        private int subscriptionConnectionPoolSize = 50;
        private int slaveConnectionMinimumIdleSize = 32;
        private int slaveConnectionPoolSize = 64;
        private int masterConnectionMinimumIdleSize = 32;
        private int masterConnectionPoolSize = 64;
        private int idleConnectionTimeout = 10000;
        private int connectTimeout = 10000;
        private int timeout = 3000;
        private int retryAttempts = 3;
        private int retryInterval = 1500;
        private int database = 0;
        private String password;
        private int subscriptionsPerConnection = 5;

        public ReplicatedConfig() {
        }

        public String getNodeAddresses() {
            return this.nodeAddresses;
        }

        public void setNodeAddresses(String nodeAddresses) {
            this.nodeAddresses = nodeAddresses;
        }

        public int getScanInterval() {
            return this.scanInterval;
        }

        public void setScanInterval(int scanInterval) {
            this.scanInterval = scanInterval;
        }

        public long getDnsMonitoringInterval() {
            return this.dnsMonitoringInterval;
        }

        public void setDnsMonitoringInterval(long dnsMonitoringInterval) {
            this.dnsMonitoringInterval = dnsMonitoringInterval;
        }

        public String getReadMode() {
            return this.readMode;
        }

        public void setReadMode(String readMode) {
            this.readMode = readMode;
        }

        public String getSubscriptionMode() {
            return this.subscriptionMode;
        }

        public void setSubscriptionMode(String subscriptionMode) {
            this.subscriptionMode = subscriptionMode;
        }

        public String getLoadBalancer() {
            return this.loadBalancer;
        }

        public void setLoadBalancer(String loadBalancer) {
            this.loadBalancer = loadBalancer;
        }

        public int getDefaultWeight() {
            return this.defaultWeight;
        }

        public void setDefaultWeight(int defaultWeight) {
            this.defaultWeight = defaultWeight;
        }

        public String getWeightMaps() {
            return this.weightMaps;
        }

        public void setWeightMaps(String weightMaps) {
            this.weightMaps = weightMaps;
        }

        public int getSubscriptionConnectionMinimumIdleSize() {
            return this.subscriptionConnectionMinimumIdleSize;
        }

        public void setSubscriptionConnectionMinimumIdleSize(int subscriptionConnectionMinimumIdleSize) {
            this.subscriptionConnectionMinimumIdleSize = subscriptionConnectionMinimumIdleSize;
        }

        public int getSubscriptionConnectionPoolSize() {
            return this.subscriptionConnectionPoolSize;
        }

        public void setSubscriptionConnectionPoolSize(int subscriptionConnectionPoolSize) {
            this.subscriptionConnectionPoolSize = subscriptionConnectionPoolSize;
        }

        public int getSlaveConnectionMinimumIdleSize() {
            return this.slaveConnectionMinimumIdleSize;
        }

        public void setSlaveConnectionMinimumIdleSize(int slaveConnectionMinimumIdleSize) {
            this.slaveConnectionMinimumIdleSize = slaveConnectionMinimumIdleSize;
        }

        public int getSlaveConnectionPoolSize() {
            return this.slaveConnectionPoolSize;
        }

        public void setSlaveConnectionPoolSize(int slaveConnectionPoolSize) {
            this.slaveConnectionPoolSize = slaveConnectionPoolSize;
        }

        public int getMasterConnectionMinimumIdleSize() {
            return this.masterConnectionMinimumIdleSize;
        }

        public void setMasterConnectionMinimumIdleSize(int masterConnectionMinimumIdleSize) {
            this.masterConnectionMinimumIdleSize = masterConnectionMinimumIdleSize;
        }

        public int getMasterConnectionPoolSize() {
            return this.masterConnectionPoolSize;
        }

        public void setMasterConnectionPoolSize(int masterConnectionPoolSize) {
            this.masterConnectionPoolSize = masterConnectionPoolSize;
        }

        public int getIdleConnectionTimeout() {
            return this.idleConnectionTimeout;
        }

        public void setIdleConnectionTimeout(int idleConnectionTimeout) {
            this.idleConnectionTimeout = idleConnectionTimeout;
        }

        public int getConnectTimeout() {
            return this.connectTimeout;
        }

        public void setConnectTimeout(int connectTimeout) {
            this.connectTimeout = connectTimeout;
        }

        public int getTimeout() {
            return this.timeout;
        }

        public void setTimeout(int timeout) {
            this.timeout = timeout;
        }

        public int getRetryAttempts() {
            return this.retryAttempts;
        }

        public void setRetryAttempts(int retryAttempts) {
            this.retryAttempts = retryAttempts;
        }

        public int getRetryInterval() {
            return this.retryInterval;
        }

        public void setRetryInterval(int retryInterval) {
            this.retryInterval = retryInterval;
        }

        public int getDatabase() {
            return this.database;
        }

        public void setDatabase(int database) {
            this.database = database;
        }

        public String getPassword() {
            return this.password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public int getSubscriptionsPerConnection() {
            return this.subscriptionsPerConnection;
        }

        public void setSubscriptionsPerConnection(int subscriptionsPerConnection) {
            this.subscriptionsPerConnection = subscriptionsPerConnection;
        }
    }

    public static class SentinelConfig {
        private long dnsMonitoringInterval = 5000L;
        private String masterName;
        private String sentinelAddresses;
        private String readMode = "SLAVE";
        private String subMode = "SLAVE";
        private String loadBalancer = "RoundRobinLoadBalancer";
        private int defaultWeight = 0;
        private String weightMaps;
        private int subscriptionConnectionMinimumIdleSize = 1;
        private int subscriptionConnectionPoolSize = 50;
        private int slaveConnectionMinimumIdleSize = 32;
        private int slaveConnectionPoolSize = 64;
        private int masterConnectionMinimumIdleSize = 32;
        private int masterConnectionPoolSize = 64;
        private int idleConnectionTimeout = 10000;
        private int connectTimeout = 10000;
        private int timeout = 3000;
        private int retryAttempts = 3;
        private int retryInterval = 1500;
        private int database = 0;
        private String password;
        private int subscriptionsPerConnection = 5;

        public SentinelConfig() {
        }

        public long getDnsMonitoringInterval() {
            return this.dnsMonitoringInterval;
        }

        public void setDnsMonitoringInterval(long dnsMonitoringInterval) {
            this.dnsMonitoringInterval = dnsMonitoringInterval;
        }

        public String getMasterName() {
            return this.masterName;
        }

        public void setMasterName(String masterName) {
            this.masterName = masterName;
        }

        public String getSentinelAddresses() {
            return this.sentinelAddresses;
        }

        public void setSentinelAddresses(String sentinelAddresses) {
            this.sentinelAddresses = sentinelAddresses;
        }

        public String getReadMode() {
            return this.readMode;
        }

        public void setReadMode(String readMode) {
            this.readMode = readMode;
        }

        public String getSubMode() {
            return this.subMode;
        }

        public void setSubMode(String subMode) {
            this.subMode = subMode;
        }

        public String getLoadBalancer() {
            return this.loadBalancer;
        }

        public void setLoadBalancer(String loadBalancer) {
            this.loadBalancer = loadBalancer;
        }

        public int getDefaultWeight() {
            return this.defaultWeight;
        }

        public void setDefaultWeight(int defaultWeight) {
            this.defaultWeight = defaultWeight;
        }

        public String getWeightMaps() {
            return this.weightMaps;
        }

        public void setWeightMaps(String weightMaps) {
            this.weightMaps = weightMaps;
        }

        public int getSubscriptionConnectionMinimumIdleSize() {
            return this.subscriptionConnectionMinimumIdleSize;
        }

        public void setSubscriptionConnectionMinimumIdleSize(int subscriptionConnectionMinimumIdleSize) {
            this.subscriptionConnectionMinimumIdleSize = subscriptionConnectionMinimumIdleSize;
        }

        public int getSubscriptionConnectionPoolSize() {
            return this.subscriptionConnectionPoolSize;
        }

        public void setSubscriptionConnectionPoolSize(int subscriptionConnectionPoolSize) {
            this.subscriptionConnectionPoolSize = subscriptionConnectionPoolSize;
        }

        public int getSlaveConnectionMinimumIdleSize() {
            return this.slaveConnectionMinimumIdleSize;
        }

        public void setSlaveConnectionMinimumIdleSize(int slaveConnectionMinimumIdleSize) {
            this.slaveConnectionMinimumIdleSize = slaveConnectionMinimumIdleSize;
        }

        public int getSlaveConnectionPoolSize() {
            return this.slaveConnectionPoolSize;
        }

        public void setSlaveConnectionPoolSize(int slaveConnectionPoolSize) {
            this.slaveConnectionPoolSize = slaveConnectionPoolSize;
        }

        public int getMasterConnectionMinimumIdleSize() {
            return this.masterConnectionMinimumIdleSize;
        }

        public void setMasterConnectionMinimumIdleSize(int masterConnectionMinimumIdleSize) {
            this.masterConnectionMinimumIdleSize = masterConnectionMinimumIdleSize;
        }

        public int getMasterConnectionPoolSize() {
            return this.masterConnectionPoolSize;
        }

        public void setMasterConnectionPoolSize(int masterConnectionPoolSize) {
            this.masterConnectionPoolSize = masterConnectionPoolSize;
        }

        public int getIdleConnectionTimeout() {
            return this.idleConnectionTimeout;
        }

        public void setIdleConnectionTimeout(int idleConnectionTimeout) {
            this.idleConnectionTimeout = idleConnectionTimeout;
        }

        public int getConnectTimeout() {
            return this.connectTimeout;
        }

        public void setConnectTimeout(int connectTimeout) {
            this.connectTimeout = connectTimeout;
        }

        public int getTimeout() {
            return this.timeout;
        }

        public void setTimeout(int timeout) {
            this.timeout = timeout;
        }

        public int getRetryAttempts() {
            return this.retryAttempts;
        }

        public void setRetryAttempts(int retryAttempts) {
            this.retryAttempts = retryAttempts;
        }

        public int getRetryInterval() {
            return this.retryInterval;
        }

        public void setRetryInterval(int retryInterval) {
            this.retryInterval = retryInterval;
        }

        public int getDatabase() {
            return this.database;
        }

        public void setDatabase(int database) {
            this.database = database;
        }

        public String getPassword() {
            return this.password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public int getSubscriptionsPerConnection() {
            return this.subscriptionsPerConnection;
        }

        public void setSubscriptionsPerConnection(int subscriptionsPerConnection) {
            this.subscriptionsPerConnection = subscriptionsPerConnection;
        }
    }

    public static class MasterSlaveConfig {
        private long dnsMonitoringInterval = 5000L;
        private String masterAddress;
        private String slaveAddresses;
        private String readMode = "SLAVE";
        private String subMode = "SLAVE";
        private String loadBalancer = "RoundRobinLoadBalancer";
        private int defaultWeight = 0;
        private String weightMaps;
        private int subscriptionConnectionMinimumIdleSize = 1;
        private int subscriptionConnectionPoolSize = 50;
        private int slaveConnectionMinimumIdleSize = 32;
        private int slaveConnectionPoolSize = 64;
        private int masterConnectionMinimumIdleSize = 32;
        private int masterConnectionPoolSize = 64;
        private int idleConnectionTimeout = 10000;
        private int connectTimeout = 10000;
        private int timeout = 3000;
        private int retryAttempts = 3;
        private int retryInterval = 1500;
        private int reconnectionTimeout = 3000;
        private int failedAttempts = 3;
        private int database = 0;
        private String password;
        private int subscriptionsPerConnection = 5;

        public MasterSlaveConfig() {
        }

        public long getDnsMonitoringInterval() {
            return this.dnsMonitoringInterval;
        }

        public void setDnsMonitoringInterval(long dnsMonitoringInterval) {
            this.dnsMonitoringInterval = dnsMonitoringInterval;
        }

        public String getMasterAddress() {
            return this.masterAddress;
        }

        public void setMasterAddress(String masterAddress) {
            this.masterAddress = masterAddress;
        }

        public String getSlaveAddresses() {
            return this.slaveAddresses;
        }

        public void setSlaveAddresses(String slaveAddresses) {
            this.slaveAddresses = slaveAddresses;
        }

        public String getReadMode() {
            return this.readMode;
        }

        public void setReadMode(String readMode) {
            this.readMode = readMode;
        }

        public String getSubMode() {
            return this.subMode;
        }

        public void setSubMode(String subMode) {
            this.subMode = subMode;
        }

        public String getLoadBalancer() {
            return this.loadBalancer;
        }

        public void setLoadBalancer(String loadBalancer) {
            this.loadBalancer = loadBalancer;
        }

        public int getDefaultWeight() {
            return this.defaultWeight;
        }

        public void setDefaultWeight(int defaultWeight) {
            this.defaultWeight = defaultWeight;
        }

        public String getWeightMaps() {
            return this.weightMaps;
        }

        public void setWeightMaps(String weightMaps) {
            this.weightMaps = weightMaps;
        }

        public int getSubscriptionConnectionMinimumIdleSize() {
            return this.subscriptionConnectionMinimumIdleSize;
        }

        public void setSubscriptionConnectionMinimumIdleSize(int subscriptionConnectionMinimumIdleSize) {
            this.subscriptionConnectionMinimumIdleSize = subscriptionConnectionMinimumIdleSize;
        }

        public int getSubscriptionConnectionPoolSize() {
            return this.subscriptionConnectionPoolSize;
        }

        public void setSubscriptionConnectionPoolSize(int subscriptionConnectionPoolSize) {
            this.subscriptionConnectionPoolSize = subscriptionConnectionPoolSize;
        }

        public int getSlaveConnectionMinimumIdleSize() {
            return this.slaveConnectionMinimumIdleSize;
        }

        public void setSlaveConnectionMinimumIdleSize(int slaveConnectionMinimumIdleSize) {
            this.slaveConnectionMinimumIdleSize = slaveConnectionMinimumIdleSize;
        }

        public int getSlaveConnectionPoolSize() {
            return this.slaveConnectionPoolSize;
        }

        public void setSlaveConnectionPoolSize(int slaveConnectionPoolSize) {
            this.slaveConnectionPoolSize = slaveConnectionPoolSize;
        }

        public int getMasterConnectionMinimumIdleSize() {
            return this.masterConnectionMinimumIdleSize;
        }

        public void setMasterConnectionMinimumIdleSize(int masterConnectionMinimumIdleSize) {
            this.masterConnectionMinimumIdleSize = masterConnectionMinimumIdleSize;
        }

        public int getMasterConnectionPoolSize() {
            return this.masterConnectionPoolSize;
        }

        public void setMasterConnectionPoolSize(int masterConnectionPoolSize) {
            this.masterConnectionPoolSize = masterConnectionPoolSize;
        }

        public int getIdleConnectionTimeout() {
            return this.idleConnectionTimeout;
        }

        public void setIdleConnectionTimeout(int idleConnectionTimeout) {
            this.idleConnectionTimeout = idleConnectionTimeout;
        }

        public int getConnectTimeout() {
            return this.connectTimeout;
        }

        public void setConnectTimeout(int connectTimeout) {
            this.connectTimeout = connectTimeout;
        }

        public int getTimeout() {
            return this.timeout;
        }

        public void setTimeout(int timeout) {
            this.timeout = timeout;
        }

        public int getRetryAttempts() {
            return this.retryAttempts;
        }

        public void setRetryAttempts(int retryAttempts) {
            this.retryAttempts = retryAttempts;
        }

        public int getRetryInterval() {
            return this.retryInterval;
        }

        public void setRetryInterval(int retryInterval) {
            this.retryInterval = retryInterval;
        }

        public int getReconnectionTimeout() {
            return this.reconnectionTimeout;
        }

        public void setReconnectionTimeout(int reconnectionTimeout) {
            this.reconnectionTimeout = reconnectionTimeout;
        }

        public int getFailedAttempts() {
            return this.failedAttempts;
        }

        public void setFailedAttempts(int failedAttempts) {
            this.failedAttempts = failedAttempts;
        }

        public int getDatabase() {
            return this.database;
        }

        public void setDatabase(int database) {
            this.database = database;
        }

        public String getPassword() {
            return this.password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public int getSubscriptionsPerConnection() {
            return this.subscriptionsPerConnection;
        }

        public void setSubscriptionsPerConnection(int subscriptionsPerConnection) {
            this.subscriptionsPerConnection = subscriptionsPerConnection;
        }
    }
}
