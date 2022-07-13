package com.ttdo.gateway.common;

import java.util.Set;

public interface SetRepository<T> {


    boolean isEnable();

    Set<T> get();

    boolean contains(String key);
}
