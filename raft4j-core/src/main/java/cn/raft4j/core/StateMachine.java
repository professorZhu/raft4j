package cn.raft4j.core;

/**
 * Raft状态机接口类
 */
public interface StateMachine {

    /**
     * 将数据应用到状态机
     * @param dataBytes 数据二进制
     */
    void apply(byte[] dataBytes);

    /**
     * 获取状态机中最新的一条
     */
    byte[]  getLast();
}
