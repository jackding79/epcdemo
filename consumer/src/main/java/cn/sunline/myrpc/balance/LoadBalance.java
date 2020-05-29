package cn.sunline.myrpc.balance;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class LoadBalance {

    public static Integer getIndex(String key,int size){
            selectMap.put(key,size);
            return selectMap.get(key);
    }

    static class selectMap{
       static private Map<String,Integer> map=new HashMap();

        private static void put(String key,int size){
            if(map.containsKey(key)) {
                Integer currindex=map.get(key);
                if(currindex>=size)
                    currindex=0;
                map.put(key, currindex++);
            }else{
                int index=key.hashCode()%size;
                if(index>=size)
                    index=0;
                map.put(key,index);
            }
        }

        private static Integer get(String key){
            return map.get(key);
        }
    }
}
