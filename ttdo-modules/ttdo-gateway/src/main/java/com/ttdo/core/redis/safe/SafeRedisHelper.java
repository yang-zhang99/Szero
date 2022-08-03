package com.ttdo.core.redis.safe;


import com.ttdo.core.convertor.ApplicationContextHelper;
import com.ttdo.core.redis.RedisHelper;

public class SafeRedisHelper {
    private static RedisHelper redisHelper;


    public static void execute(int db, ExecuteNoneResult executor) {
        try {
            redisHelper.setCurrentDatabase(db);
            executor.accept();
        } finally {
            redisHelper.clearCurrentDatabase();
        }
    }

    public static <T> T execute(int db, ExecuteWithResult<T> executor) {
        T var2;
        try {
            redisHelper.setCurrentDatabase(db);
            var2 = executor.get();
        } finally {
            redisHelper.clearCurrentDatabase();
        }
        return var2;
    }

    public static void execute(int db, RedisHelper redisHelper, ExecuteNoneResult executor) {
        try {
            redisHelper.setCurrentDatabase(db);
            executor.accept();
        } finally {
            redisHelper.clearCurrentDatabase();
        }
    }

    public static <T> T execute(int db, RedisHelper redisHelper, ExecuteWithResult<T> executor) {
        T rtu;
        try {
            redisHelper.setCurrentDatabase(db);
            rtu = executor.get();
        } finally {
            redisHelper.clearCurrentDatabase();
        }
        return rtu;
    }

    static {
        ApplicationContextHelper.asyncStaticSetter(RedisHelper.class, SafeRedisHelper.class, "redisHelper");
    }
}
