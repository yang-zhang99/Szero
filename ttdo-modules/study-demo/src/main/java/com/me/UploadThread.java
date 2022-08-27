package com.me;

public class UploadThread implements Runnable {
    @Override
    public void run() {
        throw new IllegalStateException("test-illegal");
    }
}
