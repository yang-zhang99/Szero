package com.yang.redis.safe;


import com.yang.redis.RedisHelper;
import com.yang.redis.convertor.ApplicationContextHelper;

public class SafeRedisHelper {
    private static RedisHelper redisHelper;

    public SafeRedisHelper() {
    }

    public static void execute(int db, ExecuteNoneResult executor) {
        try {
            redisHelper.setCurrentDatabase(db);
            executor.accept();
        } finally {
            redisHelper.clearCurrentDatabase();
        }

    }

    public static <T> T execute(int db, ExecuteWithResult<T> executor) {
        Object var2;
        try {
            redisHelper.setCurrentDatabase(db);
            var2 = executor.get();
        } finally {
            redisHelper.clearCurrentDatabase();
        }

        return (T) var2;
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
        Object var3;
        try {
            redisHelper.setCurrentDatabase(db);
            var3 = executor.get();
        } finally {
            redisHelper.clearCurrentDatabase();
        }

        return (T) var3;
    }

    static {
        ApplicationContextHelper.asyncStaticSetter(RedisHelper.class, SafeRedisHelper.class, "redisHelper");
    }
}
