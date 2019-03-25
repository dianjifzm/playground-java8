package org.projectbarbel.playground.concurrency;

import java.util.PriorityQueue;
import java.util.Queue;

import io.github.benas.randombeans.api.EnhancedRandom;

public class NotifyAndWait {

    private static Queue<String> queue = new PriorityQueue<>();
    private static Object mutex = new Object();
    private static volatile int counter = 0;

    public static void main(String[] args) throws InterruptedException {
        Runnable producer = new Runnable() {

            @Override
            public void run() {
                while (true) {
                    synchronized (mutex) {
                        while (queue.size() > 0) {
                            try {
                                mutex.wait();
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                        }
                        queue.add(EnhancedRandom.random(String.class));
                        counter++;
                        mutex.notifyAll();
                    }
                }
            }
        };
        Runnable consumer = new Runnable() {

            @Override
            public void run() {
                while (true) {
                    synchronized (mutex) {
                        while (queue.isEmpty()) {
                            try {
                                mutex.wait();
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                        }
                        System.out.println(queue.poll());
                        mutex.notifyAll();
                    }
                }
            }
        };
        Thread thread1 = new Thread(producer);
        thread1.setDaemon(true);
        Thread thread2 = new Thread(consumer);
        thread2.setDaemon(true);
        thread1.start();
        thread2.start();
        Thread.sleep(5000);
        System.out.println(counter);
    }
}
