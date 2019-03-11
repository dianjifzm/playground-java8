package org.projectbarbel.playground.singleton;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import org.junit.jupiter.api.Test;

import lombok.AllArgsConstructor;
import lombok.Data;

public class ProxyGeneratorTest {

    @Test
    public void testCreateResource() throws Exception {
        final CyclicBarrier gate = new CyclicBarrier(16);

        for (int i = 0; i < 15; i++) {
            new Thread() {
                public void run() {
                    try {
                        gate.await();
                        Client client = ProxyGenerator.INSTANCE.getProxy(new Client("1234", "Niklas"));
                        System.out.println(client);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }

        gate.await();
        System.out.println("all threads started");
        Thread.sleep(5000);

    }

    @Test
    public void testCreateResourceOldSchool() throws Exception {
        final CyclicBarrier gate = new CyclicBarrier(16);
        
        for (int i = 0; i < 15; i++) {
            new Thread() {
                public void run() {
                    try {
                        gate.await();
                        Client client = ProxyGeneratorSynchronized.INSTANCE.getProxy(new Client("1234", "Niklas"));
                        System.out.println(client);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
        
        gate.await();
        System.out.println("all threads started");
        Thread.sleep(5000);
        
    }
    
    public static class ProxyMaker implements Runnable {

        @Override
        public void run() {
            Client client = ProxyGenerator.INSTANCE.getProxy(new Client("1234", "Niklas"));
            System.out.println(client);
        }

    }

    @Data
    @AllArgsConstructor
    public static class Client {
        private String clientId;
        private String clientName;

        public Client(Client client) {
            this.clientId = client.getClientId();
            this.clientName = client.getClientName();
        }
        
    }

}
