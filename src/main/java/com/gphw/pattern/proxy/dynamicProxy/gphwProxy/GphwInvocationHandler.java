package com.gphw.pattern.proxy.dynamicProxy.gphwProxy;

import java.lang.reflect.Method;

public interface GphwInvocationHandler {
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable;
}
