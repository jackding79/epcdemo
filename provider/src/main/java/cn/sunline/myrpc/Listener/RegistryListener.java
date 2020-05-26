package cn.sunline.myrpc.Listener;

import cn.sunline.myrpc.ZkUtil;
import cn.sunline.myrpc.processer.ServiceProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class RegistryListener implements ApplicationListener , ApplicationContextAware {
    static  final  Logger logger= LoggerFactory.getLogger(RegistryListener.class);
    private Class<?> initEventClass= ContextRefreshedEvent.class;
    private ApplicationContext applicationContext;
    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if(initEventClass.isInstance(applicationEvent)) {
           logger.info("启动服务注册");
           ServiceProcessor serviceProcessor=new ServiceProcessor();
           if(serviceProcessor.registry(applicationContext)) {
               new ZkUtil().init(applicationContext);
           }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }
}
