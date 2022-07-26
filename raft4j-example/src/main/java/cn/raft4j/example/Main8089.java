package cn.raft4j.example;

import cn.raft4j.core.*;
import cn.raft4j.core.message.ElectionMessageHandle;
import cn.raft4j.core.message.LeaderMessageHandle;

/**
 * @copyright Copyright 2017-2022 JD.COM All Right Reserved
 * @author: zhuqiang3
 * @version:
 * @Date: 2022-07-06 16:23
 * @Modified By:
 * @Description: TODO
 * @see
 */
public class Main8089 {


    public static void main(String [] args){
        MessageHandlerContext handlerContext = MessageHandlerContext.INSTANCE;
        handlerContext.addHandler(new ElectionMessageHandle());
        handlerContext.addHandler(new LeaderMessageHandle());
        Main.main8089();
    }

}
