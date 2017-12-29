package com.sanshengshui.highConcurrence_of_Java.container_of_Java;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 穆书伟
 * @description
 * 简单列举一下AtomicInteger的一些主要方法，对于其他原子类,操作也是非常类似的
 * public final int get()                   //取得当前值
 * public final void set(int newValue)      //设置当前值
 * public final int getAndSet(int newValue) //设置新值，并返回旧值
 * public final boolean compareAndSet(int expect,int u) //如果当前值为except,则设置为u
 * public final int getAndIncrement() //当前值加1,返回旧值
 * public final int getAndDecrement() //当前值减1,返回旧值
 * public final int incrementAndGet() //当前值加1,返回新值
 * public final int decrementAndGet() //当前值减1,返回新值
 * public final int addAndGet(int delta) //当前值增加delta,返回新值
 * @date 2017年12月29日 下午15:35
 */
public class AtomicIntegerDemo {
    static AtomicInteger i = new AtomicInteger();

    public static class AddThread implements Runnable{
        @Override
        public void run() {
            for (int k =0;k<10000;k++){
                i.incrementAndGet();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread[] ts = new Thread[10];
        for (int k =0;k<10;k++){
            ts[k] = new Thread(new AddThread());
        }
        for (int k =0;k<10;k++){ts[k].start();}
        for (int k =0;k<10;k++){ts[k].join();}
        System.out.println(i);
    }
}
