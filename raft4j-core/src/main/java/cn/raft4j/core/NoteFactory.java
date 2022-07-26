package cn.raft4j.core;

import cn.raft4j.core.netty.client.NettyClient;
import cn.raft4j.core.netty.client.NettyClientService;
import cn.raft4j.core.netty.server.NettyServer;

import java.util.Set;

/**
 * @copyright Copyright 2017-2022 JD.COM All Right Reserved
 * @author: zhuqiang3
 * @version:
 * @Date: 2022-07-06 18:06
 * @Modified By:
 * @Description: TODO
 * @see
 */
public class NoteFactory {

    public static NoteFactory INSTANCE = new NoteFactory();
    private NoteFactory(){

    }
    public  void build(NoteContext context){
        Note localNote = context.getLocalNote();
        server(localNote);
        Set<Note> notes = context.getAllNote();
        noteClients(notes);
    }

    private void server(Note note){
        try {
            NettyServer server = new NettyServer(note.getPort());
            server.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private  void noteClients( Set<Note> notes ){
        for (Note note : notes){
            NettyClientService nettyClientService = new NettyClientService();
            nettyClientService.setIp(note.getIp());
            nettyClientService.setPort(note.getPort());
            NettyClient client = new NettyClient(nettyClientService);
            client.start();
            note.setNettyClientService(nettyClientService);
        }
    }
}
