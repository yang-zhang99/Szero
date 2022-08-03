package com.ttdo.gateway.filter;

import com.ttdo.core.redis.RedisHelper;

import java.util.Set;
public class RedisWhiteSetRepository extends RedisSetRepository {

    private static final String WHITELIST_TEMPLATE_KEY = MODULE_CONTEXT + SPLIT +
            "whitelist" + SPLIT +
            ID_PLACEHOLDER + SPLIT + VALUE;

    private static String getWhiteListKey(Long id) {
        return WHITELIST_TEMPLATE_KEY.replace(RedisSetRepository.ID_PLACEHOLDER, String.valueOf(id));
    }

    public RedisWhiteSetRepository(RedisHelper redisHelper) {
        super("whitelist", redisHelper);
    }

    public Set<String> getWhitelistById(Long id){
        return getRedisHelper().setMembers(getWhiteListKey(id));
    }
}
