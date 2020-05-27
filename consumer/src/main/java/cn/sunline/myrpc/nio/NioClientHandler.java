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

public class NioClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
   // private final RequestData data;
    final static Logger logger= LoggerFactory.getLogger(NioClientHandler.class);
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        System.out.println("Client received: " + msg); // 记录接收到的消息
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("client 已经连接到server {}",ctx.channel().localAddress());
        ctx.writeAndFlush("ss".getBytes());
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 记录日志错误并关闭 channel

        cause.printStackTrace();
        System.out.println(cause.getMessage());
        ctx.close();
    }

   /* public NioClientHandler(RequestData data){
        this.data=data;
    }*/
}
