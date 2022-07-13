package com.ttdo.gateway.filter.metric;

/**
 * @author XCXCXCXCX
 * @version 1.2.0
 * @date 2019/12/6 1:46 下午
 */
@FunctionalInterface
public interface Query<T>{
    T get();
}
