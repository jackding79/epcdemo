package cn.sunline.myrpc;

import cn.sunline.myrpc.config.RegistryConfig;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;


public class ZkClient {

    public static CuratorFramework createClient(RegistryConfig config){
        CuratorFramework client=null;
        RetryPolicy retryPolicy=new ExponentialBackoffRetry(1000,3);
        client=CuratorFrameworkFactory.builder().connectString(config.getHost()+":"+config.getPort())
                .connectionTimeoutMs(config.getConnTimeout())
                .sessionTimeoutMs(config.getSessionTimeOut())
                .canBeReadOnly(false)
                .retryPolicy(retryPolicy)
                .build();
        client.start();
        return  client;
    }
}
