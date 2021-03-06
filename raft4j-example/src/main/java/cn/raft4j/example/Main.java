package cn.raft4j.example;

import cn.raft4j.core.Note;
import cn.raft4j.core.NoteContext;
import cn.raft4j.core.NoteFactory;
import cn.raft4j.core.RaftManager;
import cn.raft4j.core.common.NanoIdUtils;

/**
 * @copyright Copyright 2017-2022 JD.COM All Right Reserved
 * @author: zhuqiang3
 * @version:
 * @Date: 2022-07-06 18:20
 * @Modified By:
 * @Description: TODO
 * @see
 */
public class Main {
    public static  void main(String [] arsg){
        //        String server = "127.0.0.1:8088:"+ NanoIdUtils.randomNanoId();
//        //集群中的所有节点 ip:port:serverId
//        List<String> servers = new ArrayList<>();
//        servers.add(server);
//        //本机节点 ip:port:serverId
//        String localServer = "127.0.0.1:8089:"+NanoIdUtils.randomNanoId();
//        main8089();

    }


    public static void main8090(){
        NoteContext noteContext = NoteContext.INSTANCE;
        Note note = new Note("127.0.0.1",8088, 8088);
        noteContext.addNode(note);
        Note note1 = new Note("127.0.0.1",8089, 8089);
        noteContext.addNode(note1);

        Note localNote = new Note("127.0.0.1",8090, 8090);
        noteContext.setLocalNote(localNote);

        //初始化所有节点
        NoteFactory.INSTANCE.build(noteContext);

        //可以读取一些配置对manager进行配置
        RaftManager raftManager = new RaftManager(500);
        raftManager.start();
    }
    public static void main8089(){
        NoteContext noteContext = NoteContext.INSTANCE;
        Note note = new Note("127.0.0.1",8088, 8088);
        noteContext.addNode(note);
        Note note1 = new Note("127.0.0.1",8090, 8090);
        noteContext.addNode(note1);

        Note localNote = new Note("127.0.0.1",8089,8089);
        noteContext.setLocalNote(localNote);

        //初始化所有节点
        NoteFactory.INSTANCE.build(noteContext);

        //可以读取一些配置对manager进行配置
        RaftManager raftManager = new RaftManager(600);
        raftManager.start();
    }

    public static void main8088(){
        NoteContext noteContext = NoteContext.INSTANCE;
        Note note = new Note("127.0.0.1",8089, 8089);
        noteContext.addNode(note);

        Note note1 = new Note("127.0.0.1",8090, 8090);
        noteContext.addNode(note1);

        Note localNote = new Note("127.0.0.1",8088, 8088);
        noteContext.setLocalNote(localNote);

        //初始化所有节点
        NoteFactory.INSTANCE.build(noteContext);

        //可以读取一些配置对manager进行配置
        RaftManager raftManager = new RaftManager(700);
        raftManager.start();
    }
}
