package cn.raft4j.core;

import cn.raft4j.common.util.NanoIdUtils;
import com.alibaba.fastjson.JSON;
import com.sun.tools.corba.se.idl.constExpr.Not;

import java.util.Map;
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


    private long eletime = 500; //选举超时时间


    private ScheduledExecutorService service = Executors.newScheduledThreadPool(5);


    public void start() {
        Note note = noteContext.getLocalNote();
        //5毫秒执行一次
        service.scheduleAtFixedRate(()->{
            if (!note.isLeader() && candidateRun(note)){
                note.leader(); //晋升为leader
                note.resetLastTime();
            }
        },0,1000, TimeUnit.MILLISECONDS);

        //如果是leader 开始同步消息
        service.scheduleAtFixedRate(()->{
            if (note.isLeader() && append() ){
                note.resetLastTime();//现在没什么用，在数据同步时，需要追加操作
            }
        },0,5, TimeUnit.MILLISECONDS);

    }

    public boolean candidateRun(Note note){
        boolean election = false;
        long time = System.currentTimeMillis();
        if (Objects.equals(note.lastTime(),0)){
            note.resetLastTime();
        }
        if (time - note.lastTime() > eletime && election()){
            note.success(); //选举成功的一些初始化操作
            return true; //选举成功
        }
        note.resetLastTime();
        return election;
    }


    public  boolean election(){
        Note loaclNote = noteContext.getLocalNote();
        for (Note note : noteContext.getAllNote()){
            if (electionMessage(note)) loaclNote.incrementVot();
        }
        return canLeader();
    }

    /**
     * 是否可以晋升
     */
    private boolean canLeader(){
        Note loaclNote = noteContext.getLocalNote();
        int vot = loaclNote.getVot();
        Set<Note> set =  noteContext.getAllNote();
        int size = set.size();
        if (vot > (size/2)){
            return true;
        }
        return false;
    }

    public boolean electionMessage(Note note){
        try {
        Message message = new Message();
        message.setUuid(NanoIdUtils.randomNanoId());
        message.setType(1);
        System.out.println("发送消息："+JSON.toJSONString(message));
        Message   re = note.getNettyClientService().sendSyncMsg(message);
        System.out.println("收到消息---------"+JSON.toJSONString(re));
        if (Objects.equals(re.getContent(),"ok")){ //这里的判断需要优化
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
                System.out.println(reMessage.toString());
                note.resetLastTime();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return true;
    }

}
