package com.sanshengshui.highConcurrence_of_Java.container_of_Java;

import java.util.concurrent.locks.LockSupport;

/**
 * @author 穆书伟
 * @description LockSupport是一个非常方便实用的线程阻塞工具
 * 和Thread.suspend相比，它补足了由于resume()在前发生，导致线程无法继续执行的情况。和
 * Object.wait相比，它不需要先获得某个对象的锁，也不会抛出InterruptedException异常
 * @date 2017年12月27日 下午17:15
 */
public class LockSupportDemo {
    public static Object u = new Object();
    static ChangeObjectThread t1 = new ChangeObjectThread("t1");
    static ChangeObjectThread t2 = new ChangeObjectThread("t2");
    public static class ChangeObjectThread extends Thread{
        public ChangeObjectThread(String name){
            super.setName(name);
        }
        @Override
        public void run() {
            synchronized (u){
                System.out.println("in "+getName());
                LockSupport.park();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        t1.start();
        Thread.sleep(100);
        t2.start();
        LockSupport.unpark(t1);
        LockSupport.unpark(t2);
        t1.join();
        t2.join();
    }
}
