package org.projectbarbel.playground.revisitevolatile;

import java.util.Timer;
import java.util.TimerTask;

public class MutableSingletonSynchronizedTest {

    private long counter = 0;

    public Object[] execute(Object... arguments) {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                MutableSingletonSynchronized.getInstance().setSomeFlag(true);
                System.out.println("Timer interrupted main thread ...");
                timer.cancel();
            }
        }, 1000);
        while (!MutableSingletonSynchronized.getInstance().isSomeFlag()) {
            counter++;
        };
        System.out.println("Main thread was interrupted by timer ...");
        return new Object[] { counter, MutableSingletonSynchronized.getInstance().isSomeFlag() };
    }

    private class Worker implements Runnable {
        @Override
        public void run() {
            Object[] result = execute();
            System.out.println(result[0]+"/"+result[1]);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MutableSingletonSynchronizedTest volatileExample = new MutableSingletonSynchronizedTest();
        Thread thread1 = new Thread(volatileExample.new Worker(), "Worker-1");
        thread1.start();
        Thread.sleep(5000);
    }
}