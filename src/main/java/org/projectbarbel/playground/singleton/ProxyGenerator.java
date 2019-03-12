package org.projectbarbel.playground.singleton;

import java.lang.reflect.Method;

import org.projectbarbel.playground.singleton.SingletonState.InitializingSingleton;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * A singleton that requires initialization work, and like that is not enough,
 * also requires initialization based on run-time data passed by clients. <br>
 * -> creating proxying infrastructure based on type information acquired at
 * run-time<br>
 * -> creating network infrastructure based on connection data acquired at
 * runtime
 * 
 * @author Niklas Schlimm
 *
 */
public class ProxyGenerator implements InitializingSingleton<Enhancer> {

    public final static ProxyGenerator INSTANCE = new ProxyGenerator();
    private SingletonState state = SingletonState.NOT_INITIALIZED;
    @SuppressWarnings("unused")
    private Enhancer resource;

    private ProxyGenerator() {
    }

    @SuppressWarnings("unchecked")
    public <T> T getProxy(T template) {
        return (T) state.getResource(this, template.getClass()).create(new Class[] { template.getClass() },
                new Object[] { template });
    }

    @Override
    public void createResource(Object... args) {
        System.out.println("created once");
        Enhancer resource = new Enhancer();
        resource.setInterfaces(new Class[] {});
        resource.setSuperclass((Class<?>) args[0]);
        resource.setCallback(new MethodInterceptor() {

            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                return proxy.invokeSuper(obj, args);
            }
        });
        this.resource = resource;
    }

    @Override
    public Enhancer get(Object... args) {
        return resource;
    }

}
