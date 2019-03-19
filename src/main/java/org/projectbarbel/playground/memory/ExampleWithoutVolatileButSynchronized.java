package org.projectbarbel.playground.memory;

import java.util.Timer;
import java.util.TimerTask;

public class ExampleWithoutVolatileButSynchronized {

    private boolean expired;
    private long counter = 0;
    private Object mutext = new Object();

    public Object[] execute(Object... arguments) {
        expired = false;
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                synchronized (mutext) {
                    expired = true;
                    System.out.println("Timer interrupted main thread ...");
                }
                timer.cancel();
            }
        }, 1000);
        while (!expired) {
            synchronized (mutext) {
                counter++; // do some work
            }
        }
        ;
        System.out.println("Main thread was interrupted by timer ...");
        return new Object[] { counter, expired };
    }

    private class Worker implements Runnable {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println(execute());
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExampleWithoutVolatileButSynchronized volatileExample = new ExampleWithoutVolatileButSynchronized();
        Thread thread1 = new Thread(volatileExample.new Worker(), "Worker-1");
        thread1.start();
        Thread.sleep(5000);
        thread1.interrupt();
    }
}