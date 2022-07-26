package cn.raft4j.core.message;

import cn.raft4j.core.NoteContext;

import java.util.Objects;

/**
 * @copyright Copyright 2017-2022 JD.COM All Right Reserved
 * @author: zhuqiang3
 * @version:
 * @Date: 2022-07-07 20:30
 * @Modified By:
 * @Description: TODO
 * @see
 */
public abstract class AbstractMessageHandle implements MessageHandleService {

    protected NoteContext noteContext = NoteContext.INSTANCE;

    public Message dealIn(Message message){
        if (!Objects.equals(message.getType(),type())){
            return message;
        }
        return deal(message);
    }

}
