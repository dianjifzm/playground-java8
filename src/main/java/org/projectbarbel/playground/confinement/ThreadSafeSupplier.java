package org.projectbarbel.playground.confinement;

import java.util.function.Supplier;

public class ThreadSafeSupplier implements Supplier<String>{

    static String someString = new String();
    
    @Override
    public String get() {
        return someString;
    }

}
