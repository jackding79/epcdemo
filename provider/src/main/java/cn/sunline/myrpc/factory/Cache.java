package cn.sunline.myrpc.factory;

import java.util.HashMap;

public class Cache extends HashMap<String ,Class<?>> {
    private static Cache cache;
    private Cache(){};
    public static Cache getCache(){
        if(cache==null){
            synchronized (Cache.class){
                if(cache==null){
                    cache=new Cache();
                }
            }
        }
        return cache;
    }


}
