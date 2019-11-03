package com.gphw.pattern.proxy.dynamicProxy.jdkProxy;

import com.gphw.pattern.proxy.dynamicProxy.Person;

public class Man implements Person {
    @Override
    public void findHouse() {
        System.out.println("三室两厅，一厨一卫");
    }

    @Override
    public void findBigHouse(Integer area) {
        System.out.println("五室两厅，一厨三卫，带花园....总面积："+area);
    }
}
