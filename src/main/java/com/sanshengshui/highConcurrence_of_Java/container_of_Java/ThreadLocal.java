package com.sanshengshui.highConcurrence_of_Java.container_of_Java;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadLocal {
    /**
     *   一种可行的方案是在sdf.parse()前后加锁，这也是我们一般的处理思路。这里我们不这么做，我们
     *   使用ThreadLocal为每一个线程都产生一个SimpleDateformat对象实例:
     */
    static java.lang.ThreadLocal<SimpleDateFormat> t1 = new java.lang.ThreadLocal<SimpleDateFormat>();
    /**
     *   old 在多线程中使用SimpleDateFormat来解析字符串类型的日期。如果你执行上述代码，一般来说，
     * 你很可能得到一些异常
     *   出现这些问题的原因，是SimpleDateFormat.parse()方法并不是线程安全的。因此，在线程池中共享
     *   这些对象必须导致错误。
     */
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static class ParseDate implements Runnable{

        int i =0;

        public ParseDate(int i){
            this.i = i;
        }

        @Override
        public void run() {
            try {
                if (t1.get() == null){
                    t1.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
                }
                Date t = t1.get().parse("2017-12-17 10:15:" + i % 60);
                System.out.println(i+":"+ t);
            }catch (ParseException e){
                e.printStackTrace();
            }
        }


//        @Override
//        public void run() {
//            try {
//                Date t = sdf.parse("2017-12-17 10:15:" + i % 60);
//                System.out.println(i + ":" + t);
//            }catch (ParseException e){
//                e.printStackTrace();
//            }
//        }


    }

    public static void main(String[] args){
        ExecutorService es = Executors.newFixedThreadPool(10);
        for (int i =0;i<1000;i++){
            es.execute(new ParseDate(i));
        }
    }
}
