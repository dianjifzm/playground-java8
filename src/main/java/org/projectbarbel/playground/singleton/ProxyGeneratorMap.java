package org.projectbarbel.playground.singleton;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class ProxyGeneratorMap {

    public final static ProxyGeneratorMap INSTANCE = new ProxyGeneratorMap();
    private Map<Class<?>, Enhancer> map = new ConcurrentHashMap<>();

    private ProxyGeneratorMap() {
    }

    public <T> T getProxy(T template) {
        @SuppressWarnings("unchecked")
        T proxy = (T) map.computeIfAbsent(template.getClass(), this::createResource)
                .create(new Class[] { template.getClass() }, new Object[] { template });
        return proxy;
    }

    private Enhancer createResource(Class<?> type) {
        System.out.println("created once");
        Enhancer resource = new Enhancer();
        resource.setInterfaces(new Class[] {});
        resource.setSuperclass(type);
        resource.setCallback(new MethodInterceptor() {

            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                return proxy.invokeSuper(obj, args);
            }
        });
        return resource;
    }

}
