package org.projectbarbel.playground.singleton;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

public enum SingletonState {
    
    NOT_INITIALIZED(InitializingSingleton::create), 
    INITIALIZED(InitializingSingleton::get);
    
    private final BiFunction<InitializingSingleton<?>, Object[], ?> resourceFunction;

    private SingletonState(final BiFunction<InitializingSingleton<?>, Object[], ?> resourceFunction) {
        this.resourceFunction = resourceFunction;
    }

    @SuppressWarnings("unchecked")
    public <T> T getResource(final InitializingSingleton<T> singleton, final Object... args) {
        return (T) resourceFunction.apply(singleton, args);
    }

    public interface InitializingSingleton<T> {
        
        static final String RESOURCE = "resource";
        static final String STATE = "state";
        static final Map<InitializingSingleton<?>, Object> initCompletedCache = new ConcurrentHashMap<>();
        static final Map<InitializingSingleton<?>, Object> resourceCache = new HashMap<>();

        default T get(Object... args) {
            @SuppressWarnings("unchecked")
            T resource = (T) resourceCache.computeIfAbsent(this, this::resource);
            return resource;
        }

        default T resource(InitializingSingleton<?> singleton) {
            try {
                Field resourceField = this.getClass().getDeclaredField(RESOURCE);
                resourceField.setAccessible(true);
                @SuppressWarnings("unchecked")
                T resource = (T) resourceField.get(this);
                return resource;
            } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException
                    | SecurityException e) {
                throw new IllegalStateException("could not get 'resource' with defaul method", e);
            }
        }

        default void setInitialized() {
            try {
                Field stateField = this.getClass().getDeclaredField(STATE);
                stateField.setAccessible(true);
                stateField.set(this, SingletonState.INITIALIZED);
            } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
                throw new IllegalStateException("could not access 'state' with default method", e);
            }
        }

        default T init(Object... args) {
            createResource(args);
            setInitialized();
            return get();
        }

        void createResource(Object... args);

        static <T> T create(InitializingSingleton<T> singleton, Object... args) {
            @SuppressWarnings("unchecked")
            T resource = (T) initCompletedCache.computeIfAbsent(singleton, s -> s.init(args));
            return resource;
        }

    }
}
