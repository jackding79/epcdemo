package cn.sunline.myrpc.consumer.proxy;



import cn.sunline.myrpc.common.RequestData;
import cn.sunline.myrpc.consumer.cache.LocalCache;
import cn.sunline.myrpc.consumer.zookeeper.ZkClient;
import org.apache.curator.framework.CuratorFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.List;


public class ServiceProxy<T> implements InvocationHandler {
    private Logger logger= LoggerFactory.getLogger(ServiceProxy.class);

    private Class<T> interfaceType;
    public ServiceProxy(Class<T> interfaceType){
        this.interfaceType=interfaceType;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String path=method.getDeclaringClass().getName();
        CuratorFramework client= ZkClient.client;
        if(client==null){
            Thread.sleep(1000);
        }
        String ip="";
        if(client!=null&&client.checkExists().forPath("/rpc"+"/"+path)==null||client==null){
            logger.info(path+"服务未发现");
            //走本地缓存
            LocalCache cache=LocalCache.getLocalCache();
            ip =cache.get(path);
            if(ip==null||ip.isEmpty()){
                logger.error("服务调用失败,未找到可用服务 {}",path);
            }
        }else{
            List<String> ips = client.getChildren().forPath("/rpc"+"/"+path);
            //选用第一个地址  可以添加负载均衡策略
            ip=ips.get(0);
        }
        String [] address=ip.split(":");
        Socket socket=new Socket(address[0],Integer.parseInt(address[1]));
        RequestData requestData=new RequestData();
        requestData.setArgs(args);
        requestData.setMethodName(method.getName());
        requestData.setServiceName(path);
        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
        outputStream.writeObject(requestData);
        outputStream.flush();
        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
        Object result = inputStream.readObject();
        inputStream.close();
        outputStream.close();
        return result;
    }

}
