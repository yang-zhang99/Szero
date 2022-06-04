package com.szero;

public class UploadThread implements Runnable {
    @Override
    public void run() {
        throw new IllegalStateException("test-illegal");
    }
}
