package cn.raft4j.core.message;

/**
 * @copyright Copyright 2017-2022 JD.COM All Right Reserved
 * @author: zhuqiang3
 * @version:
 * @Date: 2022-07-07 20:28
 * @Modified By:
 * @Description: TODO
 * @see
 */
public interface MessageHandleService {

    Integer type();


    Message deal(Message message);
}
