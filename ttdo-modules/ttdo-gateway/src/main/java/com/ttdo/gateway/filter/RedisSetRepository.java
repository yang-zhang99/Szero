package com.ttdo.gateway.filter;

import com.ttdo.core.redis.RedisHelper;
import com.ttdo.gateway.common.StringSetRepository;
import com.ttdo.gateway.filter.metric.Query;
import com.ttdo.utils.RedisOpUtils;

import java.util.Set;

public abstract class RedisSetRepository implements StringSetRepository {

    protected static final String MODULE_CONTEXT = "hadm";
    protected static final String SPLIT = ":";
    protected static final String ENABLE = "enable";
    protected static final String VALUE = "value";
    protected static final String ID_PLACEHOLDER = "{id}";

    private static final int SELECT_DB = 1;

    private String keyPrefix;

    private RedisHelper redisHelper;

    public RedisSetRepository(String mode, RedisHelper redisHelper) {
        this.keyPrefix = MODULE_CONTEXT + SPLIT + mode + SPLIT;
        this.redisHelper = redisHelper;
    }

    private <T> T selectDbAndClear(RedisHelper redisHelper, Query<T> query){
        return RedisOpUtils.selectDbAndClear(redisHelper, SELECT_DB, query);
    }

    @Override
    public boolean isEnable() {
        return selectDbAndClear(redisHelper, () -> Boolean.parseBoolean(redisHelper.strGet(getKeyPrefix() + ENABLE)));
    }

    @Override
    public Set<String> get() {
        return selectDbAndClear(redisHelper, () -> redisHelper.setMembers(getKeyPrefix() + VALUE));
    }

    @Override
    public boolean contains(String key) {
        return selectDbAndClear(redisHelper, () -> redisHelper.setIsmember(getKeyPrefix() + VALUE, key));
    }

    protected String getKeyPrefix() {
        return keyPrefix;
    }

    protected RedisHelper getRedisHelper() {
        return redisHelper;
    }
}
