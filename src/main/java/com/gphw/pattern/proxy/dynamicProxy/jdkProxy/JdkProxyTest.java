package com.gphw.pattern.proxy.dynamicProxy.jdkProxy;

import java.lang.reflect.Method;

public class JdkProxyTest {
    public static void main(String[] args) {
        Object obj = new HouseAgent().getInstance(new Man());
        try {
            Method method =obj.getClass().getMethod("findBigHouse",Integer.class);
            method.invoke(obj,150);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
