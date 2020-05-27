package cn.sunline.myrpc.nio;


import cn.sunline.myrpc.common.RequestData;
import cn.sunline.myrpc.common.ResponseData;
import cn.sunline.myrpc.config.HandleThreadPoolConfig;
import cn.sunline.myrpc.factory.CustomThreadFactory;
import cn.sunline.myrpc.task.Executor;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
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
