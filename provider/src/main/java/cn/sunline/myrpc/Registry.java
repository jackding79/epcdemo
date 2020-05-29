package cn.sunline.myrpc;

import cn.sunline.myrpc.config.RegistryConfig;
import cn.sunline.myrpc.factory.Cache;
import cn.sunline.myrpc.nio.NioServer;
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
        try {
            new NioServer(registryConfig.getServerHost(), registryConfig.getServerPort()).start();
        }catch (Exception e){
            logger.error("netty 启动失败 {}",e.getMessage());
            System.exit(-1);

        }


    }

    private void registryToZoo(){
        String ip=registryConfig.getServerHost()+":"+registryConfig.getServerPort();

        String rootPath=registryConfig.getPath();
        if(rootPath==null||rootPath.isEmpty()){
            rootPath="/rpc";
        }
        try {
            if(client.checkExists().forPath(rootPath)==null) {
                client.create().withMode(CreateMode.PERSISTENT).withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE);
            }
            Cache cache=Cache.getCache();
            for (Map.Entry<String,Class<?>> entry:cache.entrySet()
                ) {
                String path=rootPath+"/"
                        +entry.getKey();
                client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path+"/"+ip);
                logger.info("创建zookeeper服务节点 {}", path+"/"+ip);
            }
        }catch (Exception e){

            logger.error("注册失败 {}",e);
        }
    }

}
