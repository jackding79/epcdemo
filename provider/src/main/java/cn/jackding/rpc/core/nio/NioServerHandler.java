package cn.jackding.rpc.core.nio;


import cn.jackding.rpc.core.common.RequestData;
import cn.jackding.rpc.core.common.ResponseData;
import cn.jackding.rpc.core.task.Executor;
import cn.jackding.rpc.core.config.HandleThreadPoolConfig;
import cn.jackding.rpc.core.factory.CustomThreadFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@ChannelHandler.Sharable
public class NioServerHandler extends ChannelInboundHandlerAdapter  {
    private ThreadPoolExecutor handlePool;
    private HandleThreadPoolConfig poolConfig=new HandleThreadPoolConfig();
    private Executor executor;

    public  NioServerHandler(){
        this.handlePool=new ThreadPoolExecutor(poolConfig.getCoreSize(),poolConfig.getMaxSize(),
                                            0L, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>(poolConfig.getQueueSize()),
                new CustomThreadFactory("dh"),new ThreadPoolExecutor.AbortPolicy());
        this.executor=new Executor();
    }

    final Logger logger= LoggerFactory.getLogger(NioServerHandler.class);
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RequestData in=(RequestData) msg;
        logger.info("server received: "+in.toString());
        System.out.println(ctx.channel().isActive());
        handlePool.submit(new Runnable() {
            @Override
            public void run() {
                ResponseData responseData=executor.execute(in);
                try {

                    ctx.writeAndFlush(responseData);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("管道读数据完毕");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        System.out.println(cause.getMessage());
        ctx.close();
    }



    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // TODO Auto-generated method stub
        super.channelActive(ctx);
        System.out.println("管道变得可用");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        System.out.println("管道变得不可用");

    }
}
