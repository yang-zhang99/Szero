package com.yang.lock.service.impl;


import com.yang.lock.LockInfo;
import com.yang.lock.service.LockService;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @author yang99
 */
public class WriteLockServiceImpl implements LockService {

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
        LockInfo lockInfo = lockInfoThreadLocal.get();

        try {
            RReadWriteLock rLock = redissonClient.getReadWriteLock(lockInfo.getName());
            return rLock.writeLock().tryLock(lockInfo.getWaitTime(), lockInfo.getLeaseTime(), lockInfo.getTimeUnit());
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void releaseLock() {
        LockInfo lockInfo = lockInfoThreadLocal.get();
        RReadWriteLock rLock = redissonClient.getReadWriteLock(lockInfo.getName());
        if (rLock.writeLock().isHeldByCurrentThread()) {
            rLock.writeLock().unlockAsync();
        }

        this.lockInfoThreadLocal.remove();
    }

}
