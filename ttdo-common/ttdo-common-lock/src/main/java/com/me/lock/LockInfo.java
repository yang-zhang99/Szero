package com.me.lock;


import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 锁的信息
 */
public class LockInfo {

    /**
     * 锁的名称
     */
    private String name;
    /**
     * 过期时间
     */
    private long waitTime;
    /**
     * 租赁时间
     */
    private long leaseTime;


    private TimeUnit timeUnit;


    private List<String> keyList;


    public LockInfo() {
        this.timeUnit = TimeUnit.SECONDS;
    }

    public LockInfo(String name, long waitTime, long leaseTime, TimeUnit timeUnit) {
        this.name = name;
        this.waitTime = waitTime;
        this.leaseTime = leaseTime;
        this.timeUnit = timeUnit;
    }

    public LockInfo(String name, long waitTime, long leaseTime, TimeUnit timeUnit, List<String> keyList) {
        this.name = name;
        this.waitTime = waitTime;
        this.leaseTime = leaseTime;
        this.timeUnit = timeUnit;
        this.keyList = keyList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(long waitTime) {
        this.waitTime = waitTime;
    }

    public long getLeaseTime() {
        return leaseTime;
    }

    public void setLeaseTime(long leaseTime) {
        this.leaseTime = leaseTime;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    public List<String> getKeyList() {
        return keyList;
    }

    public void setKeyList(List<String> keyList) {
        this.keyList = keyList;
    }
}
