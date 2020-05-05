package cn.sunline.myrpc.proxy;

import cn.sunline.myrpc.entity.User;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ServiceProxy<T> implements InvocationHandler {
    private Class<T> interfaceType;
    public ServiceProxy(Class<T> interfaceType){
        this.interfaceType=interfaceType;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Field field= proxy.getClass().getSuperclass().getDeclaredField("h");
        return new User();
    }
    //生成代理类
    public Object newProxy(){
        Object obj= Proxy.newProxyInstance(interfaceType.getClass().getClassLoader(),interfaceType.getClass().getInterfaces(),
                this);
        return  obj;
    }

    @Override
    public String toString() {
        return "ServiceProxy{" +
                "interfaceType=" + interfaceType +
                '}';
    }
}
