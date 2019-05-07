package com.xunhuan.netty.decorator;

/**
 * @author tianhuan
 * @date 2019-02-20 14:12
 **/
public class Client {
    public static void main(String[] args) {
        Component component = new ConcreteDecorator2(new ConcreteDecorator1(new ConcreteComponent()));
        component.doSomething();
    }
}
