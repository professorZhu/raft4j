package cn.raft4j.core;

import cn.raft4j.core.netty.client.NettyClientService;
import io.netty.channel.socket.SocketChannel;

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

    private String ip;

    private Integer port;

    //通信通道
    private NettyClientService nettyClientService;


    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public NettyClientService getNettyClientService() {
        return nettyClientService;
    }

    public void setNettyClientService(NettyClientService nettyClientService) {
        this.nettyClientService = nettyClientService;
    }
}
