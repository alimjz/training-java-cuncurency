package com.sadad.multithread;

import java.util.LinkedList;
import java.util.Queue;

class SampleResource {
    private final Queue<Integer> queue = new LinkedList<>();
    private final int capacity;

    public SampleResource(int capacity) {
        this.capacity = capacity;
    }

    public void produce(int value) throws InterruptedException {
        synchronized (this) {
            while (queue.size() == capacity) {
                System.out.println("Queue is full, producer is waiting...");
                wait(); // Wait until space is available
            }
            queue.add(value);
            System.out.println("Produced: " + value);
            notify(); // Notify the consumer that an item has been produced
        }
    }

    public int consume() throws InterruptedException {
        synchronized (this) {
            while (queue.isEmpty()) {
                System.out.println("Queue is empty, consumer is waiting...");
                wait(); // Wait until an item is available
            }
            int value = queue.poll();
            System.out.println("Consumed: " + value);
            notify(); // Notify the producer that space is available
            return value;
        }
    }
}

class Producer implements Runnable {
    private final SampleResource resource;

    public Producer(SampleResource resource) {
        this.resource = resource;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                resource.produce(i);
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

class Consumer implements Runnable {
    private final SampleResource resource;

    public Consumer(SampleResource resource) {
        this.resource = resource;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                resource.consume();
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

public class WaitNotifyExample {
    public static void main(String[] args) {
        SampleResource sampleResources = new SampleResource(5); // Queue capacity of 5

        Thread producerThread = new Thread(new Producer(sampleResources), "Producer");
        Thread consumerThread = new Thread(new Consumer(sampleResources), "Consumer");

        producerThread.start();
        consumerThread.start();
    }
}