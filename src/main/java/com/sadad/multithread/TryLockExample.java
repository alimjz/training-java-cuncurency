package com.sadad.multithread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Resource {
    private final Lock lock = new ReentrantLock();

    public void safeMethod() {
        lock.lock(); // Acquire the lock
        try {
            System.out.println(Thread.currentThread().getName() + " has acquired the lock.");
            // Simulate some work
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            System.out.println(Thread.currentThread().getName() + " releasing the lock.");
            lock.unlock(); // Release the lock
        }
    }

    public void tryLockMethod() {
        if (lock.tryLock()) { // Attempt to acquire the lock
            try {
                System.out.println(Thread.currentThread().getName() + " has acquired the lock using tryLock.");
                // Simulate some work
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                System.out.println(Thread.currentThread().getName() + " releasing the lock.");
                lock.unlock(); // Release the lock
            }
        } else {
            System.out.println(Thread.currentThread().getName() + " could not acquire the lock.");
        }
    }
}

public class TryLockExample {
    public static void main(String[] args) throws InterruptedException {
        Resource resource = new Resource();

        Thread thread1 = new Thread(resource::safeMethod);
        Thread thread2 = new Thread(resource::tryLockMethod);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }
}