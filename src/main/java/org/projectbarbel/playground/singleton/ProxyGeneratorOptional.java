package org.projectbarbel.playground.singleton;

import java.lang.reflect.Method;
import java.util.Optional;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class ProxyGeneratorOptional {

    public final static ProxyGeneratorOptional INSTANCE = new ProxyGeneratorOptional();
    private Enhancer resource;

    private ProxyGeneratorOptional() {
    }

    public <T> T getProxy(T template) {
        @SuppressWarnings("unchecked")
        T proxy = (T) Optional.ofNullable(resource).orElseGet(()->createResource(template.getClass()))
                .create(new Class[] { template.getClass() }, new Object[] { template });
        return proxy;
    }

    private synchronized Enhancer createResource(Class<?> type) {
        if (resource == null) {
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
            this.resource = resource;
        }
        return resource;
    }


}
