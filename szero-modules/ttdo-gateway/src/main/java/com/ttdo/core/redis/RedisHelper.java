package com.ttdo.core.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ttdo.core.redis.convert.DateDeserializer;
import com.ttdo.core.redis.convert.DateSerializer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class RedisHelper implements InitializingBean {
    private final Logger logger = LoggerFactory.getLogger(RedisHelper.class);

    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    public static final long DEFAULT_EXPIRE = 86400L;
    public static final long NOT_EXPIRE = -1L;

    private final RedisTemplate<String, String> redisTemplate;
    private final ValueOperations<String, String> valueOpr;
    private final HashOperations<String, String, String> hashOpr;
    private final ListOperations<String, String> listOpr;
    private final SetOperations<String, String> setOpr;
    private final ZSetOperations<String, String> zSetOpr;

    @Autowired
    public RedisHelper(RedisTemplate<String, String> redisTemplate, ValueOperations<String, String> valueOpr, HashOperations<String, String, String> hashOpr, ListOperations<String, String> listOpr, SetOperations<String, String> setOpr, ZSetOperations<String, String> zSetOpr) {
        this.redisTemplate = redisTemplate;
        this.valueOpr = valueOpr;
        this.hashOpr = hashOpr;
        this.listOpr = listOpr;
        this.setOpr = setOpr;
        this.zSetOpr = zSetOpr;
    }

    static ObjectMapper objectMapper = new ObjectMapper();

    public void afterPropertiesSet() {
        Assert.notNull(this.redisTemplate, "redisTemplate must not be null.");
        Assert.notNull(this.valueOpr, "redisTemplate must not be null.");
        Assert.notNull(this.hashOpr, "redisTemplate must not be null.");
        Assert.notNull(this.listOpr, "redisTemplate must not be null.");
        Assert.notNull(this.setOpr, "redisTemplate must not be null.");
        Assert.notNull(this.zSetOpr, "redisTemplate must not be null.");
    }

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public RedisTemplate<String, String> getRedisTemplate() {
        return this.redisTemplate;
    }

    // 设置当前使用的 Redis
    public void setCurrentDatabase(int database) {
        this.logger.warn("Use default RedisHelper, you'd better use a DynamicRedisHelper instead.");
    }

    // 清除当前使用的 Redis
    public void clearCurrentDatabase() {
        this.logger.warn("Use default RedisHelper, you'd better use a DynamicRedisHelper instead.");
    }

    /**
     * 删除 key 的值
     *
     * @param key key
     */
    public void delKey(String key) {
        this.redisTemplate.delete(key);
    }

    /**
     * 是否存在 key 的值
     *
     * @param key key
     * @return 是否
     */
    public Boolean hasKey(String key) {
        return this.redisTemplate.hasKey(key);
    }

    /**
     * 剩余时间
     *
     * @param key key
     * @return Long Long
     */
    public Long getExpire(String key) {
        return this.redisTemplate.getExpire(key);
    }

    /**
     * 剩余时间
     *
     * @param key key
     * @return Long Long
     */
    public Long getExpire(String key, final TimeUnit timeUnit) {
        return this.redisTemplate.getExpire(key, timeUnit);
    }

    /**
     * 设置时间
     *
     * @param key key
     * @return Boolean Boolean
     */
    public Boolean setExpire(String key) {
        return this.setExpire(key, 86400L, TimeUnit.SECONDS);
    }

    /**
     * 设置时间
     *
     * @param key key
     * @return expire expire
     */
    public Boolean setExpire(String key, long expire) {
        return this.setExpire(key, expire, TimeUnit.SECONDS);
    }

    /**
     * String 设置值
     *
     * @param key      缓存key
     * @param expire   超时时间
     * @param timeUnit timeUnit
     */
    public Boolean setExpire(String key, long expire, TimeUnit timeUnit) {
        return this.redisTemplate.expire(key, expire, timeUnit == null ? TimeUnit.SECONDS : timeUnit);
    }

    /**
     * 删除 keys
     * @param keys keys
     */
    public void delKeys(Collection<String> keys) {
        Set<String> hs = new HashSet();
        Iterator var3 = keys.iterator();
        while (var3.hasNext()) {
            String key = (String) var3.next();
            hs.add(key);
        }
        this.redisTemplate.delete(hs);
    }

    private void deleteFullKey(String fullKey) {
        this.redisTemplate.delete(fullKey);
    }

    private void deleteFullKeys(Collection<String> fullKeys) {
        this.redisTemplate.delete(fullKeys);
    }

    /**
     * String 设置值
     *
     * @param key    缓存key
     * @param value  缓存value
     * @param expire 超时时间
     */
    public void strSet(String key, String value, long expire, TimeUnit timeUnit) {
        this.valueOpr.set(key, value);
        if (expire != -1L) {
            this.setExpire(key, expire, timeUnit == null ? TimeUnit.SECONDS : timeUnit);
        }
    }

    /**
     * String 设置值
     *
     * @param key   缓存key
     * @param value 缓存value
     */
    public void strSet(String key, String value) {
        this.valueOpr.set(key, value);
    }

    /**
     * String 获取值
     *
     * @param key 缓存key
     * @return 缓存value
     */
    public String strGet(String key) {
        return this.valueOpr.get(key);
    }

    public String strGet(String key, long expire, TimeUnit timeUnit) {
        String value = this.valueOpr.get(key);
        if (expire != -1L) {
            this.setExpire(key, expire, timeUnit == null ? TimeUnit.SECONDS : timeUnit);
        }

        return value;
    }

    public <T> T strGet(String key, Class<T> clazz) {
        String value = this.valueOpr.get(key);
        return value == null ? null : this.fromJson(value, clazz);
    }

    public <T> T strGet(String key, Class<T> clazz, long expire, TimeUnit timeUnit) {
        String value = this.valueOpr.get(key);
        if (expire != -1L) {
            this.setExpire(key, expire, timeUnit == null ? TimeUnit.SECONDS : timeUnit);
        }

        return value == null ? null : this.fromJson(value, clazz);
    }

    public String strGet(String key, Long start, Long end) {
        return this.valueOpr.get(key, start, end);
    }

    public Boolean strSetIfAbsent(String key, String value) {
        return this.valueOpr.setIfAbsent(key, value);
    }

    /**
     * String 获取自增字段，递减字段可使用delta为负数的方式
     *
     * @param key   缓存key
     * @param delta 只增量
     * @return Long Long
     */
    public Long strIncrement(String key, Long delta) {
        return this.valueOpr.increment(key, delta);
    }

    /**
     * List 推入数据至列表左端
     *
     * @param key key
     * @param value value
     * @return Long 值
     */
    public Long lstLeftPush(String key, String value) {
        return this.listOpr.leftPush(key, value);
    }

    /**
     * List 推入数据至列表左端
     *
     * @param key key
     * @param values values
     * @return Long 值
     */
    public Long lstLeftPushAll(String key, Collection<String> values) {
        return this.listOpr.leftPushAll(key, values);
    }

    /**
     * List 推入数据至列表右端
     *
     * @param key key
     * @param value value
     * @return Long 值
     */
    public Long lstRightPush(String key, String value) {
        return this.listOpr.rightPush(key, value);
    }

    /**
     * List 推入数据至列表右端
     *
     * @param key key
     * @param values values
     * @return Long 值
     */
    public Long lstRightPushAll(String key, Collection<String> values) {
        return this.listOpr.rightPushAll(key, values);
    }

    public List<String> lstRange(String key, long start, long end) {
        return this.listOpr.range(key, start, end);
    }

    public List<String> lstAll(String key) {
        return this.lstRange(key, 0L, this.lstLen(key));
    }

    /**
     * List 移除并返回列表最左端的项
     *
     * @param key key
     * @return String String
     */
    public String lstLeftPop(String key) {
        return this.listOpr.leftPop(key);
    }

    public String lstRightPop(String key) {
        return this.listOpr.rightPop(key);
    }

    public String lstLeftPop(String key, long timeout, TimeUnit timeUnit) {
        return this.listOpr.leftPop(key, timeout, timeUnit);
    }

    public String lstRightPop(String key, long timeout, TimeUnit timeUnit) {
        return this.listOpr.rightPop(key, timeout, timeUnit);
    }

    public Long lstLen(String key) {
        return this.listOpr.size(key);
    }

    /**
     * List 设置指定索引上的列表项。将列表键 key索引index上的列表项设置为value。
     * 如果index参数超过了列表的索引范围，那么命令返回了一个错误
     *
     * @param key key
     * @param index index
     * @param value value
     */
    public void lstSet(String key, long index, String value) {
        this.listOpr.set(key, index, value);
    }

    public Long lstRemove(String key, long index, String value) {
        return this.listOpr.remove(key, index, value);
    }

    /**
     * List 返回列表键key中，指定索引index上的列表项。index索引可以是正数或者负数
     *
     * @param key
     * @param index
     * @return
     */
    public Object lstIndex(String key, long index) {
        return this.listOpr.index(key, index);
    }

    public void lstTrim(String key, long start, long end) {
        this.listOpr.trim(key, start, end);
    }
    /**
     * Set 将数组添加到给定的集合里面，已经存在于集合的元素会自动的被忽略， 命令返回新添加到集合的元素数量。
     *
     * @param key key
     * @param values values
     * @return Long Long
     */
    public Long setAdd(String key, String[] values) {
        return this.setOpr.add(key, values);
    }

    public Long setIrt(String key, String... values) {
        return this.setOpr.add(key, values);
    }
    /**
     * Set 将返回集合中所有的元素。
     *
     * @param key key
     * @return Set<String> Set<String>
     */
    public Set<String> setMembers(String key) {
        return this.setOpr.members(key);
    }

    public Boolean setIsmember(String key, String o) {
        return this.setOpr.isMember(key, o);
    }

    public Long setSize(String key) {
        return this.setOpr.size(key);
    }
    /**
     * Set 计算所有给定集合的交集，并返回结果
     *
     * @param key key
     * @param otherKey otherKey
     * @return Set<String> Set<String>
     */
    public Set<String> setIntersect(String key, String otherKey) {
        return this.setOpr.intersect(key, otherKey);
    }
    /**
     * Set 计算所有的并集并返回结果
     *
     * @param key key
     * @param otherKey otherKey
     * @return Set<String> Set<String>
     */
    public Set<String> setUnion(String key, String otherKey) {
        return this.setOpr.union(key, otherKey);
    }
    /**
     * Set 计算所有的并集并返回结果
     *
     * @param key key
     * @param otherKeys otherKeys
     * @return Set<String>
     */
    public Set<String> setUnion(String key, Collection<String> otherKeys) {
        return this.setOpr.union(key, otherKeys);
    }
    /**
     * Set 返回一个集合的全部成员，该集合是所有给定集合之间的差集
     *
     * @param key key
     * @param otherKey otherKey
     * @return Set<String>
     */
    public Set<String> setDifference(String key, String otherKey) {
        return this.setOpr.difference(key, otherKey);
    }
    /**
     * Set 返回一个集合的全部成员，该集合是所有给定集合之间的差集
     *
     * @param key key
     * @param otherKeys otherKeys
     * @return Set<String>
     */
    public Set<String> setDifference(String key, Collection<String> otherKeys) {
        return this.setOpr.difference(key, otherKeys);
    }

    public Long setDel(String key, String value) {
        return this.setOpr.remove(key, value);
    }

    public Long setRemove(String key, Object[] value) {
        return this.setOpr.remove(key, value);
    }

    public Boolean zSetAdd(String key, String value, double score) {
        return this.zSetOpr.add(key, value, score);
    }

    public Double zSetScore(String key, String value) {
        return this.zSetOpr.score(key, value);
    }

    public Double zSetIncrementScore(String key, String value, double delta) {
        return this.zSetOpr.incrementScore(key, value, delta);
    }

    public Long zSetRank(String key, String value) {
        return this.zSetOpr.rank(key, value);
    }

    public Long zSetReverseRank(String key, String value) {
        return this.zSetOpr.reverseRank(key, value);
    }

    public Long zSetSize(String key) {
        return this.zSetOpr.size(key);
    }

    public Long zSetRemove(String key, String value) {
        return this.zSetOpr.remove(key, value);
    }

    public Long zSetRemoveByScore(String key, double min, double max) {
        return this.zSetOpr.removeRangeByScore(key, min, max);
    }

    public Set<String> zSetRange(String key, Long start, Long end) {
        return this.zSetOpr.range(key, start, end);
    }

    public Set<String> zSetReverseRange(String key, Long start, Long end) {
        return this.zSetOpr.reverseRange(key, start, end);
    }

    public Set<String> zSetRangeByScore(String key, Double min, Double max) {
        return this.zSetOpr.rangeByScore(key, min, max);
    }

    public Set<String> zSetReverseRangeByScore(String key, Double min, Double max) {
        return this.zSetOpr.reverseRangeByScore(key, min, max);
    }

    public Set<String> zSetRangeByScore(String key, Double min, Double max, Long offset, Long count) {
        return this.zSetOpr.rangeByScore(key, min, max, offset, count);
    }

    public Set<String> zSetReverseRangeByScore(String key, Double min, Double max, Long offset, Long count) {
        return this.zSetOpr.reverseRangeByScore(key, min, max, offset, count);
    }

    public Long zSetCount(String key, Double min, Double max) {
        return this.zSetOpr.count(key, min, max);
    }

    /**
     * Hash 将哈希表 key 中的域 field的值设为 value。如果 key不存在，一个新的哈希表被创建并进行HSET操作。
     * 如果域 field已经存在于哈希表中，旧值将被覆盖
     *
     * @param key     key
     * @param hashKey hashKey
     * @param value   value
     */
    public void hshPut(String key, String hashKey, String value) {
        this.hashOpr.put(key, hashKey, value);
    }

    public void hshPutAll(String key, Map<String, String> map) {
        this.hashOpr.putAll(key, map);
    }

    public byte[] hshGetSerial(String key, String hashKey) {
        RedisSerializer<String> redisSerializer = this.redisTemplate.getStringSerializer();
        return (byte[]) redisTemplate.execute((RedisCallback<Object>) (connection) -> {
            try {
                return connection.hGet(Objects.requireNonNull(redisSerializer.serialize(key)), Objects.requireNonNull(redisSerializer.serialize(hashKey)));
            } catch (Exception var6) {
                this.logger.error("获取HASH对象序列失败", var6);
                return null;
            }
        });
    }

    public Boolean hshPutSerial(String key, String hashKey, byte[] value) {
        RedisSerializer<String> redisSerializer = this.redisTemplate.getStringSerializer();
        return (Boolean) redisTemplate.execute((RedisCallback<Object>) (connection) -> {
            try {
                return connection.hSet(Objects.requireNonNull(redisSerializer.serialize(key)), Objects.requireNonNull(redisSerializer.serialize(hashKey)), value);
            } catch (Exception e) {
                this.logger.error("插入HASH对象序列失败", e);
                return Boolean.FALSE;
            }
        });
    }

    /**
     * Hash 返回哈希表 key 中给定域 field的值，返回值：给定域的值。当给定域不存在或是给定 key不存在时，返回 nil。
     *
     * @param key key
     * @param hashKey hashKey
     * @return String
     */
    public String hshGet(String key, String hashKey) {
        return this.hashOpr.get(key, hashKey);
    }

    public List<String> hshMultiGet(String key, Collection<String> hashKeys) {
        return this.hashOpr.multiGet(key, hashKeys);
    }

    public Map<String, String> hshGetAll(String key) {
        return this.hashOpr.entries(key);
    }

    /**
     * Hash 查看哈希表 key 中，给定域 field是否存在
     *
     * @param key     key
     * @param hashKey hashKey
     * @return Boolean
     */
    public Boolean hshHasKey(String key, String hashKey) {
        return this.hashOpr.hasKey(key, hashKey);
    }

    public Set<String> hshKeys(String key) {
        return this.hashOpr.keys(key);
    }

    public List<String> hshVals(String key) {
        return this.hashOpr.values(key);
    }

    public List<String> hshVals(String key, Collection<String> hashKeys) {
        return this.hashOpr.multiGet(key, hashKeys);
    }

    public Long hshSize(String key) {
        return this.hashOpr.size(key);
    }

    public void hshDelete(String key, Object... hashKeys) {
        this.hashOpr.delete(key, hashKeys);
    }

    public void hshRemove(String key, Object[] hashKeys) {
        this.hashOpr.delete(key, hashKeys);
    }

    public <T> String toJson(T object) {
        if (object == null) {
            return "";
        } else if (!(object instanceof Integer) && !(object instanceof Long) && !(object instanceof Float) && !(object instanceof Double) && !(object instanceof Boolean) && !(object instanceof String)) {
            try {
                return objectMapper.writeValueAsString(object);
            } catch (JsonProcessingException var3) {
                return "";
            }
        } else {
            return String.valueOf(object);
        }
    }

    public <T> T fromJson(String json, Class<T> clazz) {
        if (!StringUtils.isBlank(json) && clazz != null) {
            try {
                return objectMapper.readValue(json, clazz);
            } catch (Exception var4) {
                if (this.logger.isErrorEnabled()) {
                    this.logger.error(var4.getMessage(), var4);
                }

                return null;
            }
        } else {
            return null;
        }
    }

    public <T> T fromJson(String json, TypeReference valueTypeRef) {
        if (!StringUtils.isBlank(json) && valueTypeRef != null) {
            try {
                return (T) objectMapper.readValue(json, valueTypeRef);
            } catch (Exception var4) {
                if (this.logger.isErrorEnabled()) {
                    this.logger.error(var4.getMessage(), var4);
                }

                return null;
            }
        } else {
            return null;
        }
    }

    public <T> List<T> fromJsonList(String json, Class<T> clazz) {
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, clazz);

        try {
            return objectMapper.readValue(json, javaType);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    public <T> void objectSet(String key, T object) {
        this.strSet(key, this.toJson(object));
    }

    static {
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(Date.class, new DateSerializer());
        javaTimeModule.addDeserializer(Date.class, new DateDeserializer());
        objectMapper.registerModule(javaTimeModule);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
}
