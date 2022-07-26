package cn.raft4j.core.rpc;


import cn.raft4j.core.common.NanoIdUtils;
import cn.raft4j.core.message.Message;
import cn.raft4j.core.rpc.client.NettyClient;
import cn.raft4j.core.rpc.client.NettyClientService;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @copyright Copyright 2017-2022 JD.COM All Right Reserved
 * @author: zhuqiang3
 * @version:
 * @Date: 2021-08-30 17:46
 * @Modified By:
 * @Description: TODO
 * @see
 */
public class ClientMain {


    public static void main(String args []) throws Exception {
        NettyClientService nettyClientService = new NettyClientService();
        nettyClientService.setIp("127.0.0.1");
        nettyClientService.setPort(8888);
        NettyClient client = new NettyClient(nettyClientService);

        new Thread(()->{
            client.start();
        }).start();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        for (;;){
            String str = bufferedReader.readLine();
            if (str==null||str.length()<1){
                System.out.println("请求为空");
                continue;
            }

            Message message = new Message();

            message.setType(1);
            message.setUuid(NanoIdUtils.randomNanoId());
            message.setContent("异步调用: "+str);
            // 异步调用
            SyncFuture<Message> future =  nettyClientService.sendAsyncMsg(message);
            System.out.println(future.get().toString());

            message.setType(1);
            message.setUuid(NanoIdUtils.randomNanoId());
            message.setContent("同步调用:"+str);
            //同步调用
            Message result = nettyClientService.sendSyncMsg(message);
            System.out.println(result.toString());

        }

    }
}
