package com.yang.lock.service;


import com.yang.lock.LockInfo;

public interface LockService {

    void setLockInfo(LockInfo lockInfo);

    LockInfo getLockInfo();

    void clearLockInfo();

    boolean lock();

    void releaseLock();

}
