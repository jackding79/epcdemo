package cn.sunline.myrpc.factory;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomThreadFactory implements ThreadFactory {
    private static final AtomicInteger poolNumber=new AtomicInteger(1);
    private final ThreadGroup threadGroup;
    private final AtomicInteger threadNumber;
    private final String namePrefix;

    public CustomThreadFactory(String name){
        this.threadNumber=new AtomicInteger(1);
        this.namePrefix="Handle-Pool-"+poolNumber.getAndIncrement();
        this.threadGroup=new ThreadGroup(this.namePrefix+"("+name+")");

    }


    @Override
    public Thread newThread(Runnable r) {
        return this._newThread(r);
    }
    private Thread _newThread(Runnable r){
        Thread t=new Thread(this.threadGroup,r,threadGroup.getName()+"-thread-"+threadNumber.getAndIncrement(),0){
            @Override
            public void run() {
                super.run();
            }
        };
        if(t.isDaemon())
            t.setDaemon(false);
        if(t.getPriority()!=Thread.NORM_PRIORITY)//设置线程优先级
            t.setPriority(Thread.NORM_PRIORITY);
        return t;
    }
}
