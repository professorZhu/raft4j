package cn.raft4j.core;

import cn.raft4j.core.netty.client.NettyClientService;
import io.netty.channel.socket.SocketChannel;

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
    /**
     * serverId
     */
    private String serverId;

    /**
     * leaderServerId;
     */
    private String leaderServerId;

    /**
     * 1 follower 2 candidate 3 leader
     * 默认 follower
     */
    private AtomicInteger identity= new AtomicInteger(1);

    /**
     * 获的的票数
     */
    private AtomicInteger vot = new AtomicInteger(0);

    /**
     * 最近一次与leader同步的时间
     */
    private long lastTime = 0;

    // note的联系方式
    private NettyClientService nettyClientService;

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
        vot.set(0);
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
     * 晋升为leader
     */
    public void leader(){
        identity.getAndSet(3);
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


    public void success(){

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
