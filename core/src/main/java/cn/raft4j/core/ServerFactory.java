package cn.raft4j.core;

import cn.raft4j.core.Note;
import cn.raft4j.core.netty.client.NettyClientService;
import cn.raft4j.core.netty.server.NettyServer;

/**
 * @copyright Copyright 2017-2022 JD.COM All Right Reserved
 * @author: zhuqiang3
 * @version:
 * @Date: 2022-07-04 11:00
 * @Modified By:
 * @Description: TODO
 * @see
 */
public class ServerFactory {


    public static void service(Note note){
        try {
            NettyServer server = new NettyServer(note.getPort());
            server.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
