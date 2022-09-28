package com.me.demo.observer;

import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {

        final TaskLifecycle<String> lifecycle = new TaskLifecycle.EmptyLifecycle<String>(){
            public void onFinish(Thread thread, String result) {
                System.out.println("This result is" + result);
            }
        };

        Observable observable = new ObservableThread<>(lifecycle,  () -> {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(" finished done .");

            return "Hello Observer";
        });

        observable.start();

    }
}
