package com.sadad.multithread;

import java.util.concurrent.CountDownLatch;

class WorkerCountDownLatch implements Runnable {
    private final CountDownLatch latch;
    private final String name;

    public WorkerCountDownLatch(CountDownLatch latch, String name) {
        this.latch = latch;
        this.name = name;
    }

    @Override
    public void run() {
        try {
            // Simulate work by sleeping for a random time
            int workDuration = (int) (Math.random() * 3000);
            System.out.println(name + " is working for " + workDuration + " milliseconds.");
            Thread.sleep(workDuration);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            System.out.println(name + " has finished its work.");
            latch.countDown(); // Decrement the count of the latch
        }
    }
}

public class CountDownLatchExample {
    public static void main(String[] args) throws InterruptedException {
        int numberOfWorkers = 5;
        CountDownLatch latch = new CountDownLatch(numberOfWorkers); // Initialize the latch with the number of worker threads

        // Create and start worker threads
        for (int i = 1; i <= numberOfWorkers; i++) {
            new Thread(new WorkerCountDownLatch(latch, "Worker-" + i)).start();
        }

        System.out.println("Main thread is waiting for workers to finish...");

        latch.await(); // Wait until the count reaches zero

        System.out.println("All workers have finished. Main thread resumes execution.");
    }
}
