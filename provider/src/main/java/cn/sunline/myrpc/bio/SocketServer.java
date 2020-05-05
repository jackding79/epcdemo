package cn.sunline.myrpc.bio;

import cn.sunline.myrpc.executors.ThreadPoll;
import cn.sunline.myrpc.task.TaskProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class SocketServer {
    static final Logger logger= LoggerFactory.getLogger(SocketServer.class);

    public void startBIOServer(int port){
        LinkedBlockingQueue queue=new LinkedBlockingQueue();
        try
        {
            ServerSocket serverSocket = new ServerSocket(port);
            logger.info("监听服务已在{}端口启动",port);
            while (true){
                Socket socket= serverSocket.accept();
                ThreadPoll.getThreadPoolExecutor(10,20,1000,queue)
                .execute(new TaskProcess(socket));
            }
        }catch (IOException e){
                e.printStackTrace();
        }
    }
}

