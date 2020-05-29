package cn.sunline.myrpc.nio;

import cn.sunline.myrpc.common.RequestData;
import cn.sunline.myrpc.common.ResponseData;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class NioClientHandler extends SimpleChannelInboundHandler<ResponseData> {
    private final RequestData data;
    private  ResponseData  responseData;
    private CountDownLatch cdl;
    final static Logger logger= LoggerFactory.getLogger(NioClientHandler.class);



    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ResponseData msg) throws Exception {
        responseData=msg;
        cdl.countDown();
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("client 已经连接到server {}",ctx.channel().localAddress());
        //开启客户端后 直接发送数据请求
        ctx.writeAndFlush(data);
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 记录日志错误并关闭 channel

        cause.printStackTrace();
        logger.error("netty client发生异常 {}", cause.getMessage());
        ctx.close();
    }

    public NioClientHandler(RequestData data){
        this.data=data;
        this.cdl=new CountDownLatch(1);

    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        System.out.println("--------------------------------------");
        logger.info("客户端管道注册完毕 {}",ctx.channel().toString());
    }
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
        logger.info("客户端管道注销");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // TODO Auto-generated method stub
        super.channelInactive(ctx);
        logger.info("客户端管道不可用 {}",ctx.channel().toString());

        System.out.println("");
    }
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
        System.out.println("客户端管道读取数据完毕,准备关闭管道");
        ctx.close();
    }

    public ResponseData responseData(){
            while (responseData==null){
                try {
                    cdl.await();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            return this.responseData;
    }

}
