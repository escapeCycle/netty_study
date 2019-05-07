package com.xunhuan.netty.decorator;

/**
 * @author tianhuan
 * @date 2019-02-20 14:06
 **/
public class Decorator implements Component {

    private Component component;

    public Decorator(Component component) {
        this.component = component;
    }

    @Override
    public void doSomething(){
        component.doSomething();
    }
}
