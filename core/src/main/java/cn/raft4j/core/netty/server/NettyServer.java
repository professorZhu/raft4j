package cn.raft4j.core.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;

/**
 * @copyright Copyright 2017-2022 JD.COM All Right Reserved
 * @author: zhuqiang3
 * @version:
 * @Date: 2021-08-30 17:43
 * @Modified By:
 * @Description: TODO
 * @see
 */
public class NettyServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(NettyServer.class);

    /**
     *@Fields DELIMITER : 自定义分隔符，服务端和客户端要保持一致
     */
    public static final String DELIMITER = "@@";

    /**
     * @Fields boss : boss 线程组用于处理连接工作, 默认是系统CPU个数的两倍，也可以根据实际情况指定
     */
    private EventLoopGroup boss = new NioEventLoopGroup();

    /**
     * @Fields work : work 线程组用于数据处理, 默认是系统CPU个数的两倍，也可以根据实际情况指定
     */
    private EventLoopGroup work = new NioEventLoopGroup();

    /**
     * @Fields port : 监听端口
     */
    private Integer port = 8888;

    public NettyServer(){

    }
    public NettyServer(Integer port){
        this.port = port;
    }


    /**
     * @throws InterruptedException
     * @Description: 启动Netty Server
     * @Author:杨攀
     * @Since: 2019年9月12日下午4:21:35
     */
    public void start() throws InterruptedException {
        try{
            ServerBootstrap bootstrap = new ServerBootstrap();

            bootstrap.group(boss, work)
                    // 指定Channel
                    .channel(NioServerSocketChannel.class)
                    // 使用指定的端口设置套接字地址
                    .localAddress(new InetSocketAddress(port))

                    // 服务端可连接队列数,对应TCP/IP协议listen函数中backlog参数
                    .option(ChannelOption.SO_BACKLOG, 1024)

                    // 设置TCP长连接,一般如果两个小时内没有数据的通信时,TCP会自动发送一个活动探测数据报文
                    .childOption(ChannelOption.SO_KEEPALIVE, true)

                    // 将小的数据包包装成更大的帧进行传送，提高网络的负载,即TCP延迟传输
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childHandler(new NettyServerHandlerInitializer());

            ChannelFuture future = bootstrap.bind();
            System.out.println("启动 Netty Server...");
            future.sync();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @PreDestroy
    public void destory() throws InterruptedException {
        boss.shutdownGracefully().sync();
        work.shutdownGracefully().sync();
        LOGGER.info("关闭Netty...");
    }

}
