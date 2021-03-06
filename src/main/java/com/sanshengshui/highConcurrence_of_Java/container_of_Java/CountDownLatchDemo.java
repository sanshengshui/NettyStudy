package com.sanshengshui.highConcurrence_of_Java.container_of_Java;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @author 穆书伟
 * @description 这个工具通常用来控制线程等待，它可以让某一个线程等待直到倒计时结束，再开始执行
 */
public class CountDownLatchDemo implements Runnable{
    static final CountDownLatch end = new CountDownLatch(10);
    static  final CountDownLatchDemo demo = new CountDownLatchDemo();

    @Override
    public void run() {
        try{
            Thread.sleep(new Random().nextInt(10)*1000);
            System.out.println("check complete");
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            end.countDown();
        }
    }

    public static void main(String[] args) throws InterruptedException{
        ExecutorService exec = Executors.newFixedThreadPool(10);
        for (int i=0;i<10;i++){
            exec.submit(demo);
        }
        //等待检查
        end.await();
        //发射火箭
        System.out.println("Fire!");
        exec.shutdown();
    }
}
