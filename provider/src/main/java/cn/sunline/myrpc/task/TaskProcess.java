package cn.sunline.myrpc.task;

import cn.sunline.myrpc.common.RequestData;
import cn.sunline.myrpc.factory.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;
@Deprecated
public class TaskProcess implements Runnable {
    private static final Logger logger= LoggerFactory.getLogger(TaskProcess.class);
    private Socket socket;
    public TaskProcess(Socket socket){
        this.socket=socket;
    }

    /**
     * 处理数据并写回
     */
    @Override
    public void run() {
        ObjectInputStream objectInputStream=null;
        try {
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            RequestData data=(RequestData) objectInputStream.readObject();
            Object result=invoke(data);
            //写回client
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(result);
            objectOutputStream.flush();
            objectInputStream.close();

        }catch (IOException e){
            logger.error("服务处理异常 {}",e);
        }catch (ClassNotFoundException e){
            logger.error("找不到类 {}",e.getMessage());
        }finally {
            if(objectInputStream!=null){
                try{
                  objectInputStream.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 反射调用
     * @param data
     * @return
     */
    private Object invoke(RequestData data){
        Cache cache=Cache.getCache();
        Object result=null;
        logger.info("开始处理数据 :{}",data);
        Object[] params=data.getArgs();

        Class parameterTypes[]=new Class[params.length];
        for (int i=0;i<params.length;i++){
            parameterTypes[i]=params[i].getClass();
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
