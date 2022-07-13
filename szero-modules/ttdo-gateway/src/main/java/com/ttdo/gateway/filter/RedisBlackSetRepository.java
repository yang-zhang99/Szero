package com.ttdo.gateway.filter;

import com.ttdo.core.redis.RedisHelper;

import java.util.Set;

public class RedisBlackSetRepository extends RedisSetRepository {
    public RedisBlackSetRepository(String mode, RedisHelper redisHelper) {
        super(mode, redisHelper);
    }

//    private static final String BLACKLIST_TEMPLATE_KEY = MODULE_CONTEXT + SPLIT +
//            "blacklist" + SPLIT +
//            ID_PLACEHOLDER + SPLIT + VALUE;
//
//    private static String getBlackListKey(Long id) {
//        return BLACKLIST_TEMPLATE_KEY.replace(RedisSetRepository.ID_PLACEHOLDER, String.valueOf(id));
//    }
//
//    public RedisBlackSetRepository(RedisHelper redisHelper) {
//        super("blacklist", redisHelper);
//    }
//
//    public Set<String> getBlacklistById(Long id) {
//        return getRedisHelper().setMembers(getBlackListKey(id));
//    }
//
//    public void blacklist(Long id, String ip) {
//        getRedisHelper().setAdd(getBlackListKey(id), new String[]{ip});
//    }

}
