package com.sanshengshui.highConcurrence_of_Java.highConcurrence_of_Java.ThreadPool;

public class BadSuspend {
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
                System.out.println("in "+ getName());
                Thread.currentThread().suspend();
            }
        }
    }
    public static void main(String[] args) throws InterruptedException{
//        t1.start();
//        Thread.sleep(100);
//        t1.resume();
//        t1.join();
//        t2.start();
//        t2.resume();
//        t2.join();



    }
}
