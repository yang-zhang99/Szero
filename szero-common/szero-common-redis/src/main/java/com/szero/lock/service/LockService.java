package com.szero.lock.service;


import com.szero.lock.LockInfo;

public interface LockService {

    void setLockInfo(LockInfo lockInfo);

    LockInfo getLockInfo();

    void clearLockInfo();

    boolean lock();

    void releaseLock();

}
