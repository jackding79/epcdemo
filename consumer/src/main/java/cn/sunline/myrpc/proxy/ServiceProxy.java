package cn.sunline.myrpc.proxy;



import cn.sunline.myrpc.balance.LoadBalance;
import cn.sunline.myrpc.cache.LocalCache;
import cn.sunline.myrpc.common.RequestData;
import cn.sunline.myrpc.common.ResponseData;
import cn.sunline.myrpc.nio.NioClient;
import cn.sunline.myrpc.nio.NioClientHandler;
import cn.sunline.myrpc.test.User;
import cn.sunline.myrpc.zookeeper.ZkClient;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.curator.framework.CuratorFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
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
            logger.error("zkclient 未初始化完成");
            throw new Exception("zkclient 未初始化完成");
        }
        String ip="";
        if(client!=null&&client.checkExists().forPath("/rpc"+"/"+path)==null||client==null){
            logger.info(path+"服务未发现");
            //走本地缓存
            LocalCache cache=LocalCache.getLocalCache();
            List<String> ips =cache.get(path);
            if(ips==null||ips.isEmpty()){
                logger.error("服务调用失败,未找到可用服务 {}",path);
            }
            Integer index= LoadBalance.getIndex("/rpc"+"/"+path,ips.size());
            ip=ips.get(index);
        }else{
            List<String> ips = client.getChildren().forPath("/rpc"+"/"+path);
            Integer index= LoadBalance.getIndex("/rpc"+"/"+path,ips.size());
            ip=ips.get(index);
        }

        RequestData requestData=new RequestData();
        requestData.setArgs(args);
        requestData.setMethodName(method.getName());
        requestData.setServiceName(path);
        NioClientHandler handler=new NioClientHandler(requestData);
        logger.info("发起服务调用 {}->{}[{}]",path,method,ip);
        new NioClient(ip.split(":")[0],Integer.parseInt(ip.split(":")[1])).start(handler);
        ResponseData responseData= handler.responseData();
        if(responseData.getObject() instanceof JSONArray){
            return JSONArray.parseObject(((JSONArray)responseData.getObject()).toJSONString(),responseData.getCls());
        }
        if(responseData.getObject() instanceof String){
            return (String)responseData.getObject();
        }
        if(responseData.getObject() instanceof Integer){
            return (Integer)responseData.getObject();
        }
        return JSONObject.parseObject(((JSONObject)responseData.getObject()).toJSONString(),responseData.getCls());
    }

}
