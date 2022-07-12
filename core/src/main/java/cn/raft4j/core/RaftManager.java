package cn.raft4j.core;

import cn.raft4j.common.util.NanoIdUtils;
import cn.raft4j.core.message.Message;
import com.alibaba.fastjson.JSON;

import java.util.Objects;
import java.util.Set;
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

    private NoteContext noteContext = NoteContext.INSTANCE;


    private long eletime = 1000; //选举超时时间


    private ScheduledExecutorService service = Executors.newScheduledThreadPool(5);


    public void start() {
        Note note = noteContext.getLocalNote();
        //5毫秒执行一次
        service.scheduleAtFixedRate(()->{
            if (!note.isLeader() && reCandidate(note) && candidateRun(note) ){
                note.leader(); //晋升为leader
            }else{
                note.resetLastTime(); //重置候选时间
            }
        },10,2000, TimeUnit.MILLISECONDS);



        //如果是leader 开始同步消息
        service.scheduleAtFixedRate(()->{
            if (note.isLeader() && append() ){
                note.resetLastTime();//现在没什么用，在数据同步时，需要追加操作
            }
        },0,1000, TimeUnit.MILLISECONDS);
    }

    public boolean reCandidate(Note note){
        long time = System.currentTimeMillis();
        if (Objects.equals(note.lastTime(),0)){
            note.resetLastTime();
        }
        if (time - note.lastTime() > eletime){
            System.out.println("---------------开始晋选-----------");
            note.candidate(); //成为竞选人
            return true;
        }
        return false;
    }
    public boolean candidateRun(Note note){
        boolean success=false;
        note.incrementVot();
        if (election()){
            System.out.println("竞选成功");
            success=true;
        }
        note.clearVot();
        return success;
    }


    public  boolean election(){
        Note loaclNote = noteContext.getLocalNote();
        for (Note note : noteContext.getAllNote()){
            if (electionMessage(note)) loaclNote.incrementVot();
        }

        int vot = loaclNote.getVot();
        System.out.println("本次竞选获取选票："+vot);
        Set<Note> set =  noteContext.getAllNote();
        int size = set.size();
        if (vot >= (size+1/2)){
            return true;
        }
        return false;
    }

    public boolean electionMessage(Note note){
        try {
            Message message = new Message();
            message.setUuid(NanoIdUtils.randomNanoId());
            message.setType(1);
            Message   re = note.getNettyClientService().sendSyncMsg(message);
            if (re!=null && Objects.equals(re.getContent(),"ok")){ //这里的判断需要优化
                System.out.println("获得选票");
                return true;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    //测试方法leader 心跳消息
    public  boolean append(){
        for (Note note : noteContext.getAllNote()){
            try{
                Message message = new Message();
                message.setType(2);
                message.setUuid(NanoIdUtils.randomNanoId());
                Message reMessage = note.getNettyClientService().sendSyncMsg(message);
                if (reMessage==null){
                    System.out.println("没有收到"+note.getIp()+":"+note.getPort()+"反馈的消息");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return true;
    }

}
