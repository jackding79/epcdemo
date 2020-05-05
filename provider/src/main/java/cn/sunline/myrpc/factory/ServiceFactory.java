package cn.sunline.myrpc.factory;

import cn.sunline.myrpc.provider.myinterface.Service;
import cn.sunline.myrpc.proxy.ServiceProxy;
import com.sun.deploy.net.proxy.ProxyHandler;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 通过factoryBean创建代理对象
 * @author dinghang
 * @param <T>
 */
public class ServiceFactory<T> implements FactoryBean<T> {
    private static ConcurrentHashMap<String,Object> map=null;


    public static ConcurrentHashMap<String,Object> getServiceRegistreMap(){
        if(map==null){
            synchronized (Object.class){
                if(map==null){
                    map=new ConcurrentHashMap<>();
                }
            }
        }
        return map;
    }


    private Class<T> interfaceType;

    public Class<T> getInterfaceType() {
        return interfaceType;
    }

    public void setInterfaceType(Class<T> interfaceType) {
        this.interfaceType = interfaceType;
    }


    @Override
    public T getObject() throws Exception {
        ServiceProxy serviceProxy=new ServiceProxy(interfaceType);
        return (T)serviceProxy.newProxy();

    }

    @Override
    public Class<?> getObjectType() {
        return interfaceType;
    }

    @Override
    public boolean isSingleton() {
        //单例
        return true;
    }

    @Override
    public String toString() {
        return "ServiceFactory{" +
                "interfaceType=" + interfaceType +
                '}';
    }
}
