package cn.raft4j.core.rpc.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;

/**
 * @copyright Copyright 2017-2022 JD.COM All Right Reserved
 * @author: zhuqiang3
 * @version:
 * @Date: 2021-08-30 17:44
 * @Modified By:
 * @Description: TODO
 * @see
 */
public class NettyServerHandlerInitializer extends ChannelInitializer<Channel> {


    @Override
    protected void initChannel(Channel ch) throws Exception {

        // 通过socketChannel去获得对应的管道
        ChannelPipeline channelPipeline = ch.pipeline();

        /*
         * channelPipeline中会有很多handler类（也称之拦截器类）
         * 获得pipeline之后，可以直接.addLast添加handler
         */
        ByteBuf buf = Unpooled.copiedBuffer(NettyServer.DELIMITER.getBytes());
        channelPipeline.addLast("framer", new DelimiterBasedFrameDecoder(1024*1024*2, buf));
        //channelPipeline.addLast("decoder",new StringDecoder(CharsetUtil.UTF_8));
        //channelPipeline.addLast("encoder",new StringEncoder(CharsetUtil.UTF_8));

        // 自定义解码器，粘包/拆包/断包
        channelPipeline.addLast(new NettyServerHandler());
    }

}