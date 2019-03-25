package org.projectbarbel.playground.safeaccessor;

import java.util.ConcurrentModificationException;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import org.projectbarbel.playground.testutil.ConcurrentExecutor;

import io.github.benas.randombeans.api.EnhancedRandom;

public class SafeAccessorServiceTest {

    public static void main(String[] args) throws InterruptedException {
        
        SafeAccessorService accessor = new SafeAccessorService();
        
        System.out.println("\n*** getting an instance ***\n");
        
        ConcurrentExecutor.time(Executors.newFixedThreadPool(4, r -> {
            Thread t = Executors.defaultThreadFactory().newThread(r);
            t.setDaemon(true);
            return t;
        }), 4, () -> {
            accessor.get("test");
            System.out.println("done");
        });
        
        System.out.println("\n*** randomly updating an instance ***\n");

        ConcurrentExecutor.time(Executors.newFixedThreadPool(4, r -> {
            Thread t = Executors.defaultThreadFactory().newThread(r);
            t.setDaemon(true);
            return t;
        }), 4, () -> {
            try {
                accessor.updateData("test", EnhancedRandom.random(String.class));
                System.out.println("done");
            } catch (ConcurrentModificationException e) {
                System.out.println("object was locked, handle this and then try again later");
            }
        });
        
        System.out.println("\n*** updating an instance with some data ***\n");
        
        ConcurrentExecutor.time(Executors.newFixedThreadPool(4, r -> {
            Thread t = Executors.defaultThreadFactory().newThread(r);
            t.setDaemon(true);
            return t;
        }), 4, () -> {
            try {
                accessor.updateData("test", "some data");
                System.out.println("done");
            } catch (ConcurrentModificationException e) {
                System.out.println("object was locked, handle this and then try again later");
            }
        });
        
        System.out.println("\n*** updating an instance using a semaphore ***\n");
        
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
