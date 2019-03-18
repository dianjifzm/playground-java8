package org.projectbarbel.playground.singleton;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import org.apache.commons.configuration2.ImmutableConfiguration;
import org.junit.jupiter.api.Test;

public class SQLDataSourceConfigOptionalTest {

    @Test
    public void testCreateResource() throws Exception {
        final CyclicBarrier gate = new CyclicBarrier(16);

        for (int i = 0; i < 15; i++) {
            new Thread() {
                public void run() {
                    try {
                        gate.await();
                        for (int j = 0; j < 1000; j++) {
                            ImmutableConfiguration configuration = SQLDataSourceConfigOptional.INSTANCE.configuration("database.properties");
                            configuration.getString("test");
                        }
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

    public void safePrintln(String s) {
        synchronized (System.out) {
            System.out.println(s);
        }
    }

}
