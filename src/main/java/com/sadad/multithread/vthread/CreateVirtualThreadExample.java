package com.sadad.multithread.vthread;

import java.util.concurrent.Executors;

public class CreateVirtualThreadExample {
    public static void main(String[] args) throws InterruptedException {
        Runnable run = () -> System.out.println("This is a thread " + Thread.currentThread());
        var th = Thread.ofVirtual().name("VThread").unstarted(run);
        th.start();
        th.join();

        try (var es = Executors.newVirtualThreadPerTaskExecutor()) {
            es.submit(run);
        }

        Thread.ofPlatform().name("platform").start(run);
    }
}
