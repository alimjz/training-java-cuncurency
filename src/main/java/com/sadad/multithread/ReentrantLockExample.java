package com.sadad.multithread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class SharedResource {
    private final Lock lock = new ReentrantLock();
    private int sharedCounter = 0;

    // Method to increment the counter
    public void increment() {
        lock.lock(); // Acquire the lock
        try {
            sharedCounter++;
            System.out.println(Thread.currentThread().getName() + " incremented counter to: " + sharedCounter);
            // Call another method that also requires the lock
            doAdditionalWork();

        } finally {
            lock.unlock(); // Release the lock
        }
    }

    // Method to perform additional work while holding the lock
    public void doAdditionalWork() {
        lock.lock(); // Acquire the lock again (reentrant)
        try {
            System.out.println(Thread.currentThread().getName() + " is doing additional work.");
            // Simulate some work with sleep
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock(); // Release the lock
        }
    }

    public int getSharedCounter() {
        return sharedCounter;
    }
}

class Worker implements Runnable {
    private final SharedResource resource;

    public Worker(SharedResource resource) {
        this.resource = resource;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            resource.increment();
            try {
                Thread.sleep(50); // Simulate some delay between increments
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

public class ReentrantLockExample {
    public static void main(String[] args) {
        SharedResource sharedResource = new SharedResource();

        Thread thread1 = new Thread(new Worker(sharedResource), "Thread-1");
        Thread thread2 = new Thread(new Worker(sharedResource), "Thread-2");

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Final Counter Value: " + sharedResource.getSharedCounter());
    }
}