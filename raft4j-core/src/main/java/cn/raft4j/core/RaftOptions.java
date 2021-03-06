package cn.raft4j.core;

/**
 * @copyright Copyright 2017-2022 JD.COM All Right Reserved
 * @author: zhuqiang3
 * @version:
 * @Date: 2022-07-26 14:36
 * @Modified By:
 * @Description: raft配置信息
 * @see
 */
public class RaftOptions {

    //选举超时时间
    private int electionTimeoutMilliseconds = 2000;

    // leader心跳间隔
    private int heartbeatPeriodMilliseconds = 500;

//    // snapshot定时器执行间隔
//    private int snapshotPeriodSeconds = 3600;
//
//    // log entry大小达到snapshotMinLogSize，才做snapshot
//    private int snapshotMinLogSize = 100 * 1024 * 1024;
//    private int maxSnapshotBytesPerRequest = 500 * 1024; // 500k
//
//    private int maxLogEntriesPerRequest = 5000;
//
//    // 单个segment文件大小，默认100m
//    private int maxSegmentFileSize = 100 * 1000 * 1000;
//
//    // follower与leader差距在catchupMargin，才可以参与选举和提供服务
//    private long catchupMargin = 500;
//
//    // replicate最大等待超时时间，单位ms
//    private long maxAwaitTimeout = 1000;
//
//    // 与其他节点进行同步、选主等操作的线程池大小
//    private int raftConsensusThreadNum = 20;

    // 是否异步写数据；true表示主节点保存后就返回，然后异步同步给从节点；
    // false表示主节点同步给大多数从节点后才返回。
    private boolean asyncWrite = false;

    // raft的log和snapshot父目录，绝对路径
    private String dataDir = System.getProperty("cn.raft4j.core.data.dir");


    public int getElectionTimeoutMilliseconds() {
        return electionTimeoutMilliseconds;
    }

    public void setElectionTimeoutMilliseconds(int electionTimeoutMilliseconds) {
        this.electionTimeoutMilliseconds = electionTimeoutMilliseconds;
    }

    public int getHeartbeatPeriodMilliseconds() {
        return heartbeatPeriodMilliseconds;
    }

    public void setHeartbeatPeriodMilliseconds(int heartbeatPeriodMilliseconds) {
        this.heartbeatPeriodMilliseconds = heartbeatPeriodMilliseconds;
    }
    public boolean isAsyncWrite() {
        return asyncWrite;
    }

    public void setAsyncWrite(boolean asyncWrite) {
        this.asyncWrite = asyncWrite;
    }

    public String getDataDir() {
        return dataDir;
    }

    public void setDataDir(String dataDir) {
        this.dataDir = dataDir;
    }
}
