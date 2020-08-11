package cn.jackding.rpc.core.task;

import cn.jackding.rpc.core.common.RequestData;
import cn.jackding.rpc.core.common.ResponseData;
import cn.jackding.rpc.core.factory.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public class Executor  {
    private static final Logger logger= LoggerFactory.getLogger(Executor.class);

    public Executor(){
    }
    public ResponseData execute(RequestData msg) {
        logger.info("开始执行-》{}",msg);
        ResponseData responseData=new ResponseData();
        RequestData requestData=msg;
        Object result=null;
        try {
            result  = this.invoke(requestData);
        }catch (Exception e){
            logger.error(e.getMessage());
            responseData.setStatus("error");
            responseData.setMessage(e.getMessage());
        }
        responseData.setObject(result);
        responseData.setCls(result.getClass());
        return responseData;


    }
    /**
     * 反射调用
     * @param data
     * @return
     */
    private Object invoke(RequestData data){
        Cache cache=Cache.getCache();
        Object result=null;
        logger.info("开始处理数据 :{}->{}",data.getServiceName(),data.getMethodName());
        Object[] params=data.getArgs();
        Class parameterTypes[];
        if(params!=null) {
            parameterTypes= new Class[params.length];
            for (int i = 0; i < params.length; i++) {
                parameterTypes[i] = params[i].getClass();
            }
        }else{
            parameterTypes=null;
        }

        String serviceName=data.getServiceName();
        String methodName=data.getMethodName();
        Class<?> bean =cache.get(serviceName);

        try {
            Method method = bean.getMethod(methodName, parameterTypes);
            result= method.invoke(bean.newInstance(),params);
        }catch (Exception e){
            logger.error("执行方法出错 {}->{},{}",methodName,parameterTypes,e);
        }
        return  result;
    }
}
