package com.ttdo.utils;

import com.ttdo.core.redis.RedisHelper;
import com.ttdo.gateway.filter.metric.Query;


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
