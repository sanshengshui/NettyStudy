package com.sanshengshui.highConcurrence_of_Java.highConcurrence_of_Java.ThreadPool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorServiceDemo {
    public static void main(String[] args){
        ScheduledExecutorService ses= Executors.newScheduledThreadPool(10);
        //如果前面的任务没有完成，则调度也不会启动
        ses.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    System.out.println(System.currentTimeMillis() / 1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        },0,2, TimeUnit.SECONDS);
    }
}
