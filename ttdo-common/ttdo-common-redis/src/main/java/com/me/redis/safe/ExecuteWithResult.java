package com.me.redis.safe;

@FunctionalInterface
public interface ExecuteWithResult<T> {
    T get();
}
