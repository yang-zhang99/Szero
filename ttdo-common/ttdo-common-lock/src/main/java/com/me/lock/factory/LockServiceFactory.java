package com.me.lock.factory;


import com.me.lock.enums.LockType;
import com.me.lock.service.LockService;
import com.me.lock.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LockServiceFactory {

    @Autowired
    private FairLockServiceImpl fairLockService;
    @Autowired
    private WriteLockServiceImpl writeLockService;
    @Autowired
    private MultiLockServiceImpl multiLockService;
    @Autowired
    private ReadLockServiceImpl readLockService;
    @Autowired
    private RedLockServiceImpl redLockService;
    @Autowired
    private ReentrantLockServiceImpl reentrantLockService;

    public LockService getLock(LockType lockType) {
        switch (lockType) {
            case FAIR:
                return fairLockService;
            case WRITE:
                return writeLockService;
            case MULTI:
                return multiLockService;
            case READ:
                return readLockService;
            case RED:
                return redLockService;
            case REENTRANT:
                return reentrantLockService;
            default:
                return fairLockService;
        }
    }
}
