package cn.sunline.myrpc.discovery;

import cn.sunline.myrpc.cache.LocalCache;
import cn.sunline.myrpc.zookeeper.ZkClient;
import org.apache.curator.framework.CuratorFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class ServiceDiscovery {
    static final Logger logger= LoggerFactory.getLogger(ServiceDiscovery.class);
    static LocalCache localCache=LocalCache.getLocalCache();
    @Autowired
    cn.sunline.myrpc.config.ConsumerConfig consumerConfig;

    private CuratorFramework zkClient;

    @PostConstruct
    public void init(){
        zkClient=connectServer();
        if(zkClient!=null){
           // TODO:监听节点变化
            try{
               List<String> list= zkClient.getChildren().forPath("/rpc");
                for (String s:list
                     ) {
                    logger.info("发现服务:{}",s);
                    //寻找服务的子节点 ip
                    List<String> ips=zkClient.getChildren().forPath("/rpc"+"/"+s);
                    for (String ip:ips){
                        localCache.put(s,ip);
                    }
                }
            }catch (Exception e){

            }
        }
    }

    //连接zookeeper
    private CuratorFramework connectServer() {
        CuratorFramework client = ZkClient.createClient(consumerConfig);
        return client;
    }

}
