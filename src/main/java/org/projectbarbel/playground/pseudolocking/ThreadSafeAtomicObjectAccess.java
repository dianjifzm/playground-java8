package org.projectbarbel.playground.pseudolocking;

import java.util.ConcurrentModificationException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * Thread safe access to shared object.
 */
public final class ThreadSafeAtomicObjectAccess {

    /**
     * {@link ConcurrentHashMap} that safely publishes the objects to all threads,
     * without letting any of the objects escape.
     */
    private final Map<String, SharedObjectAtomic> sharedObjects = new ConcurrentHashMap<String, SharedObjectAtomic>();

    public ThreadSafeAtomicObjectAccess() {
    }

    public void update(String objectId, String data) {
        access(objectId, so -> {
            so.setData(data);
            return so;
        });
    }

    /**
     * Get access returns copy of shared object. The original instance should not
     * escape, as it is required that clients always work on valid state.
     * 
     * @param objectId id of the {@link SharedObjectAtomic}
     * @return copy of shared object
     */
    public SharedObjectAtomic get(String objectId) {
        return access(objectId, so -> new SharedObjectAtomic(so));
    }

    /**
     * Thread-safe pseudo-lock idiom applied.
     * 
     * @param objectId         object id to access
     * @param accessorFunction function that works on the shared object.
     * @return the eventually updated shared object
     */
    private SharedObjectAtomic access(String objectId, Function<SharedObjectAtomic, SharedObjectAtomic> accessorFunction) {
        // eventually new instance, safely created and one-time published by
        // ConcurrentHashmap
        SharedObjectAtomic sharedObject = sharedObjects.computeIfAbsent(objectId, this::createSharedInstance);
        if (sharedObject.lockAcquired()) {
            try {
                // pseudo-thread-safe mutation block; shared object needs to adhere to
                // pseudo-locking rules
                return accessorFunction.apply(sharedObject);
            } finally {
                sharedObject.unlock();
            }
        } else {
            // clients need to be prepared for this to happen
            throw new ConcurrentModificationException(
                    "the shared object with id=" + objectId + " is locked - try again later");
        }
    }

    /**
     * Method to create new shared object instances.
     * 
     * @param id the id of the shared object.
     * @return the created shared object instance
     */
    private SharedObjectAtomic createSharedInstance(String id) {
        return new SharedObjectAtomic();
    }

}
