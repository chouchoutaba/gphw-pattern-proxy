package com.gphw.pattern.proxy.dynamicProxy.gphwProxy;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class GphwHouseAgent implements GphwInvocationHandler {
    private Object obj;

    public Object getInstance(Object obj) {
        this.obj = obj;
        Class cl = obj.getClass();
        return GphwProxy.newProxyInstance(new GphwClassLoader(), cl.getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object o = method.invoke(obj, args);
        after();
        return o;
    }

    private void before() {
        System.out.println("开始搜索房源");
    }

    private void after() {
        System.out.println("价格谈妥，成交！");
    }
}
