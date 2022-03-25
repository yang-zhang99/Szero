package com.szero.lock.service.impl;


import com.szero.lock.LockInfo;
import com.szero.lock.service.LockService;
import org.redisson.RedissonMultiLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class MultiLockServiceImpl implements LockService {


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
        RLock[] lockList = new RLock[lockInfo.getKeyList().size()];
        for (int i = 0; i < lockInfo.getKeyList().size(); ++i) {
            lockList[i] = this.redissonClient.getLock(lockInfo.getKeyList().get(i));
        }
        try {
            RedissonMultiLock lock = new RedissonMultiLock(lockList);
            return lock.tryLock(lockInfo.getWaitTime(), lockInfo.getLeaseTime(), lockInfo.getTimeUnit());
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void releaseLock() {
        LockInfo lockInfo = this.lockInfoThreadLocal.get();
        RLock[] lockList = new RLock[lockInfo.getKeyList().size()];

        for (int i = 0; i < lockInfo.getKeyList().size(); ++i) {
            lockList[i] = this.redissonClient.getLock(lockInfo.getKeyList().get(i));
        }

        RedissonMultiLock lock = new RedissonMultiLock(lockList);
        lock.unlock();
        this.lockInfoThreadLocal.remove();
    }


}
