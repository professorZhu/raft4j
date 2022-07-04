package cn.raft4j.core.netty;


import cn.raft4j.core.netty.server.NettyServer;

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

        NettyServer server = new NettyServer();

        server.start();


    }
}
