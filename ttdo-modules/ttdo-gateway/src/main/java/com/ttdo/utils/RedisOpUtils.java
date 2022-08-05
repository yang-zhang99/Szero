package com.ttdo.utils;

import com.ttdo.gateway.filter.Query;
import com.yang.redis.RedisHelper;


public class RedisOpUtils {

    public static <T> T selectDbAndClear(RedisHelper redisHelper, int db, Query<T> query){

        try {
            redisHelper.setCurrentDatabase(db);
            return query.get();
        }finally {
            redisHelper.clearCurrentDatabase();
        }

    }
}
