package com.szero.lock.service.impl;


import com.szero.lock.LockInfo;
import com.szero.lock.service.LockService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class ReentrantLockServiceImpl implements LockService {

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
            RLock rLock = this.redissonClient.getLock(lockInfo.getName());
            return rLock.tryLock(lockInfo.getWaitTime(), lockInfo.getLeaseTime(), lockInfo.getTimeUnit());
        } catch (Exception var3) {
            return false;
        }
    }

    @Override
    public void releaseLock() {
        LockInfo lockInfo = (LockInfo) this.lockInfoThreadLocal.get();
        RLock rLock = this.redissonClient.getLock(lockInfo.getName());
        if (rLock.isHeldByCurrentThread()) {
            rLock.unlockAsync();
        }

        this.lockInfoThreadLocal.remove();
    }

}
