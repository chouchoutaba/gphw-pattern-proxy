package com.gphw.pattern.proxy.dynamicProxy.gphwProxy;

import com.gphw.pattern.proxy.dynamicProxy.Person;

import java.lang.reflect.*;

public class $Proxy0 implements com.gphw.pattern.proxy.dynamicProxy.Person {
    GphwInvocationHandler h;

    public $Proxy0(GphwInvocationHandler h) {
        this.h = h;
    }

    public void findBigHouse(java.lang.Integer integer) {
        try {
            Method m = com.gphw.pattern.proxy.dynamicProxy.Person.class.getMethod("findBigHouse", new Class[]{java.lang.Integer.class});
            this.h.invoke(this, m, new Object[]{integer});
        } catch (Error _ex) {
        } catch (Throwable e) {
            throw new UndeclaredThrowableException(e);
        }
    }

    public void findHouse() {
        try {
            Method m = com.gphw.pattern.proxy.dynamicProxy.Person.class.getMethod("findHouse", new Class[]{});
            this.h.invoke(this, m, new Object[]{});
        } catch (Error _ex) {
        } catch (Throwable e) {
            throw new UndeclaredThrowableException(e);
        }
    }
}
