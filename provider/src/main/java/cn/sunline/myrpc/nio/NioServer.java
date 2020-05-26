package cn.sunline.myrpc.nio;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

public class NioServer {
    static  final Logger logger= LoggerFactory.getLogger(NioServer.class);
    private final int port;
    private  final String host;

    public NioServer(String host,int port){
        this.port=port;
        this.host=host;
    }

    public void start() throws Exception{
        NioEventLoopGroup group=new NioEventLoopGroup();
        try{
            ServerBootstrap bootstrap=new ServerBootstrap();
            bootstrap.group(group,new NioEventLoopGroup())
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(host,port))
                    .childHandler(new ChannelInitializer<SocketChannel>(){
                        @Override
                        public void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new NioServerHandler());
                        }
                    });
            ChannelFuture future=bootstrap.bind().sync();//绑定的服务器 sync等待服务器关闭
            //System.out.println(NioServer.class.getName()+" started and listen on "+future.channel().localAddress());
            logger.info("started and listen on {}",future.channel().localAddress().toString());
            future.channel().closeFuture().sync();//关闭 channel和块 直到它被关闭
        }finally {
            group.shutdownGracefully().sync();//关闭group 释放资源
        }
    }
}
