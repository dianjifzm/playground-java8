package org.projectbarbel.playground.revisitevolatileagain;

public class PrivateConstructorMutableSingleton {
    public static final PrivateConstructorMutableSingleton INSTANCE = new PrivateConstructorMutableSingleton();
    private volatile boolean someFlag;
    private PrivateConstructorMutableSingleton() {
    }
    public boolean isSomeFlag() {
        return someFlag;
    }
    public synchronized void setSomeFlag(boolean someFlag) {
        this.someFlag = someFlag;
    }
}
