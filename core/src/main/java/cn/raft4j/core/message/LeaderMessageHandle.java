package cn.raft4j.core.message;

import cn.raft4j.core.Note;

/**
 * @copyright Copyright 2017-2022 JD.COM All Right Reserved
 * @author: zhuqiang3
 * @version:
 * @Date: 2022-07-07 20:32
 * @Modified By:
 * @Description: TODO
 * @see
 */
public class LeaderMessageHandle extends AbstractMessageHandle{

    //leader 同步过来的的消息
    @Override
    public Integer type() {
        return 2;
    }

    @Override
    public Message deal(Message message) {
        Note note = noteContext.getLocalNote();
        note.resetLastTime();
        note.clearVot();
        note.follower();
        message.setContent(note.getIp()+":"+note.getPort()+"反馈leader消息");
        return message;
    }
}
