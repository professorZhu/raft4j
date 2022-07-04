package cn.raft4j.core;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @copyright Copyright 2017-2022 JD.COM All Right Reserved
 * @author: zhuqiang3
 * @version:
 * @Date: 2022-06-30 15:05
 * @Modified By:
 * @Description: TODO
 * @see
 */
public class RaftManager {

    private Integer identity=1; // 1 , 2 , 3 follower candidate leader

    private Integer vot; //投票数

    private long eletime = 500; //选举超时时间

    private Map<String,Note> noteMap;

    private Long lastLeader;

    private Note note;

    private ScheduledExecutorService service = Executors.newScheduledThreadPool(5);

    public void start() {

        //启动server
        ServerFactory.service(note);

        //初始化通道
        for (Note note : noteMap.values()){
            note.setNettyClientService(ClientServerFactory.getNettyClientService(note));
        }



        //5毫秒执行一次
        service.scheduleAtFixedRate(()->{
            if (identity!=3 ){
                if (candidateRun()) {
                    identity = 3;
                    lastLeader = System.currentTimeMillis();
                }
            }
        },0,1000, TimeUnit.MILLISECONDS);


//        //如果是leader 开始同步消息
//        service.scheduleAtFixedRate(()->{
//            if (identity==3 ){
//                append();
//            }
//        },0,5, TimeUnit.MILLISECONDS);

    }


    public void setNoteMap(Map<String, Note> noteMap) {
        this.noteMap = noteMap;
    }

    public boolean candidateRun(){
        boolean election = false;
        try{

            long time = System.currentTimeMillis();

            if (lastLeader==null){
                lastLeader = time;
            }
            if (time - lastLeader > eletime ){
                if (election()){
                    identity = 3;
                }
                lastLeader = System.currentTimeMillis();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return election;
    }


    public  boolean election(){
        for (Note note : noteMap.values()){
            try{
                String returen = note.getNettyClientService().sendSyncMsg("你好", UUID.randomUUID().toString(), UUID.randomUUID().toString());
                System.out.println("-----------"+returen+"-----------");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }


    int n =1;
    //测试方法leader 心跳消息
    public  boolean append(){

        n++;
        for (Note note : noteMap.values()){
            System.out.println(note.getIp()+"开始进行通信；"+n);
            lastLeader = System.currentTimeMillis();
        }
        if (n==1000){ //通信
            identity=1;//变成follower
            n=1;
            return false;
        }
        return true;
    }


    public  void recevie(){
        System.out.println("收到了消息");
        if (identity==1 && vot==0){
            System.out.println("投票");
        }
        System.out.println("不投票");
    }

    public void setNote(Note note) {
        this.note = note;
    }
}
