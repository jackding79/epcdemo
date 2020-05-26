package cn.sunline.myrpc;

import cn.sunline.myrpc.config.RegistryConfig;
import org.apache.curator.framework.CuratorFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

public class ZkUtil  {
    private static final Logger logger= LoggerFactory.getLogger(ZkUtil.class);
    private ApplicationContext context;

    public void init(ApplicationContext applicationContext){
            this.context=applicationContext;
            RegistryConfig conf=context.getBean(RegistryConfig.class);
            if(conf==null){
                logger.error("注册中心配置为Null,不开启服务注册",conf);
                return ;
            }
        CuratorFramework client= ZkClient.createClient(conf);
        new Registry().registry(client,conf);
    };




}
