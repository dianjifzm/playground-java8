package org.projectbarbel.playground.revisitevolatileagain;

public class ImmutableSingleton {
    private static ImmutableSingleton INSTANCE;
    private static Object mutext = new Object();
    private final boolean someFlag = true;
    // ... other final immutable state
    private ImmutableSingleton() {
    }
    public static ImmutableSingleton getInstance() {
        if (INSTANCE==null) {
            synchronized (mutext) {
                if (INSTANCE==null) {
                    INSTANCE = new ImmutableSingleton();
                }
            }
        }
        return INSTANCE;
    }
    public boolean isSomeFlag() {
        return someFlag;
    }
}
