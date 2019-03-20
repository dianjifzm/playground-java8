package org.projectbarbel.playground.revisitevolatileagain;

public class SafelyPublishedImmutablePrivateConstructorSingleton {
    public static final SafelyPublishedImmutablePrivateConstructorSingleton INSTANCE = new SafelyPublishedImmutablePrivateConstructorSingleton();
    private final boolean someFlag = true;
    // other final fields
    
    private SafelyPublishedImmutablePrivateConstructorSingleton() {
    }
    public boolean isSomeFlag() {
        return someFlag;
    }
}
