package org.projectbarbel.playground.safeaccessor;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Thread-safe shared object instance.
 */
public final class SharedObject {

    /**
     * Exclusive read-write lock.
     */
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    /**
     * example mutable state field
     */
    private volatile String data;

    /**
     * other mutable state according to the locking idiom rules declared
     */

    /**
     * Package private default constructor.
     */
    SharedObject() {
    }

    /**
     * Package private copy constructor.
     */
    SharedObject(SharedObject template) {
        this.data = template.data;
    }

    boolean readLock() {
        return lock.readLock().tryLock();
    }
    
    boolean writeLock() {
        return lock.writeLock().tryLock();
    }
    
    void readUnlock() {
        lock.readLock().unlock();
    }

    void writeUnlock() {
        lock.writeLock().unlock();
    }
    
    public String getData() {
        return data;
    }

    /**
     * Package private setter.
     */
    void setData(String data) {
        this.data = data;
    }

}
