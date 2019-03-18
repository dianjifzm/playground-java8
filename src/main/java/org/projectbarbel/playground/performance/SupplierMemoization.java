package org.projectbarbel.playground.performance;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import com.google.common.base.Stopwatch;
import com.google.common.base.Suppliers;

public class SupplierMemoization {

    static Supplier<BigInteger> memoizedSupplier = Suppliers.memoize(CostlySupplier::generateBigNumber);
    public static void main(String[] args) {
        Stopwatch watch = Stopwatch.createStarted();
        memoizedSupplier.get();
        System.out.println("Elapsed after first execution: " + watch.elapsed(TimeUnit.MILLISECONDS));
        memoizedSupplier.get();
        System.out.println("Elapsed after second execution: " + watch.elapsed(TimeUnit.MILLISECONDS));
        memoizedSupplier.get();
        System.out.println("Elapsed after third execution: " + watch.elapsed(TimeUnit.MILLISECONDS));
    }
    public static class CostlySupplier {
        static BigInteger generateBigNumber() {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            return new BigInteger("12345");
        }
    }
}
