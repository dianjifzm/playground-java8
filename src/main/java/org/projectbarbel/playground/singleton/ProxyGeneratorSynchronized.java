package org.projectbarbel.playground.singleton;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class ProxyGeneratorSynchronized {

    public final static ProxyGeneratorSynchronized INSTANCE = new ProxyGeneratorSynchronized();
    private Enhancer resource;

    private ProxyGeneratorSynchronized() {
    }

    @SuppressWarnings("unchecked")
    public <T> T getProxy(T template) {
        synchronized (this) {
            if (resource==null) {
                createResource(template.getClass());
                return (T) resource.create(new Class[] { template.getClass() },
                        new Object[] { template });
            }
            return (T) resource.create(new Class[] { template.getClass() },
                    new Object[] { template });
        }
    }

    private void createResource(Object... args) {
        System.out.println("created once");
        resource = new Enhancer();
        resource.setInterfaces(new Class[] {});
        resource.setSuperclass((Class<?>) args[0]);
        resource.setCallback(new MethodInterceptor() {

            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                return proxy.invokeSuper(obj, args);
            }
        });
    }

}
