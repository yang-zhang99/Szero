package com.ttdo.gateway.common;

import java.util.Set;

/**
 * 只读列表
 * @param <T>
 */
public interface SetRepository<T> {

    boolean isEnable();

    Set<T> get();

    boolean contains(String key);
}
