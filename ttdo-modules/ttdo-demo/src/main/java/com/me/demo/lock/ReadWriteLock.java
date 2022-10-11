package com.me.demo.lock;

public interface ReadWriteLock {

    Lock readLock();

    Lock writeLock();

    int getWritingWriters();

    int getWaitingWriters();

    int getReadingReaders();

//    static ReadWriteLock readWriteLock(){
////        return new ReadL
//    }
}
