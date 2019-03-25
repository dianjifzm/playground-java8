package org.projectbarbel.playground.revisitevolatile;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.Executors;

import org.junit.jupiter.api.Test;
import org.projectbarbel.playground.testutil.ConcurrentExecutor;

public class SingletonConcurrentTest {

    @Test
    public void mutableSingleton() throws InterruptedException {
        long time = ConcurrentExecutor.time(Executors.newCachedThreadPool(), 10,
                () -> System.out.println(MutableSingleton.getInstance().isSomeFlag()));
        System.out.println(time);
        assertTrue(time>0);
    }
    @Test
    public void mutableSingletonSynchronized() throws InterruptedException {
        long time = ConcurrentExecutor.time(Executors.newCachedThreadPool(), 10,
                () -> System.out.println(MutableSingletonSynchronized.getInstance().isSomeFlag()));
        System.out.println(time);
        assertTrue(time>0);
    }
    @Test
    public void mutableSingletonComplete() throws InterruptedException {
        long time = ConcurrentExecutor.time(Executors.newCachedThreadPool(), 10,
                () -> System.out.println(MutableSingletonComplete.getInstance().getCounter()));
        System.out.println(time);
        assertTrue(time>0);
    }
}
