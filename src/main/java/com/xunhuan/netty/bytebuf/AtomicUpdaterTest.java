package com.xunhuan.netty.bytebuf;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * 原子更新测试
 *
 * AtomicIntegerFieldUpdater要点总结：
 *
 * 更新器更新的必须是int类型变量，不能是其包装类型
 *
 * 更新器更新的必须是volatile类型变量，保证线程间的可见性，并禁止指令重排序
 *
 * 变量不能是static的，必须要是实例变量。因为Unsafe.objectFieldOfset()方法不支持静态变量（CAS操作本质上市通过对象示例的偏移量来直接进行赋值的）
 *
 * 更新器只能修改它可见范围内的变量，因为更新器是通过反射来得到这个变量，如果变量不可见就会报错
 *
 * @Auther: tianhuan
 * @Date: 2019/5/29 14:52
 * @Description:
 */
public class AtomicUpdaterTest {

    public static void main(String[] args) {
        /*Person person = new Person();

        for (int i = 0; i < 10; ++i){
            Thread thread = new Thread(() -> {
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(person.age++);
            });
            thread.start();
        }*/

        Person person = new Person();
        AtomicIntegerFieldUpdater<Person> newUpdater = AtomicIntegerFieldUpdater.newUpdater(Person.class, "age");
        for (int i = 0; i < 10; ++i) {
            Thread thread = new Thread(() -> {
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(newUpdater.getAndIncrement(person));
            });
            thread.start();
        }
    }
}

class Person {
    volatile int age = 1;
}
