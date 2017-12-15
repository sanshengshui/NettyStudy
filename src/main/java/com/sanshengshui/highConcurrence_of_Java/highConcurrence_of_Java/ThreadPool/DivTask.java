package com.sanshengshui.highConcurrence_of_Java.highConcurrence_of_Java.ThreadPool;

import java.util.concurrent.*;

public class DivTask {
    public static class divtask implements Runnable {
        int a, b;

        public divtask(int a, int b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public void run() {
            double re = a / b;
            System.out.println(re);
        }
    }

    public static void main(String[] args){
//        ThreadPoolExecutor pools = new ThreadPoolExecutor(0,Integer.MAX_VALUE,0, TimeUnit.MILLISECONDS,new SynchronousQueue<Runnable>());
//        for (int i =0;i<5;i++){
//            pools.execute(new divtask(100,i));
//        }
        ThreadPoolExecutor pools = new TraceThreadPoolExecutor(0,Integer.MAX_VALUE,0L,TimeUnit.SECONDS,new SynchronousQueue<Runnable>());
        for (int i =0;i<5;i++){
            pools.execute(new divtask(100,i));
        }
    }

    public static class TraceThreadPoolExecutor extends ThreadPoolExecutor{
        public TraceThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue){
            super(corePoolSize,maximumPoolSize,keepAliveTime,unit,workQueue);
        }

        @Override
        public void execute(Runnable task){
            super.execute(wrap(task,clientTrace(),Thread.currentThread().getName()));
        }

        @Override
        public Future<?> submit(Runnable task) {
            return super.submit(wrap(task,clientTrace(),Thread.currentThread().getName()));
        }

        private Exception clientTrace(){
            return new Exception("Client stack trace");
        }

        private Runnable wrap(final Runnable task,final Exception clientStack,String clientThreadName){
            return new Runnable() {
                @Override
                public void run() {
                    try {
                        task.run();
                    }catch (Exception e){
                        clientStack.printStackTrace();
                        throw e;
                    }
                }
            };
        }
    }
}
