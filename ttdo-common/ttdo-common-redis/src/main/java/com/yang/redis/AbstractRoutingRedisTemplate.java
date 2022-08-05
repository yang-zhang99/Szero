package com.yang.redis;


import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.query.SortQuery;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.core.script.ScriptExecutor;
import org.springframework.data.redis.core.types.RedisClientInfo;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.io.Closeable;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 抽象类 动态Redis库
 *
 * @param <K>
 * @param <V>
 */
public abstract class AbstractRoutingRedisTemplate<K, V> extends RedisTemplate<K, V> implements InitializingBean {
    private Map<Object, RedisTemplate<K, V>> redisTemplates;
    private RedisTemplate<K, V> defaultRedisTemplate;

    public <T> T execute(RedisCallback<T> action) {
        return this.determineTargetRedisTemplate().execute(action);
    }

    public <T> T execute(RedisCallback<T> action, boolean exposeConnection) {
        return this.determineTargetRedisTemplate().execute(action, exposeConnection);
    }

    public <T> T execute(RedisCallback<T> action, boolean exposeConnection, boolean pipeline) {
        return this.determineTargetRedisTemplate().execute(action, exposeConnection, pipeline);
    }

    public <T> T execute(SessionCallback<T> session) {
        return this.determineTargetRedisTemplate().execute(session);
    }

    public List<Object> executePipelined(SessionCallback<?> session) {
        return this.determineTargetRedisTemplate().executePipelined(session);
    }

    public List<Object> executePipelined(SessionCallback<?> session, RedisSerializer<?> resultSerializer) {
        return this.determineTargetRedisTemplate().executePipelined(session, resultSerializer);
    }

    public List<Object> executePipelined(RedisCallback<?> action) {
        return this.determineTargetRedisTemplate().executePipelined(action);
    }

    public List<Object> executePipelined(RedisCallback<?> action, RedisSerializer<?> resultSerializer) {
        return this.determineTargetRedisTemplate().executePipelined(action, resultSerializer);
    }

    public <T> T execute(RedisScript<T> script, List<K> keys, Object... args) {
        return this.determineTargetRedisTemplate().execute(script, keys, args);
    }

    public <T> T execute(RedisScript<T> script, RedisSerializer<?> argsSerializer, RedisSerializer<T> resultSerializer, List<K> keys, Object... args) {
        return this.determineTargetRedisTemplate().execute(script, argsSerializer, resultSerializer, keys, args);
    }

    public <T extends Closeable> T executeWithStickyConnection(RedisCallback<T> callback) {
        return this.determineTargetRedisTemplate().executeWithStickyConnection(callback);
    }

    public boolean isExposeConnection() {
        return this.determineTargetRedisTemplate().isExposeConnection();
    }

    public void setExposeConnection(boolean exposeConnection) {
        this.determineTargetRedisTemplate().setExposeConnection(exposeConnection);
    }

    public boolean isEnableDefaultSerializer() {
        return this.determineTargetRedisTemplate().isEnableDefaultSerializer();
    }

    public void setEnableDefaultSerializer(boolean enableDefaultSerializer) {
        this.determineTargetRedisTemplate().setEnableDefaultSerializer(enableDefaultSerializer);
    }

    public RedisSerializer<?> getDefaultSerializer() {
        return this.determineTargetRedisTemplate().getDefaultSerializer();
    }

    public void setDefaultSerializer(RedisSerializer<?> serializer) {
        this.determineTargetRedisTemplate().setDefaultSerializer(serializer);
    }

    public void setKeySerializer(RedisSerializer<?> serializer) {
        this.determineTargetRedisTemplate().setKeySerializer(serializer);
    }

    public RedisSerializer<?> getKeySerializer() {
        return this.determineTargetRedisTemplate().getKeySerializer();
    }

    public void setValueSerializer(RedisSerializer<?> serializer) {
        this.determineTargetRedisTemplate().setValueSerializer(serializer);
    }

    public RedisSerializer<?> getValueSerializer() {
        return this.determineTargetRedisTemplate().getValueSerializer();
    }

    public RedisSerializer<?> getHashKeySerializer() {
        return this.determineTargetRedisTemplate().getHashKeySerializer();
    }

    public void setHashKeySerializer(RedisSerializer<?> hashKeySerializer) {
        this.determineTargetRedisTemplate().setHashKeySerializer(hashKeySerializer);
    }

    public RedisSerializer<?> getHashValueSerializer() {
        return this.determineTargetRedisTemplate().getHashValueSerializer();
    }

    public void setHashValueSerializer(RedisSerializer<?> hashValueSerializer) {
        this.determineTargetRedisTemplate().setHashValueSerializer(hashValueSerializer);
    }

    public RedisSerializer<String> getStringSerializer() {
        return this.determineTargetRedisTemplate().getStringSerializer();
    }

    public void setStringSerializer(RedisSerializer<String> stringSerializer) {
        this.determineTargetRedisTemplate().setStringSerializer(stringSerializer);
    }

    public void setScriptExecutor(ScriptExecutor<K> scriptExecutor) {
        this.determineTargetRedisTemplate().setScriptExecutor(scriptExecutor);
    }

    public List<Object> exec() {
        return this.determineTargetRedisTemplate().exec();
    }

    public List<Object> exec(RedisSerializer<?> valueSerializer) {
        return this.determineTargetRedisTemplate().exec(valueSerializer);
    }

    public Boolean delete(K key) {
        return this.determineTargetRedisTemplate().delete(key);
    }

    public Long delete(Collection<K> keys) {
        return this.determineTargetRedisTemplate().delete(keys);
    }

    public Boolean hasKey(K key) {
        return this.determineTargetRedisTemplate().hasKey(key);
    }

    public Boolean expire(K key, long timeout, TimeUnit unit) {
        return this.determineTargetRedisTemplate().expire(key, timeout, unit);
    }

    public Boolean expireAt(K key, Date date) {
        return this.determineTargetRedisTemplate().expireAt(key, date);
    }

    public void convertAndSend(String channel, Object message) {
        this.determineTargetRedisTemplate().convertAndSend(channel, message);
    }

    public Long getExpire(K key) {
        return this.determineTargetRedisTemplate().getExpire(key);
    }

    public Long getExpire(K key, TimeUnit timeUnit) {
        return this.determineTargetRedisTemplate().getExpire(key, timeUnit);
    }

    public Set<K> keys(K pattern) {
        return this.determineTargetRedisTemplate().keys(pattern);
    }

    public Boolean persist(K key) {
        return this.determineTargetRedisTemplate().persist(key);
    }

    public Boolean move(K key, int dbIndex) {
        return this.determineTargetRedisTemplate().move(key, dbIndex);
    }

    public K randomKey() {
        return this.determineTargetRedisTemplate().randomKey();
    }

    public void rename(K oldKey, K newKey) {
        this.determineTargetRedisTemplate().rename(oldKey, newKey);
    }

    public Boolean renameIfAbsent(K oldKey, K newKey) {
        return this.determineTargetRedisTemplate().renameIfAbsent(oldKey, newKey);
    }

    public DataType type(K key) {
        return this.determineTargetRedisTemplate().type(key);
    }

    public byte[] dump(K key) {
        return this.determineTargetRedisTemplate().dump(key);
    }

    public void restore(K key, byte[] value, long timeToLive, TimeUnit unit) {
        this.determineTargetRedisTemplate().restore(key, value, timeToLive, unit);
    }

    public void multi() {
        this.determineTargetRedisTemplate().multi();
    }

    public void discard() {
        this.determineTargetRedisTemplate().discard();
    }

    public void watch(K key) {
        this.determineTargetRedisTemplate().watch(key);
    }

    public void watch(Collection<K> keys) {
        this.determineTargetRedisTemplate().watch(keys);
    }

    public void unwatch() {
        this.determineTargetRedisTemplate().unwatch();
    }

    public List<V> sort(SortQuery<K> query) {
        return this.determineTargetRedisTemplate().sort(query);
    }

    public <T> List<T> sort(SortQuery<K> query, RedisSerializer<T> resultSerializer) {
        return this.determineTargetRedisTemplate().sort(query, resultSerializer);
    }

    public <T> List<T> sort(SortQuery<K> query, BulkMapper<T, V> bulkMapper) {
        return this.determineTargetRedisTemplate().sort(query, bulkMapper);
    }

    public <T, S> List<T> sort(SortQuery<K> query, BulkMapper<T, S> bulkMapper, RedisSerializer<S> resultSerializer) {
        return this.determineTargetRedisTemplate().sort(query, bulkMapper, resultSerializer);
    }

    public Long sort(SortQuery<K> query, K storeKey) {
        return this.determineTargetRedisTemplate().sort(query, storeKey);
    }

    public BoundValueOperations<K, V> boundValueOps(K key) {
        return this.determineTargetRedisTemplate().boundValueOps(key);
    }

    public ValueOperations<K, V> opsForValue() {
        return this.determineTargetRedisTemplate().opsForValue();
    }

    public ListOperations<K, V> opsForList() {
        return this.determineTargetRedisTemplate().opsForList();
    }

    public BoundListOperations<K, V> boundListOps(K key) {
        return this.determineTargetRedisTemplate().boundListOps(key);
    }

    public BoundSetOperations<K, V> boundSetOps(K key) {
        return this.determineTargetRedisTemplate().boundSetOps(key);
    }

    public SetOperations<K, V> opsForSet() {
        return this.determineTargetRedisTemplate().opsForSet();
    }

    public BoundZSetOperations<K, V> boundZSetOps(K key) {
        return this.determineTargetRedisTemplate().boundZSetOps(key);
    }

    public ZSetOperations<K, V> opsForZSet() {
        return this.determineTargetRedisTemplate().opsForZSet();
    }

    public GeoOperations<K, V> opsForGeo() {
        return this.determineTargetRedisTemplate().opsForGeo();
    }

    public BoundGeoOperations<K, V> boundGeoOps(K key) {
        return this.determineTargetRedisTemplate().boundGeoOps(key);
    }

    public HyperLogLogOperations<K, V> opsForHyperLogLog() {
        return this.determineTargetRedisTemplate().opsForHyperLogLog();
    }

    public <HK, HV> BoundHashOperations<K, HK, HV> boundHashOps(K key) {
        return this.determineTargetRedisTemplate().boundHashOps(key);
    }

    public <HK, HV> HashOperations<K, HK, HV> opsForHash() {
        return this.determineTargetRedisTemplate().opsForHash();
    }

    public ClusterOperations<K, V> opsForCluster() {
        return this.determineTargetRedisTemplate().opsForCluster();
    }

    public void killClient(String host, int port) {
        this.determineTargetRedisTemplate().killClient(host, port);
    }

    public List<RedisClientInfo> getClientList() {
        return this.determineTargetRedisTemplate().getClientList();
    }

    public void slaveOf(String host, int port) {
        this.determineTargetRedisTemplate().slaveOf(host, port);
    }

    public void slaveOfNoOne() {
        this.determineTargetRedisTemplate().slaveOfNoOne();
    }

    public void setEnableTransactionSupport(boolean enableTransactionSupport) {
        this.determineTargetRedisTemplate().setEnableTransactionSupport(enableTransactionSupport);
    }

    public void setBeanClassLoader(ClassLoader classLoader) {
        this.determineTargetRedisTemplate().setBeanClassLoader(classLoader);
    }

    public RedisConnectionFactory getConnectionFactory() {
        return this.determineTargetRedisTemplate().getConnectionFactory();
    }

    public void setConnectionFactory(RedisConnectionFactory connectionFactory) {
        this.determineTargetRedisTemplate().setConnectionFactory(connectionFactory);
    }

    public void setRedisTemplates(Map<Object, RedisTemplate<K, V>> redisTemplates) {
        this.redisTemplates = redisTemplates;
    }

    public void setDefaultRedisTemplate(RedisTemplate<K, V> defaultRedisTemplate) {
        this.defaultRedisTemplate = defaultRedisTemplate;
    }

    public void afterPropertiesSet() {
        if (this.redisTemplates == null) {
            throw new IllegalArgumentException("Property 'redisTemplates' is required");
        } else if (this.defaultRedisTemplate == null) {
            throw new IllegalArgumentException("Property 'defaultRedisTemplate' is required");
        }
    }

    protected RedisTemplate<K, V> determineTargetRedisTemplate() {
        Object lookupKey = this.determineCurrentLookupKey();
        if (lookupKey == null) {
            return this.defaultRedisTemplate;
        } else {
            RedisTemplate<K, V> redisTemplate = this.redisTemplates.get(lookupKey);
            if (redisTemplate == null) {
                redisTemplate = this.createRedisTemplateOnMissing(lookupKey);
                this.redisTemplates.put(lookupKey, redisTemplate);
            }
            return redisTemplate;
        }
    }

    protected abstract Object determineCurrentLookupKey();

    protected abstract RedisTemplate<K, V> createRedisTemplateOnMissing(Object lookupKey);
}
