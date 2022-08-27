package com.me.lock.service;


import com.me.lock.LockInfo;

public interface LockService {

    void setLockInfo(LockInfo lockInfo);

    LockInfo getLockInfo();

    void clearLockInfo();

    boolean lock();

    void releaseLock();

}
