package org.projectbarbel.playground.revisitevolatile;

public class MutableSingletonComplete {
    private static volatile MutableSingletonComplete INSTANCE;
    private static final Object mutex = new Object();
    private boolean someFlag;
    // more mutable state on this singleton
    private MutableSingletonComplete(boolean someFlag) {
        this.someFlag = someFlag;
    }
    public static MutableSingletonComplete getInstance() {
        if (INSTANCE == null) {
            synchronized (mutex) {
                if (INSTANCE == null)
                    INSTANCE = new MutableSingletonComplete(false);
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
