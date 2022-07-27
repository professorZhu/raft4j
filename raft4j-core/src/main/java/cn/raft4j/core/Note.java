package cn.raft4j.core;

import cn.raft4j.core.rpc.client.NettyClientService;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @copyright Copyright 2017-2022 JD.COM All Right Reserved
 * @author: zhuqiang3
 * @version:
 * @Date: 2022-06-30 15:09
 * @Modified By:
 * @Description: TODO
 * @see
 */

public class Note {
    /**
     * ip
     */
    private String ip;
    /**
     * 端口
     */
    private Integer port;

    private RaftOptions raftOptions;

    private AtomicInteger currentTerm = new AtomicInteger(0); //当前的领导任期

    private Integer votedFor; //将选票投给了那个candidate

    private Integer leaderId; //leaderid

    private Integer serverId; //本机的id

    //1 follower 2 candidate 3 leader
    private AtomicInteger identity= new AtomicInteger(1);

    // 已知的最大的已经被提交的日志条目的索引值
    private long commitIndex;

    // 最后被应用到状态机的日志条目索引值（初始化为 0，持续递增）
    private volatile long lastAppliedIndex;

    //leader记录
    private long nextIndex;
    //leader记录
    private long matchIndex;

    // 该note的联系方式
    private NettyClientService nettyClientService;

    //最近一次与leader同步的时间
    private long lastTime = 0;

    private StateMachine stateMachine;

    /**
     * 获的的票数
     */
    private AtomicInteger vot = new AtomicInteger(0);


    public Note(){

    }
    public Note(String ip,Integer port,Integer serverId){
        this.ip = ip;
        this.port = port;
        this.serverId = serverId;
    }

    /**
     *  增加一票
     */
    public void incrementVot(){
        vot.getAndIncrement();
    }

    /**
     * 获取已经得到的选票
     */
    public Integer getVot(){
        return vot.get();
    }

    /**
     * 清空选票
     */
    public void clearVot(){
        vot.getAndSet(0);
    }

    public Boolean isFollower(){
        return identity.get() == 1;
    }
    public Boolean isCandidate(){
        return identity.get() == 2;
    }
    public Boolean isLeader(){
        return identity.get() == 3;
    }
    /**
     * 晋升为leader 后做的事
     */
    public void leader(){
        identity.getAndSet(3);
        clearVot();//清除票数
        leader();
    }
    /**
     * 成为follower后做的事
     */
    public void follower(){
        identity.getAndSet(1);
    }

    /**
     * 成为竞选者做的事
     */
    public void candidate(){
        identity.getAndSet(2);

    }
    /**
     * 更新最近一次同步数据的时间
     */
    public void resetLastTime(){
        this.lastTime = System.currentTimeMillis();
    }

    public long lastTime(){
        return lastTime;
    }

    public String getIp() {
        return ip;
    }

    public Integer getPort() {
        return port;
    }

    public NettyClientService getNettyClientService() {
        return nettyClientService;
    }

    public void setNettyClientService(NettyClientService nettyClientService) {
        this.nettyClientService = nettyClientService;
    }


    public AtomicInteger getCurrentTerm() {
        return currentTerm;
    }

    public void setCurrentTerm(AtomicInteger currentTerm) {
        this.currentTerm = currentTerm;
    }

    public Integer getVotedFor() {
        return votedFor;
    }

    public void setVotedFor(Integer votedFor) {
        this.votedFor = votedFor;
    }

    public Integer getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(Integer leaderId) {
        this.leaderId = leaderId;
    }

    public Integer getServerId() {
        return serverId;
    }

    public void setServerId(Integer serverId) {
        this.serverId = serverId;
    }

    public AtomicInteger getIdentity() {
        return identity;
    }

    public void setIdentity(AtomicInteger identity) {
        this.identity = identity;
    }

    public long getCommitIndex() {
        return commitIndex;
    }

    public void setCommitIndex(long commitIndex) {
        this.commitIndex = commitIndex;
    }

    public long getLastAppliedIndex() {
        return lastAppliedIndex;
    }

    public void setLastAppliedIndex(long lastAppliedIndex) {
        this.lastAppliedIndex = lastAppliedIndex;
    }

    public long getNextIndex() {
        return nextIndex;
    }

    public void setNextIndex(long nextIndex) {
        this.nextIndex = nextIndex;
    }

    public long getMatchIndex() {
        return matchIndex;
    }

    public void setMatchIndex(long matchIndex) {
        this.matchIndex = matchIndex;
    }

    public long getLastTime() {
        return lastTime;
    }

    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }

    public void setVot(AtomicInteger vot) {
        this.vot = vot;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return ip.equals(note.getIp()) && port.equals(note.getPort());
    }

    @Override
    public int hashCode() {
        return Objects.hash(ip, port);
    }
}
