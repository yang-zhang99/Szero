package com.yang;

public class UploadThread implements Runnable {
    @Override
    public void run() {
        throw new IllegalStateException("test-illegal");
    }
}
