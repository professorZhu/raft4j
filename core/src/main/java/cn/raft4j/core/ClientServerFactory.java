package cn.raft4j.core;

import cn.raft4j.core.Note;
import cn.raft4j.core.netty.client.NettyClient;
import cn.raft4j.core.netty.client.NettyClientService;

/**
 * @copyright Copyright 2017-2022 JD.COM All Right Reserved
 * @author: zhuqiang3
 * @version:
 * @Date: 2022-07-04 10:52
 * @Modified By:
 * @Description: 获取clientServer
 * @see
 */
public class ClientServerFactory {

        public static NettyClientService getNettyClientService(Note note){
            NettyClientService nettyClientService = new NettyClientService();
            nettyClientService.setIp(note.getIp());
            nettyClientService.setPort(note.getPort());
            NettyClient client = new NettyClient(nettyClientService);
            client.start();
            return nettyClientService;
        }

}
