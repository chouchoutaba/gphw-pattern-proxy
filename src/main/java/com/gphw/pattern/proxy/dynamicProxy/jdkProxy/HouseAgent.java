package com.gphw.pattern.proxy.dynamicProxy.jdkProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class HouseAgent implements InvocationHandler {

    private Object obj;
    public Object getInstance(Object obj){
        this.obj=obj;
        Class cl = obj.getClass();
        return Proxy.newProxyInstance(cl.getClassLoader(),cl.getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object o = method.invoke(obj,args);
        after();
        return o;
    }

    private void before() {
        System.out.println("开始搜索房源");
    }

    private void after(){
        System.out.println("价格谈妥，成交！");
    }
}
