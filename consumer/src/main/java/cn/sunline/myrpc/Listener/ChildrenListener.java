package cn.sunline.myrpc.Listener;

import cn.sunline.myrpc.cache.LocalCache;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ChildrenListener implements TreeCacheListener {
    LocalCache localCache=LocalCache.getLocalCache();
    final static Logger logger= LoggerFactory.getLogger(ChildrenListener.class);
    @Override
    public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
        switch(event.getType()) {
            case NODE_ADDED:
               logger.info("发生服务节点添加 {}",event.getData().getPath());
                if(!event.getData().getPath().equals("/rpc")){//不包含自身根节点
                    addCache(event.getData().getPath(),client);
                }
                break;
            case NODE_UPDATED:
                System.out.println("tree:发生节点更新"); break;
            case NODE_REMOVED:
                logger.info("发生服务节点删除 {}",event.getData().getPath());
                if(event.getData().getPath().equals("/rpc")){
                    // TODO 全部删除
                }else{
                    // TODO 删除服务节点
                }
                break;
            case CONNECTION_SUSPENDED:
                break;
            case CONNECTION_RECONNECTED:
                break;
            case CONNECTION_LOST:
                break;
            case INITIALIZED:
                System.out.println("初始化的操作"); break;
            default:
                break;
        }
    }

    public void addCache(String path,CuratorFramework client) throws Exception{
        List<String> list= client.getChildren().forPath(path);
        for (String s:list
        ) {
            List<String> currips=localCache.get(path);
            if(currips==null){
                currips=new ArrayList<>();
            }
            currips.add(s);
            localCache.put(path,currips);
            logger.info("添加服务:{}->{}",path,s);
        }
        }
    public void updateCache(String path,CuratorFramework client) throws Exception{
        // TODO 无需要

    }
    public void deleteCache(String path,CuratorFramework client) throws Exception{
        if(path.equals("/rpc")){//删除整个RPC
            localCache.clear();
            return;
        }
        if(client.getChildren().forPath(path).size()==0){//删除服务的其它节点
            if(localCache.get(path.split("/")[0]+path.split("/")[1])!=null){
                List<String> s=localCache.get(path.split("/")[0]+path.split("/")[1]);
                s.remove(path.split("/")[2]);
                localCache.put(path.split("/")[0]+path.split("/")[1],s);
            }
        }else{//删除一个服务的所有节点  发生服务节点删除 /rpc/cn.sunline.myrpc.test.TestService/127.0.0.1:9002
            //zk删除节点肯定会先删除子节点 到第二层 第三层已经删除完了
            localCache.remove(path.split("/")[0]+path.split("/")[1]);
        }

    }

    }

