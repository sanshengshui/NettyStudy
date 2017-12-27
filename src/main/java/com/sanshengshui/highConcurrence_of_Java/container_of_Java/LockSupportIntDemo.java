package com.sanshengshui.highConcurrence_of_Java.container_of_Java;

import java.util.concurrent.locks.LockSupport;

/**
 * @author 穆书伟
 * @description LockSupport.pack()还能支持中断影响。但是和其他接受中断的
 * 函数很不一样，LockSupport.park()不会抛出InterruptedException异常，它只
 * 是会默默的返回
 * @date 2017年12月27日 下午19:45
 */
public class LockSupportIntDemo {
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
                if (Thread.interrupted()){
                    System.out.println(getName()+"被中断了");
                }
            }
            System.out.println(getName()+"执行结束");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        t1.start();
        Thread.sleep(100);
        t2.start();
        t1.interrupt();
        LockSupport.unpark(t2);
    }
}
