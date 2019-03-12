package org.projectbarbel.playground.singleton;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

public class SingletonIdioms {

    public enum EnumSingleton {
        INSTANCE;
    }
    
    public static class PrivateConstructorSingleton {
        public static final PrivateConstructorSingleton INSTANCE = new PrivateConstructorSingleton();
        private PrivateConstructorSingleton() {
        }
    }
    
    public static class LazyLoadingSingleton {
        private LazyLoadingSingleton() {}
        private static class LazyHolder {
            static final LazyLoadingSingleton INSTANCE = new LazyLoadingSingleton();
        }
        public static LazyLoadingSingleton getInstance(String runtimeData) {
            return LazyHolder.INSTANCE;
        }
    }
    
    public static class ConcurrentHashMapSingleton {
        private static final ConcurrentHashMap<Class<?>, Object> INSTANCES = new ConcurrentHashMap<>();
        public <T> T getInstance(Class<T> clazz) throws InstantiationException, IllegalAccessException {
            @SuppressWarnings("unchecked")
            T instance = (T) INSTANCES.computeIfAbsent(clazz, ConcurrentHashMapSingleton::newInstance);
            return instance;
        }
        private static <T> T newInstance(Class<T> clazz) {
            try {
                return clazz.newInstance();
            } catch (Exception e) {
                throw new IllegalStateException("could not create instance", e);
            }
        }
    }
        
    public static class AtomicReferenceSingleton {
        private static final AtomicReference<Object> reference = new AtomicReference<>();
        public Object getInstance(Class<?> clazz) throws InstantiationException, IllegalAccessException {
            if(reference.compareAndSet(null, clazz.newInstance()))
                return reference.get();
            else
                return reference.get();
        }
    }
    
}
