package cn.sunline.myrpc.cache;

import java.util.concurrent.ConcurrentHashMap;

public class LocalCache extends ConcurrentHashMap<String,String> {
    private LocalCache(){};
    private static LocalCache localCache=null;

    public static LocalCache getLocalCache(){
        if(localCache==null){
            synchronized (LocalCache.class){
                if(localCache==null){
                    localCache=new LocalCache();
                }
            }
        }
        return localCache;
    }

}
