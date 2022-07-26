package cn.raft4j.core.rpc;


import cn.raft4j.core.MessageHandlerContext;
import cn.raft4j.core.message.ElectionMessageHandle;
import cn.raft4j.core.message.LeaderMessageHandle;
import cn.raft4j.core.rpc.server.NettyServer;

/**
 * @copyright Copyright 2017-2022 JD.COM All Right Reserved
 * @author: zhuqiang3
 * @version:
 * @Date: 2021-08-30 17:46
 * @Modified By:
 * @Description: TODO
 * @see
 */
public class ServerMain {


    public static void main(String args []) throws Exception {
        MessageHandlerContext handlerContext = MessageHandlerContext.INSTANCE;
        handlerContext.addHandler(new ElectionMessageHandle());
        handlerContext.addHandler(new LeaderMessageHandle());
        NettyServer server = new NettyServer();

        server.start();


    }
}
