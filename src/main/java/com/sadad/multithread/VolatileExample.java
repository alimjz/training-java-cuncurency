package com.sadad.multithread;

public class VolatileExample {
    private boolean running = true; // Volatile variable

    public static void main(String[] args) throws InterruptedException {
        VolatileExample example = new VolatileExample();
        example.startThreads();
    }

    private void startThreads() {
        Thread workerThread = new Thread(() -> {
            while (running) {
                // Simulate some work
                System.out.println("Thread is running...");
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println("Thread has stopped.");
        });

        workerThread.start();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        running = false;
        System.out.println("Requested to stop the thread.");
    }
}