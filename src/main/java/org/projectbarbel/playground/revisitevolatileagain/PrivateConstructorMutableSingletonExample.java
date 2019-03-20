package org.projectbarbel.playground.revisitevolatileagain;

import java.util.Timer;
import java.util.TimerTask;

public class PrivateConstructorMutableSingletonExample {

    private long counter = 0;

    public Object[] execute(Object... arguments) {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                PrivateConstructorMutableSingleton.INSTANCE.setSomeFlag(true);
                System.out.println("Timer interrupted main thread ...");
                timer.cancel();
            }
        }, 1000);
        while (!PrivateConstructorMutableSingleton.INSTANCE.isSomeFlag()) {
            counter++;
        };
        System.out.println("Main thread was interrupted by timer ...");
        return new Object[] { counter, PrivateConstructorMutableSingleton.INSTANCE.isSomeFlag() };
    }

    private class Worker implements Runnable {
        @Override
        public void run() {
            Object[] result = execute();
            System.out.println(result[0]+"/"+result[1]);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        PrivateConstructorMutableSingletonExample volatileExample = new PrivateConstructorMutableSingletonExample();
        Thread thread1 = new Thread(volatileExample.new Worker(), "Worker-1");
        thread1.start();
        Thread.sleep(5000);
    }
}