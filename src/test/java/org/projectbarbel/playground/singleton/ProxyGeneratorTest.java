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
                        safePrintln(client.toString());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }

        gate.await();
        safePrintln("all threads started - state");
        safePrintln("waiting");
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
                        safePrintln(client.toString());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }

        gate.await();
        safePrintln("all threads started - synchronized");
        safePrintln("waiting");
        Thread.sleep(5000);

    }

    @Test
    public void testCreateResourceMap() throws Exception {
        final CyclicBarrier gate = new CyclicBarrier(16);

        for (int i = 0; i < 15; i++) {
            new Thread() {
                public void run() {
                    try {
                        gate.await();
                        Client client = ProxyGeneratorMap.INSTANCE.getProxy(new Client("1234", "Niklas"));
                        safePrintln(client.toString());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }

        gate.await();
        safePrintln("all threads started - map");
        safePrintln("waiting");
        Thread.sleep(5000);

    }

    @Test
    public void testCreateResourceOptional() throws Exception {
        final CyclicBarrier gate = new CyclicBarrier(16);

        for (int i = 0; i < 15; i++) {
            new Thread() {
                public void run() {
                    try {
                        gate.await();
                        Client client = ProxyGeneratorOptional.INSTANCE.getProxy(new Client("1234", "Niklas"));
                        safePrintln(client.toString());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }

        gate.await();
        safePrintln("all threads started - Optional");
        safePrintln("waiting");
        Thread.sleep(5000);

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

    public void safePrintln(String s) {
        synchronized (System.out) {
            System.out.println(s);
        }
    }

}
