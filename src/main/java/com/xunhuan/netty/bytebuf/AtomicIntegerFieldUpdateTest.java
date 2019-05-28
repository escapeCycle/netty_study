package com.xunhuan.netty.bytebuf;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 *
 * AtomicIntegerFieldUpdate 原子操作类test
 * 相似的类还有AtomicLongFieldUpdater和AtomicReferenceFieldUpdater
 * @author tianhuan
 * @date 2019-05-28 23:31
 **/
public class AtomicIntegerFieldUpdateTest {

    /**
     * 保证属性线程可见
     */
    private volatile int refCount = 1;

    /** 基于反射对相应属性进行操作 */
    private final static AtomicIntegerFieldUpdater<AtomicIntegerFieldUpdateTest> ref =
            AtomicIntegerFieldUpdater.newUpdater(AtomicIntegerFieldUpdateTest.class, "refCount");

    /**
     * 保证此方法原子更新,线程安全
     * @param incre
     * @return
     */
    private AtomicIntegerFieldUpdateTest retain(int incre) {
        for (;;) {
            int refCount = this.refCount;
            final int nextCount = refCount + incre;

            if(ref.compareAndSet(this,refCount,nextCount)){
                break;
            }
        }
        return this;
    }
}
