package org.projectbarbel.playground.pseudolocking;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A shared object example that only allows creation and state mutation by
 * package neighbors. Implements the pseudo-locking idiom which includes
 * following rules: <br>
 * 1. one AtomicBoolean declared for pseudo-locking <br>
 * 2. any methods that mutate state should only available to package neighbors
 * <br>
 * 3. all mutable state is declared volatile <br>
 * 4. if the volatile field happens to be an object reference, one of the
 * following may apply: <br>
 * 4.1. the object is effectively immutable, if not strictly immutable (final
 * immutable members only)<br>
 * 4.2. the object referenced by the field itself adheres to rules 2->4.2<br>
 * If these rules are not applied, the shared object cannot be used in the
 * pseudo-locking idiom. It rather needs some synchronization by intrinsic
 * locks.
 */
public final class SharedObjectAtomic {

    // atomic boolean to "pseudo-lock" the instance
    private final AtomicBoolean locked = new AtomicBoolean();

    // example mutable state field
    private volatile String data;

    // other mutable state according to the pseudo-locking idiom rules declared

    /**
     * Default constructor only accessible to package neighbors.
     */
    SharedObjectAtomic() {
    }

    /**
     * Copy constructor.
     */
    SharedObjectAtomic(SharedObjectAtomic template) {
        this.data = template.data;
    }

    /**
     * Pseudo-lock the object.
     */
    boolean lockAcquired() {
        if (locked.compareAndSet(false, true)) {
            // reinitialize state
            return true;
        } else
            return false;
    }

    /**
     * Pseudo-unlock object.
     */
    boolean unlock() {
        return locked.compareAndSet(true, false);
    }

    /**
     * Read state available for any thread.
     * 
     * @return data
     */
    public String getData() {
        return data;
    }

    void setData(String data) {
        this.data = data;
    }

}
