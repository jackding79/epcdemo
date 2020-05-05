package cn.sunline.myrpc.provider.zookeeper;

import cn.sunline.myrpc.bio.SocketServer;
import cn.sunline.myrpc.factory.Cache;
import cn.sunline.myrpc.provider.config.RegistryConfig;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class Registry {
    static final Logger logger= LoggerFactory.getLogger(Registry.class);
    private  CuratorFramework client;
    private RegistryConfig registryConfig;
    public  void  registry(CuratorFramework client, RegistryConfig registryConfig){
        this.client=client;
        this.registryConfig=registryConfig;
        registryToZoo();
        //开启监听
        if(!registryConfig.getIsNio()){
            new SocketServer().startBIOServer(registryConfig.getRegistryPort());
        }
    }

    private void registryToZoo(){
        String ip=registryConfig.getRegistryHost()+":"+registryConfig.getRegistryPort();
        String rootPath=registryConfig.getPath();
        if(rootPath==null||rootPath.isEmpty()){
            rootPath="/rpc";
        }
        try {
            if(client.checkExists().forPath(rootPath)==null) {
                client.create().withMode(CreateMode.PERSISTENT).withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE).
                        forPath(rootPath);
            }
            Cache cache=Cache.getCache();
            for (Map.Entry<String,Class<?>> entry:cache.entrySet()
                 ) {
                String path=rootPath+"/"
                        +entry.getKey()+"/"+ip;
                client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path);
                logger.info("创建zookeeper数据节点 ({} => {})", path);
            }
        }catch (Exception e){
            logger.error("注册失败 {}",e);
        }
    }

}
