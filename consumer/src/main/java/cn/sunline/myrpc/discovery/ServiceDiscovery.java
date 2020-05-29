package cn.sunline.myrpc.discovery;

import cn.sunline.myrpc.cache.LocalCache;
import cn.sunline.myrpc.config.ConsumerConfig;
import cn.sunline.myrpc.zookeeper.ZkClient;
import org.apache.curator.framework.CuratorFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ServiceDiscovery {
    static final Logger logger= LoggerFactory.getLogger(ServiceDiscovery.class);
    static LocalCache localCache=LocalCache.getLocalCache();
    @Autowired
    ConsumerConfig consumerConfig;

    private CuratorFramework zkClient;

    @PostConstruct
    public void init(){
        connectServer();
    }

    //连接zookeeper
    private CuratorFramework connectServer() {
        CuratorFramework client = ZkClient.createClient(consumerConfig);
        return client;
    }

}
