package org.projectbarbel.playground.revisitevolatile;

public class MutableSingletonSynchronized {
    private static MutableSingletonSynchronized INSTANCE;
    private static final Object mutex = new Object();
    private boolean someFlag;
    // more mutable state on this singleton
    private MutableSingletonSynchronized(boolean someFlag) {
        this.someFlag = someFlag;
    }
    public static MutableSingletonSynchronized getInstance() {
        if (INSTANCE == null) {
            synchronized (mutex) {
                if (INSTANCE == null)
                    INSTANCE = new MutableSingletonSynchronized(false);
            }
        }
        return INSTANCE;

    }
    public synchronized boolean isSomeFlag() {
        return someFlag;
    }
    public synchronized void setSomeFlag(boolean someFlag) {
        this.someFlag = someFlag;
    }
}
