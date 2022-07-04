package cn.raft4j.core.netty.client;

import cn.raft4j.core.netty.Content;
import cn.raft4j.core.netty.SyncFuture;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @copyright Copyright 2017-2022 JD.COM All Right Reserved
 * @author: zhuqiang3
 * @version:
 * @Date: 2021-08-30 17:24
 * @Modified By:
 * @Description: TODO
 * @see
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyClientHandler.class);

    /**
     * @Description: 服务端发生消息给客户端，会触发该方法进行接收消息
     * @Author: zhuqiang25
     * @param ctx
     * @param byteBuf
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf byteBuf) throws Exception {

        String msg = byteBuf.toString(CharsetUtil.UTF_8);


        ackSyncMsg(msg); // 同步消息返回
    }

    private void ackSyncMsg(String msg) {

        JSONObject object = JSON.parseObject(msg);
        String serviceId = object.getString("serviceId");
        System.out.println("服务端响应请求 serviceId：{}"+ serviceId);
        // 从缓存中获取数据
        SyncFuture<String> syncFuture = Content.futureCache.getIfPresent(serviceId);

        // 如果不为null, 则通知返回
        if(syncFuture != null) {
            syncFuture.setResponse(msg);
            //主动释放
            Content.futureCache.invalidate(serviceId);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("请求连接成功...");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        System.out.println("连接被断开...");
//
//        // 使用过程中断线重连
//        final EventLoop eventLoop = ctx.channel().eventLoop();
//        eventLoop.schedule(new Runnable() {
//
//            @Override
//            public void run() {
//                // 重连
//                nettyClient.start();
//            }
//        }, 5, TimeUnit.SECONDS);
        super.channelInactive(ctx);
    }

    /**
     * 处理异常, 一般将实现异常处理逻辑的Handler放在ChannelPipeline的最后
     * 这样确保所有入站消息都总是被处理，无论它们发生在什么位置，下面只是简单的关闭Channel并打印异常信息
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();

        Channel channel = ctx.channel();
        if (channel.isActive()) {
            ctx.close();
        }
    }

}