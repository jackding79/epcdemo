package cn.sunline.myrpc.nio;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

public class NioClient {
    private final String host;
    private final int port;

    public NioClient(String host,int port){
        this.host=host;
        this.port=port;
    }
    public void start() throws Exception{
        EventLoopGroup group=new NioEventLoopGroup();
        try{
            Bootstrap bootstrap=new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(host,port))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NioClientHandler());
                        }
                    });
            ChannelFuture future=bootstrap.connect().sync();//链接到远程 等待连接完成
            future.channel().closeFuture().sync();//阻塞到远程 等待连接完成

        }finally {
            group.shutdownGracefully().sync();//关闭线程池和释放所有资源
        }
    }
}
