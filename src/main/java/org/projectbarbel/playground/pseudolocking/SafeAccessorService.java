package org.projectbarbel.playground.pseudolocking;

import java.util.ConcurrentModificationException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Thread safe access to shared object.
 */
public final class SafeAccessorService {

    /**
     * {@link ConcurrentHashMap} that safely publishes the objects to all threads,
     * without letting any of the objects escape.
     */
    private final Map<String, SharedObject> sharedObjects = new ConcurrentHashMap<String, SharedObject>();

    public SafeAccessorService() {
    }

    /**
     * Managed thread-safe access to shared instances.
     * 
     * @param objectId         the object id to access
     * @param compoundAction the atomic operation applied to the shared instance
     * @param lock             the function that locks the object
     * @param unlock           the function that unlocks the object
     * @return the updated instance
     */
    private SharedObject access(String objectId, Function<SharedObject, SharedObject> compoundAction,
            Function<SharedObject, Boolean> lock, Consumer<SharedObject> unlock) {
        // eventually new instance, safely created and one-time published by
        // ConcurrentHashmap
        SharedObject sharedObject = sharedObjects.computeIfAbsent(objectId, this::createSharedInstance);
        if (lock.apply(sharedObject)) {
            try {
                // thread-safe mutation block; shared object needs to adhere to locking rules
                return compoundAction.apply(sharedObject);
            } finally {
                unlock.accept(sharedObject);
            }
        } else {
            // clients need to be prepared for this to happen
            throw new ConcurrentModificationException(
                    "the shared object with id=" + objectId + " is locked - try again later");
        }
    }

    /**
     * Example update method of an accessor service. Others can be defined.
     * @param objectId the object to update
     * @param data the data to set on the object
     */
    public void updateData(String objectId, String data) {
        access(objectId, so -> {
            so.setData(data);
            return so;
        }, so -> so.writeLock(), so -> so.writeUnlock());
    }

    /**
     * Get access returns valid snapshot of shared object. The original instance
     * should not escape, as it is required that clients always work on valid shared
     * state. Method creates an instance if there is non for the passed object id.
     * 
     * @param objectId id of the {@link SharedObject}
     * @return copy of shared object
     */
    public SharedObject get(String objectId) {
        return access(objectId, so -> new SharedObject(so), so -> so.readLock(), so -> so.readUnlock());
    }

    /**
     * Remove an object hard from the map.
     * 
     * @param objectId object id to remove
     */
    public void remove(String objectId) {
        sharedObjects.remove(objectId);
    }

    /**
     * Method to create new shared object instances.
     * 
     * @param id the id of the shared object.
     * @return the created shared object instance
     */
    private SharedObject createSharedInstance(String id) {
        return new SharedObject();
    }

}
