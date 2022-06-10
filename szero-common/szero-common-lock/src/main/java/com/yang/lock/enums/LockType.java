package com.yang.lock.enums;

/**
 * 分布式锁的种类
 *
 * @author yang99
 */
public enum LockType {

    REENTRANT,
    FAIR,
    MULTI,
    RED,
    READ,
    WRITE;

}
