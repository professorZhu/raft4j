package cn.raft4j.core.netty.client;

import cn.raft4j.core.netty.Content;
import cn.raft4j.core.netty.SyncFuture;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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


    public void setNettyClient(NettyClient nettyClient){
        this.nettyClient=nettyClient;
    }
    
    public String sendSyncMsg(String text, String dataId, String serviceId) throws InterruptedException {

        // 封装数据
        JSONObject object = new JSONObject();
        object.put("dataId", dataId);
        object.put("text", text);
        object.put("serviceId",serviceId);
        // 发送同步消息
        nettyClient.sendSyncMsg(object.toJSONString());
        SyncFuture<String> syncFuture = new SyncFuture<String>();
        // 放入缓存中
        Content.futureCache.put(serviceId, syncFuture);

        return syncFuture.get();
    }



    public SyncFuture<String> sendAsyncMsg(String text, String dataId, String serviceId) {
        // 封装数据
        JSONObject object = new JSONObject();
        object.put("dataId", dataId);
        object.put("text", text);
        object.put("serviceId",serviceId);
        // 发送同步消息
        nettyClient.sendSyncMsg(object.toJSONString());
        SyncFuture<String> syncFuture = new SyncFuture<String>();
        // 放入缓存中
        Content.futureCache.put(serviceId, syncFuture);
        return syncFuture;
    }

}
