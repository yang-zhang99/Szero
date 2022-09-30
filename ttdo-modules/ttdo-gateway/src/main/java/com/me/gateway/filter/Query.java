package com.me.gateway.filter;

/**
 * @author XCXCXCXCX
 * @version 1.2.0
 */
@FunctionalInterface
public interface Query<T>{
    T get();
}
