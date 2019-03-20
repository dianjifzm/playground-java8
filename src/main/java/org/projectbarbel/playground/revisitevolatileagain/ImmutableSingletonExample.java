package org.projectbarbel.playground.revisitevolatileagain;

import java.util.Timer;
import java.util.TimerTask;

public class ImmutableSingletonExample {

    private long counter = 0;

    public Object[] execute(Object... arguments) {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                System.out.println("Reading immutable state: " + ImmutableSingleton.getInstance().isSomeFlag());
                timer.cancel();
            }
        }, 1000);
        while (!ImmutableSingleton.getInstance().isSomeFlag()) {
            counter++;
        };
        System.out.println("Main thread was interrupted by timer ...");
        return new Object[] { counter, ImmutableSingleton.getInstance().isSomeFlag() };
    }

    private class Worker implements Runnable {
        @Override
        public void run() {
            System.out.println(execute());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ImmutableSingletonExample volatileExample = new ImmutableSingletonExample();
        Thread thread1 = new Thread(volatileExample.new Worker(), "Worker-1");
        thread1.start();
        Thread.sleep(5000);
        thread1.interrupt();
    }
}