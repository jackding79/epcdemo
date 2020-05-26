package cn.sunline.myrpc.Listener;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;

public class ChildrenListener implements TreeCacheListener {
    @Override
    public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
        switch(event.getType()) {
            case NODE_ADDED:
                System.out.println("tree:发生节点添加" + event.getData().toString() );
                break;
            case NODE_UPDATED:
                System.out.println("tree:发生节点更新"); break;
            case NODE_REMOVED:
                System.out.println("tree:发生节点删除"); break;
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

}
