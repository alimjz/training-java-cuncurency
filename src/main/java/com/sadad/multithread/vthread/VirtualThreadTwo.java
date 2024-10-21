package com.sadad.multithread.vthread;

import java.util.ArrayList;
import java.util.List;

public class VirtualThreadTwo {
    public static void main(String[] args) throws InterruptedException {
        var lock = new Object();
        Runnable task1 = () -> {
//            synchronized (lock) {
                System.out.println(Thread.currentThread());
                sleep(10);
                System.out.println(Thread.currentThread());
//            }
//            synchronized (lock) {
                sleep(10);
                System.out.println(Thread.currentThread());
//            }
//            synchronized (lock) {
                sleep(10);
                System.out.println(Thread.currentThread());
//            }
        };
        Runnable task2 = () -> {
//            synchronized (lock) {
                sleep(10);
                sleep(10);
                sleep(10);
//            }
        };
        List<Thread> threads = new ArrayList<Thread>();
        for (int i = 0; i < 10; i++) {
            var thread = i == 0 ? Thread.ofVirtual().unstarted(task1) :
                    Thread.ofVirtual().unstarted(task2);
            threads.add(thread);
        }

        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
    }

    private static void sleep(long duration) {
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
