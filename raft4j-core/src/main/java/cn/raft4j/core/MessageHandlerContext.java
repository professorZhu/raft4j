package cn.raft4j.core;

import cn.raft4j.core.message.AbstractMessageHandle;
import cn.raft4j.core.message.MessageHandleService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @copyright Copyright 2017-2022 JD.COM All Right Reserved
 * @author: zhuqiang3
 * @version:
 * @Date: 2022-07-07 20:46
 * @Modified By:
 * @Description: TODO
 * @see
 */
public class MessageHandlerContext {

    public static MessageHandlerContext INSTANCE = new MessageHandlerContext();

    private MessageHandlerContext(){}

    private static List<AbstractMessageHandle> handles = new ArrayList<>();


    public  void addHandler(AbstractMessageHandle handle){
        handles.add(handle);
    }

    public List<AbstractMessageHandle>  getAllHandler(){
        return handles;
    }


    public AbstractMessageHandle getHandle(Integer type){
        for (AbstractMessageHandle abstractMessageHandle : handles){
            if (Objects.equals(abstractMessageHandle.type(),type)){
                return abstractMessageHandle;
            }
        }
        return null;
    }
}
