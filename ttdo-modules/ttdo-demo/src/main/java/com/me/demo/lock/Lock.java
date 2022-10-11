package com.me.demo.lock;

public interface Lock {


    void lock() throws InterruptedException;

    void unlock();
}
