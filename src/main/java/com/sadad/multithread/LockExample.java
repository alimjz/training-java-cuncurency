package com.sadad.multithread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class PrinterQueue {
    private final Lock queueLock = new ReentrantLock(true);

    public void printJob() {
        queueLock.lock(); // Acquire the lock
        try {
            long duration = (long) (Math.random() * 10000);
            System.out.println(Thread.currentThread().getName() +
                    ": PrintQueue: Printing a Job during " + (duration / 1000) + " seconds.");
            Thread.sleep(duration); // Simulate printing time
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.printf("%s: The document has been printed\n", Thread.currentThread().getName());
            queueLock.unlock(); // Release the lock
        }
    }
}

public class LockExample {
    public static void main(String[] args) {
        PrinterQueue printerQueue = new PrinterQueue();
        Thread[] threads = new Thread[10];

        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(() -> {new PrinterQueue().printJob();}, "Thread " + i);
            threads[i].start();
        }

        // Wait for all threads to finish
        for (int i = 0; i < 10; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}