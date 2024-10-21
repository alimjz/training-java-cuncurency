package com.sadad.multithread;

import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrencyExample {
    // Atomic variable for thread-safe increment
    private static final AtomicInteger atomicCounter = new AtomicInteger(0);

    // Volatile variable to ensure visibility across threads
    private static volatile int volatileCounter = 0;

    public static void main(String[] args) {
        // Creating threads using Runnable
        System.out.println("Program started with : " + Thread.currentThread().getName());
        System.out.println("is main daemon? : " + Thread.currentThread().isDaemon());
        Thread thread1 = new Thread(new CounterTask());
        Thread thread2 = new Thread(new CounterTask());

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Final Atomic Counter: " + atomicCounter.get());
        System.out.println("Final Volatile Counter: " + volatileCounter);
    }

    static class CounterTask implements Runnable {
        @Override
        public void run() {
            System.out.println("is Daemon? : " + Thread.currentThread().isDaemon());
            for (int i = 0; i < 1000; i++) {
                atomicCounter.incrementAndGet(); // Atomic operation
            }

            synchronized (ConcurrencyExample.class) { // Synchronized block
                for (int i = 0; i < 1000; i++) {
                    volatileCounter++; // Not atomic, but synchronized access ensures thread safety
                }
            }
        }
    }
}
