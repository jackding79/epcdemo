package cn.sunline.myrpc.executors;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoll {

    private static ThreadPoolExecutor threadPoolExecutor=null;

    public static ThreadPoolExecutor getThreadPoolExecutor(int coreSize, int maxSize, long keepAliveTime, BlockingQueue<Runnable> queue){
        if(threadPoolExecutor==null){
            synchronized (ThreadPoolExecutor.class){
                if(threadPoolExecutor==null){
                    threadPoolExecutor=new ThreadPoolExecutor(coreSize,maxSize,keepAliveTime, TimeUnit.MILLISECONDS
                                                       ,queue);
                }
            }
        }
    return threadPoolExecutor;
    }


}
