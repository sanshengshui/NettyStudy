package com.sanshengshui.highConcurrence_of_Java.container_of_Java;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author 穆书伟
 *
 */
public class CyclicBarrierDemo {
    public static class Soldier implements Runnable{
        private String soldier;

        private final CyclicBarrier cyclic;

        Soldier(CyclicBarrier cyclic,String soldierName){
            this.cyclic = cyclic;
            this.soldier = soldierName;
        }

        @Override
        public void run() {
            try{
                //等待所有士兵到齐
                cyclic.await();
                doWork();
                //等待所有士兵完成工作
                cyclic.await();
            } catch (InterruptedException e){
                e.printStackTrace();
            } catch (BrokenBarrierException e){
                e.printStackTrace();
            }
        }

        void doWork(){
            try{
                Thread.sleep(Math.abs(new Random().nextInt()%10000));
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            System.out.println(soldier +":任务完成");
        }
    }

    public static class BarrierRun implements Runnable{
        boolean flag;
        int N;
        public BarrierRun(boolean flag,int N){
            this.flag = flag;
            this.N = N;
        }
        @Override
        public void run() {
            if (flag){
                System.out.println("司令:[士兵"+ N + "个,任务完成!]");
            }else {
                System.out.println("司令:[士兵"+  N  +"个,集合完毕!]");
                flag = true;
            }
        }
    }

    public static void main(String args[])throws  InterruptedException{
        final  int N = 10;
        Thread[] allSoldier = new Thread[N];
        boolean flag = false;

    }
}
