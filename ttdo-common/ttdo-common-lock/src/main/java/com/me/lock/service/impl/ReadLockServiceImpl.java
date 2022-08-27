package com.me.lock.service.impl;


import com.me.lock.LockInfo;
import com.me.lock.service.LockService;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class ReadLockServiceImpl implements LockService {


    @Qualifier("lockRedissonClient")
    @Autowired
    private RedissonClient redissonClient;

    private final ThreadLocal<LockInfo> lockInfoThreadLocal = new ThreadLocal();

    @Override
    public void setLockInfo(LockInfo lockInfo) {
        this.lockInfoThreadLocal.set(lockInfo);
    }

    @Override
    public LockInfo getLockInfo() {
        return this.lockInfoThreadLocal.get();
    }

    @Override
    public void clearLockInfo() {
        this.lockInfoThreadLocal.remove();
    }

    @Override
    public boolean lock() {
        LockInfo lockInfo = this.lockInfoThreadLocal.get();
        try {
            RReadWriteLock rLock = this.redissonClient.getReadWriteLock(lockInfo.getName());
            return rLock.readLock().tryLock(lockInfo.getWaitTime(), lockInfo.getLeaseTime(), lockInfo.getTimeUnit());
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void releaseLock() {
        LockInfo lockInfo = this.lockInfoThreadLocal.get();
        RReadWriteLock rLock = this.redissonClient.getReadWriteLock(lockInfo.getName());
        if (rLock.readLock().isHeldByCurrentThread()) {
            rLock.readLock().unlockAsync();
        }

        this.lockInfoThreadLocal.remove();
    }
}
