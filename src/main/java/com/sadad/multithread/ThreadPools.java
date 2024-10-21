package com.sadad.multithread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ThreadPools {
    public static void main(String[] args) {
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool(Thread.ofVirtual().factory());
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);
        ExecutorService virtualExecutor = Executors.newVirtualThreadPerTaskExecutor();

        cachedThreadPool.execute(() -> {
            System.out.println(Thread.currentThread());
        });

        cachedThreadPool.close();

        virtualExecutor.execute(() -> {
            System.out.println(Thread.currentThread());
        });

        virtualExecutor.close();
    }
}
