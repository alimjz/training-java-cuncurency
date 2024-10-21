package com.sadad.multithread;

import java.util.HashSet;
import java.util.Set;

public class ThreadExample {

    private int counter = 0;

    public static void main(String[] args) throws InterruptedException {
        ThreadExample threadExample = new ThreadExample();
        threadExample.demo();
    }

    public void demo() throws InterruptedException {
        Set<Thread> threads = new HashSet<>();

        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(this::increment);
            thread.setName("thread-" + i);
            threads.add(thread);
        }

        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
//            thread.join();
        }

        System.out.println("hello!!!!!");
    }

    public synchronized void increment() {
        counter++;
        System.out.println(Thread.currentThread().getName() + " incremented counter = " + counter);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}