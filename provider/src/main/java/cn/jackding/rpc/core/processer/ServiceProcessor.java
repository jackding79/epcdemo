package cn.jackding.rpc.core.processer;

import cn.jackding.rpc.core.ServiceImpl;
import cn.jackding.rpc.core.factory.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import java.util.Map;



/**
 *
 * @author dinghang
 */
public class ServiceProcessor {
    static Logger logger= LoggerFactory.getLogger(ServiceProcessor.class);


    public boolean registry(ApplicationContext applicationContext) throws BeansException {
            Map<String,Object> beans =applicationContext.getBeansWithAnnotation(ServiceImpl.class);
            Cache cache=Cache.getCache();
            for (Object service:beans.values()){
                Class<?> clazz=service.getClass();
                Class<?> []interfaces=clazz.getInterfaces();
                if(interfaces.length!=1){
                    logger.warn("实现了多个接口: {}",clazz);
                    break;
                }
                Class<?> interfaceClass=interfaces[0];
                if(!clazz.getAnnotation(ServiceImpl.class).ref().getClass().isInstance(interfaceClass)){
                    logger.warn("指定rpc服务类型不匹配 {}->{}",clazz,interfaceClass);
                    break;
                }
                cache.put(interfaceClass.getName(),clazz);
                logger.info("加载rpc服务:({}={})",interfaces,clazz);
            }

        return true;
    }
}
