package cn.sunline.myrpc.factory;

import cn.sunline.myrpc.consumer.proxy.ServiceProxy;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;

public class ServiceFactory<T> implements FactoryBean {
    private Class<T> interfaceType;

    public ServiceFactory(Class<T> interfaceType){
        this.interfaceType=interfaceType;
    }


    @Override
    public T getObject() throws Exception {
        ServiceProxy handler=new ServiceProxy(interfaceType);
        return (T) Proxy.newProxyInstance(interfaceType.getClassLoader(),
                new Class[] {interfaceType},handler);

    }

    @Override
    public Class<T> getObjectType() {
        return interfaceType;
    }

    @Override
    public boolean isSingleton() {
        //单例
        return true;
    }
}
