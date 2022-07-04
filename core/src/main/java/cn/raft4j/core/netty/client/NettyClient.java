package cn.raft4j.core.netty.client;


import cn.raft4j.core.Note;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * @copyright Copyright 2017-2022 JD.COM All Right Reserved
 * @author: zhuqiang3
 * @version:
 * @Date: 2021-08-30 17:25
 * @Modified By:
 * @Description: TODO
 * @see
 */
public class NettyClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyClient.class);

    private EventLoopGroup group = new NioEventLoopGroup();

    private NettyClientService nettyClientService;
    /**
     *@Fields DELIMITER : 自定义分隔符，服务端和客户端要保持一致
     */
    public static final String DELIMITER = "@@";

    /**
     * @Fields hostIp : 服务端ip
     */
    private String hostIp = "127.0.0.1";

    /**
     * @Fields port : 服务端端口
     */
    private int port= 8888;

    /**
     * @Fields socketChannel : 通道
     */
    private SocketChannel socketChannel;

    public NettyClient(NettyClientService nettyClientService){
        nettyClientService.setNettyClient(this);
        this.nettyClientService = nettyClientService;
    }
    /**
     * 追加方法
     */
    public NettyClient(NettyClientService nettyClientService, Note note){
        nettyClientService.setNettyClient(this);
        this.hostIp = note.getIp();
        this.port = note.getPort();
        this.nettyClientService = nettyClientService;
    }
    /**
     * @Description: 启动客户端
     * @Author:zhuqiang
     * @Since: 2019年9月12日下午4:43:21
     */
    @SuppressWarnings("unchecked")
    @PostConstruct
    public void start() {

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                // 指定Channel
                .channel(NioSocketChannel.class)
                // 服务端地址
                .remoteAddress(hostIp, port)

                // 将小的数据包包装成更大的帧进行传送，提高网络的负载,即TCP延迟传输
                .option(ChannelOption.SO_KEEPALIVE, true)
                // 将小的数据包包装成更大的帧进行传送，提高网络的负载,即TCP延迟传输
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new NettyClientHandlerInitilizer());

        // 连接
        ChannelFuture channelFuture = bootstrap.connect();
        //客户端断线重连逻辑
        channelFuture.addListener(new ChannelFutureListener() {

            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if(future.isSuccess()) {
                  System.out.println("连接Netty服务端成功...");
                }else {

                    System.out.println("连接Netty服务端失败，进行断线重连...");
                    final EventLoop loop =future.channel().eventLoop();
                    loop.schedule(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("连接正在重试...");
                            start();
                        }
                    }, 10, TimeUnit.SECONDS);
                }
            }
        });
        socketChannel = (SocketChannel) channelFuture.channel();
    }


    /**
     *@Description: 消息发送
     *@Author:杨攀
     *@Since: 2019年9月12日下午5:08:47
     *@param message
     */
    public void sendMsg(String  message) {

        String msg = message.concat(NettyClient.DELIMITER);

        ByteBuf byteBuf = Unpooled.copiedBuffer(msg, CharsetUtil.UTF_8);
        ChannelFuture future = socketChannel.writeAndFlush(byteBuf);

        future.addListener(new ChannelFutureListener() {

            @Override
            public void operationComplete(ChannelFuture future) throws Exception {

                if(future.isSuccess()) {
                    System.out.println("===========发送成功");
                }else {
                    System.out.println("------------------发送失败");
                }
            }
        });
    }



    /**
     *@Description: 发送同步消息
     *@Author:杨攀
     *@Since: 2019年9月12日下午5:08:47
     *@param message
     */
    public String sendSyncMsg(String  message) {

        String result = "";
        String msg = message.concat(NettyClient.DELIMITER);
        ByteBuf byteBuf = Unpooled.copiedBuffer(msg, CharsetUtil.UTF_8);
        try {

            ChannelFuture future = socketChannel.writeAndFlush(byteBuf);
            future.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {

                    if(future.isSuccess()) {
                        System.out.println("===========发送成功");
                    }else {
                        System.out.println("------------------发送失败");
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public String getHostIp() {
        return hostIp;
    }


    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }


    public int getPort() {
        return port;
    }


    public void setPort(int port) {
        this.port = port;
    }
}
