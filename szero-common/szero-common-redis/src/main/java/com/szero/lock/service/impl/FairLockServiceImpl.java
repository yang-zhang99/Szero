package com.szero.lock.service.impl;


import com.szero.lock.LockInfo;
import com.szero.lock.service.LockService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @author yang99
 */
public class FairLockServiceImpl implements LockService {

    @Qualifier("lockRedissonClient")
    @Autowired
    private RedissonClient redissonClient;

    private final ThreadLocal<LockInfo> lockInfoThreadLocal = new ThreadLocal<>();

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
        LockInfo lockInfo = lockInfoThreadLocal.get();
        RLock rLock = redissonClient.getFairLock(lockInfo.getName());
        try {
            return rLock.tryLock(lockInfo.getWaitTime(), lockInfo.getLeaseTime(), lockInfo.getTimeUnit());
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void releaseLock() {
        LockInfo lockInfo = lockInfoThreadLocal.get();
        RLock rLock = redissonClient.getFairLock(lockInfo.getName());
        if (rLock.isHeldByCurrentThread()) {
            rLock.unlockAsync();
        }
        this.lockInfoThreadLocal.remove();
    }
}
