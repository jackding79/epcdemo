package cn.sunline.myrpc.nio;

import cn.sunline.myrpc.common.RequestData;
import cn.sunline.myrpc.serializa.Decoder;
import cn.sunline.myrpc.serializa.Encoder;
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
    public void start(NioClientHandler handler) throws Exception{
        EventLoopGroup group=new NioEventLoopGroup();
        NioClientHandler nioClientHandler=handler;
        try{
            Bootstrap bootstrap=new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(host,port))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast("decoder",new Decoder()).
                                    addLast("encoder",new Encoder()).addLast(nioClientHandler);

                        }
                    });
            ChannelFuture future=bootstrap.connect().sync();//链接到远程 等待连接完成
            future.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully().sync();//关闭线程池和释放所有资源
        }
    }

    public static void main(String[] args) throws Exception {
        RequestData requestData=new RequestData();
        requestData.setMethodName("1111");
        requestData.setServiceName("dsds");
        NioClientHandler nioClientHandler=new NioClientHandler(requestData);
        new NioClient("127.0.0.1",9001).start(nioClientHandler);
        System.out.println(nioClientHandler.responseData());
    }

}
