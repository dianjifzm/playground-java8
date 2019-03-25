package org.projectbarbel.playground.pseudolocking;

import java.util.ConcurrentModificationException;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import org.projectbarbel.playground.testutil.ConcurrentExecutor;

public class SafeAccessorServiceTest {

    public static void main(String[] args) throws InterruptedException {
        
        SafeAccessorService accessor = new SafeAccessorService();
        
        ConcurrentExecutor.time(Executors.newFixedThreadPool(4, r -> {
            Thread t = Executors.defaultThreadFactory().newThread(r);
            t.setDaemon(true);
            return t;
        }), 4, () -> {
            accessor.get("test");
            System.out.println("done");
        });
        
        ConcurrentExecutor.time(Executors.newFixedThreadPool(4, r -> {
            Thread t = Executors.defaultThreadFactory().newThread(r);
            t.setDaemon(true);
            return t;
        }), 4, () -> {
            try {
                accessor.updateData("test", "data");
                System.out.println("done");
            } catch (ConcurrentModificationException e) {
                System.out.println("failed, handle this - maybe do soemthing else and then try again later");
            }
        });
        
        Semaphore semaphore = new Semaphore(1);
        ConcurrentExecutor.time(Executors.newFixedThreadPool(4, r -> {
            Thread t = Executors.defaultThreadFactory().newThread(r);
            t.setDaemon(true);
            return t;
        }), 4, () -> {
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            accessor.updateData("test", "data");
            semaphore.release();
            System.out.println("done with semaphore");
        });
    }
}
