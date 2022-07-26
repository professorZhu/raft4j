package cn.raft4j.core.rpc.client;

import cn.raft4j.core.message.Message;
import cn.raft4j.core.rpc.Content;
import cn.raft4j.core.rpc.SyncFuture;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @copyright Copyright 2017-2022 JD.COM All Right Reserved
 * @author: zhuqiang3
 * @version:
 * @Date: 2021-08-30 17:23
 * @Modified By:
 * @Description: TODO
 * @see
 */
public class NettyClientService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyClientService.class);

    private NettyClient nettyClient;

    private String ip;

    private Integer port;

    public void setNettyClient(NettyClient nettyClient){
        this.nettyClient=nettyClient;
    }

    /**
     * 发送消息
     */
    public Message sendSyncMsg(Message message) throws InterruptedException {
        // 封装数据
        String messageStr = JSON.toJSONString(message);
        // 发送同步消息
        nettyClient.sendSyncMsg(messageStr);
        SyncFuture<Message> syncFuture = new SyncFuture<Message>();
        // 放入缓存中
        Content.futureCache.put(message.getUuid(), syncFuture);
        return syncFuture.get(500, TimeUnit.MILLISECONDS);
    }

    /**
     * 异步发送消息
     */
    public SyncFuture<Message> sendAsyncMsg(Message message) {
        // 封装数据
        String messageStr = JSON.toJSONString(message);
        // 发送同步消息
        nettyClient.sendSyncMsg(messageStr);
        SyncFuture<Message> syncFuture = new SyncFuture<Message>();
        // 放入缓存中
        Content.futureCache.put(message.getUuid(), syncFuture);
        return syncFuture;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
