package com.me.demo.lock;

public class ReadWriteLockImpl implements ReadWriteLock {

    private final Object MUTEX = new Object();

    private int writingWriters = 0;

    private int waitingWriters = 0;

    private int readingReaders = 0;

    private boolean preferWriter;

    public ReadWriteLockImpl(boolean preferWriter) {
        this.preferWriter = preferWriter;
    }

    public ReadWriteLockImpl() {
        this(true);
    }

    @Override
    public Lock readLock() {
        return null;
    }

    @Override
    public Lock writeLock() {
        return null;
    }

    @Override
    public int getWritingWriters() {
        return 0;
    }

    @Override
    public int getWaitingWriters() {
        return 0;
    }

    @Override
    public int getReadingReaders() {
        return 0;
    }
}
