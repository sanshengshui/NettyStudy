package com.sanshengshui.highConcurrence_of_Java.highConcurrence_of_Java.ThreadPool;

public class NoVisibility {
    private  volatile static boolean ready;
    private   static int number;

    private static class ReaderThread extends Thread{
        @Override
        public void run(){
            while (!ready) {
                System.out.println(number);
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        new ReaderThread().start();
        Thread.sleep(1000);
        number = 42;
        ready = true;
        System.out.println("开始打印数据");
        Thread.sleep(10000);
    }
}
