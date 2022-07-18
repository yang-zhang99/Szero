package com.ttdo.core.redis.safe;


@FunctionalInterface
public interface ExecuteWithResult<T> {
    T get();
}

