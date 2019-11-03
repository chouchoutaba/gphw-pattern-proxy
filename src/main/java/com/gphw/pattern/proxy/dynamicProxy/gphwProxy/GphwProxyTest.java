package com.gphw.pattern.proxy.dynamicProxy.gphwProxy;

import com.gphw.pattern.proxy.dynamicProxy.jdkProxy.HouseAgent;
import com.gphw.pattern.proxy.dynamicProxy.jdkProxy.Man;

import java.lang.reflect.Method;

public class GphwProxyTest {
    public static void main(String[] args) {
        Object obj = new GphwHouseAgent().getInstance(new Man());
        try {
            Method method =obj.getClass().getMethod("findBigHouse",Integer.class);
            method.invoke(obj,150);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
