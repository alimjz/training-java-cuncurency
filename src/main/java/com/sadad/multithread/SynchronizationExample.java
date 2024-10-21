package com.sadad.multithread;

public class SynchronizationExample {
    public static void main(String[] args) {
        SynchronizationExample synchronizationExample = new SynchronizationExample();
        new Thread(() -> {
            try {
                synchronizationExample.method1();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        new Thread(synchronizationExample::method2).start();
        new Thread(() -> {
            try {
                staticMethod();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        new Thread(SynchronizationExample::staticMethod2).start();
    }

    public static synchronized void staticMethod() throws InterruptedException {
        System.out.println("static method");
        Thread.sleep(5000);

    }

    public static synchronized void staticMethod2() {
        System.out.println("static method2");

    }

    public synchronized void method1() throws InterruptedException {
        System.out.println("method1");
        Thread.sleep(1000);
    }

    public synchronized void method2() {
        System.out.println("method2");
    }
}
