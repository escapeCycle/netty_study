package com.xunhuan.netty.decorator;

/**
 * @author tianhuan
 * @date 2019-02-20 14:05
 **/
public class ConcreteComponent implements Component {
    @Override
    public void doSomething() {
        System.out.println("功能A");
    }
}
