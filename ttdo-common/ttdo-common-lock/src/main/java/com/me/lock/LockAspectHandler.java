package com.me.lock;


import com.me.core.exception.CommonException;
import com.me.lock.annotation.Lock;
import com.me.lock.factory.LockServiceFactory;
import com.me.lock.service.LockService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 分布式锁 @Lock 的具体实现
 *
 * @author yang99
 */
@Aspect
@Component
@Order(0)
public class LockAspectHandler {

    // 锁信息
    @Autowired
    private LockInfoProvider lockInfoProvider;

    // 锁类型
    @Autowired
    private LockServiceFactory lockServiceFactory;


    @Around(value = "@annotation(lock)")
    public Object around(ProceedingJoinPoint joinPoint, Lock lock) throws Throwable {
        LockInfo lockInfo = lockInfoProvider.getLockInfo(joinPoint, lock);
        LockService lockService = lockServiceFactory.getLock(lock.lockType());

        if (lockService.getLockInfo() != null && !lockInfo.equals(lockService.getLockInfo())) {
            return joinPoint.proceed();
        } else {
            lockService.setLockInfo(lockInfo);
            boolean lockFlag = false;

            Object rtu;
            try {
                boolean lockRes = lockService.lock();
                if (!lockRes) {
                    throw new CommonException("Get lock failed.", new Object[0]);
                }
                lockFlag = true;
                rtu = joinPoint.proceed();
            } finally {
                if (lockFlag) {
                    lockService.releaseLock();
                    lockService.clearLockInfo();
                }
            }
            return rtu;
        }
    }


}
