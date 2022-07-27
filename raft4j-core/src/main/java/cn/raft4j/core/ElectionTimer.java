package cn.raft4j.core;

import cn.raft4j.core.common.NanoIdUtils;
import cn.raft4j.core.message.Message;
import cn.raft4j.core.protocol.RaftProtocol;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.*;

/**
 * @copyright Copyright 2017-2022 JD.COM All Right Reserved
 * @author: zhuqiang3
 * @version:
 * @Date: 2022-07-26 15:19
 * @Modified By:
 * @Description: TODO
 * @see
 */
public class ElectionTimer {

    private RaftOptions raftOptions;


    private StateMachine stateMachine;


    private NoteContext noteContext = NoteContext.INSTANCE;

    private ScheduledExecutorService service = Executors.newScheduledThreadPool(5);

    private ExecutorService executorService = Executors.newFixedThreadPool(20);

    public void startelectionTimer(){
        Note note = noteContext.getLocalNote();

        service.scheduleAtFixedRate(()->{
            if (!note.isLeader() && reCandidate(note) && candidateRun(note) ){
                note.leader(); //晋升为leader
            }else{
                note.resetLastTime(); //重置候选时间
            }
        },10, 50, TimeUnit.MILLISECONDS);
    }

    public boolean reCandidate(Note note){
        long time = System.currentTimeMillis();
        if (Objects.equals(note.lastTime(),0)){
            note.resetLastTime();
        }
        System.out.println(time - note.lastTime() );
        if (time - note.lastTime() > raftOptions.getElectionTimeoutMilliseconds()){
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
        List<CompletableFuture<Boolean>> list = new ArrayList<>();
        for (Note note : noteContext.getAllNote()){
            CompletableFuture<Boolean> future = CompletableFuture.supplyAsync(()->electionMessage(note),executorService);
            list.add(future);
        }
        try{
            CompletableFuture.allOf(list.toArray(new CompletableFuture[list.size()])).get();
            for (CompletableFuture<Boolean> future:list){
                if (future.get()) loaclNote.incrementVot();
            }
        }catch (Exception e){
            return false;
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
            RaftProtocol.RequestVoteReq requestVoteReq = RaftProtocol.RequestVoteReqBulider();
            Message message = new Message();
            message.setUuid(NanoIdUtils.randomNanoId());
            message.setType(1);
            requestVoteReq.setTerm(note.getCurrentTerm().get());
            requestVoteReq.setCandidateId(note.getServerId());
            requestVoteReq.setLastLogIndex(note.getCommitIndex());
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
}
