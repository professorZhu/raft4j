package cn.raft4j.core.netty.server;

import cn.raft4j.core.Note;
import cn.raft4j.core.NoteContext;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @copyright Copyright 2017-2022 JD.COM All Right Reserved
 * @author: zhuqiang3
 * @version:
 * @Date: 2021-08-30 17:45
 * @Modified By:
 * @Description: TODO
 * @see
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyServerHandler.class);

    public static AtomicInteger nConnection = new AtomicInteger(0);

    private ExecutorService executorService = Executors.newFixedThreadPool(128);

    private NoteContext noteContext = NoteContext.INSTANCE;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        String txt = msg.toString(CharsetUtil.UTF_8);
        LOGGER.info("收到客户端的消息：{}", txt);
        executorService.execute(()->{
            ackMessage(ctx, txt);
        });

    }

    /**
     * @Author zhuqiang
     * @Date 2021/8/31
     * @Param
     * @return
     * @Description 响应
     *              所有响应信息都在这里实现。
     *              follower 接受选举消息，根据自身状态判断是否需要投票
     *                       接受leader的心跳及同步数据消息，更改自身数据
     *              leader   接收follower的回调消息
     **/
    public void ackMessage(ChannelHandlerContext ctx, String message) {

        //自定义分隔符
        String msg = message+ NettyServer.DELIMITER;
        ByteBuf byteBuf = Unpooled.copiedBuffer(msg, CharsetUtil.UTF_8);
        //回应客户端
        ctx.writeAndFlush(byteBuf);
    }



    /**
     *@Description: 每次来一个新连接就对连接数加一
     *@Author:zhuqiang
     *@Since: 2019年9月16日下午3:04:42
     *@param ctx
     *@throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        nConnection.incrementAndGet();
        LOGGER.info("请求连接...{}，当前连接数: ：{}",  ctx.channel().id(),nConnection.get());
    }

    /**
     *@Description: 每次与服务器断开的时候，连接数减一
     *@Author:zhuqiang
     *@Since: 2019年9月16日下午3:06:10
     *@param ctx
     *@throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        nConnection.decrementAndGet();
        LOGGER.info("断开连接...当前连接数: ：{}",  nConnection.get());
    }


    /**
     *@Description: 连接异常的时候回调
     *@Author:zhuqiang
     *@Since: 2019年9月16日下午3:06:55
     *@param ctx
     *@param cause
     *@throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        // 打印错误日志
        cause.printStackTrace();
        Channel channel = ctx.channel();
        if(channel.isActive()){
            ctx.close();
        }

    }

}

