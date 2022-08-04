package com.yang.redis.safe;

@FunctionalInterface
public interface ExecuteWithResult<T> {
    T get();
}
