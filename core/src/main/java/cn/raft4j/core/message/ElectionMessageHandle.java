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
public class ElectionMessageHandle extends AbstractMessageHandle{

    //选举类类型的消息
    @Override
    public Integer type() {
        return 1;
    }

    @Override
    public Message deal(Message message) {
        Note note = noteContext.getLocalNote();
        if (note.isCandidate()  && note.getVot()==0){
            System.out.println("-------------投票-------------");
            note.clearVot();
            note.resetLastTime();
            message.setContent("ok");
            return message;
        }
        System.out.println("-------------不投票-------------");

        message.setContent("not ok");
        return message;
    }
}
