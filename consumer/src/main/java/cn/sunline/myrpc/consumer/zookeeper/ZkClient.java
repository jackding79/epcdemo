package cn.sunline.myrpc.consumer.zookeeper;

import cn.sunline.myrpc.consumer.Listener.ChildrenListener;
import cn.sunline.myrpc.consumer.config.consumerConfig;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ZkClient {
    final static Logger logger= LoggerFactory.getLogger(ZkClient.class);
    public static  CuratorFramework client=null;

    public static CuratorFramework createClient(consumerConfig config){
        if(client!=null){
            return client;
        }
        RetryPolicy retryPolicy=new ExponentialBackoffRetry(1000,3);
        client= CuratorFrameworkFactory.builder().connectString(config.getHost()+":"+config.getPort())
                .connectionTimeoutMs(config.getConnTimeout())
                .sessionTimeoutMs(config.getSessionTimeOut())
                .canBeReadOnly(false)
                .retryPolicy(retryPolicy)
                .build();
        client.start();
        TreeCache treeCache = new TreeCache(client,config.getPath());
        try {
            treeCache.start();
            treeCache.getListenable().addListener(new ChildrenListener());
        }catch (Exception e){
            logger.error("监听出错");
        }
        return  client;
    }
}
