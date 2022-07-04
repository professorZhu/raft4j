package cn.raft4j.core.netty;



import cn.raft4j.core.netty.client.NettyClient;
import cn.raft4j.core.netty.client.NettyClientService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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
            String arr[] = str.split("-");
            if (arr.length<2){
                System.out.println("错误的请求参数");
                continue;
            }
//            // 异步调用
//            System.out.println("发送异步请求");
//            SyncFuture<String> future =  nettyClientService.sendAsyncMsg(arr[0],arr[1],UUID.randomUUID().toString());
//            // 异步调用
//            System.out.println("发送异步请求");
//            SyncFuture<String> future1 =  nettyClientService.sendAsyncMsg(arr[0],arr[1],UUID.randomUUID().toString());
//            System.out.println("service result async：" + future.get(2000, TimeUnit.MILLISECONDS));
//            System.out.println("service result async：" + future1.get(2000, TimeUnit.MILLISECONDS));
//
//            //同步调用
//            System.out.println("发送同步请求");
//            String result = nettyClientService.sendSyncMsg(arr[0],arr[1],UUID.randomUUID().toString());
//            System.out.println("发送同步请求");
//            String result1 = nettyClientService.sendSyncMsg(arr[0],arr[1],UUID.randomUUID().toString());
//            System.out.println("service result sync：" + result);
//            System.out.println("service result sync：" + result1);

        }

    }
}
