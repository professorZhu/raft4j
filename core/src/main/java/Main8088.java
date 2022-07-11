import cn.raft4j.core.Main;
import cn.raft4j.core.MessageHandlerContext;
import cn.raft4j.core.message.ElectionMessageHandle;
import cn.raft4j.core.message.LeaderMessageHandle;

/**
 * @copyright Copyright 2017-2022 JD.COM All Right Reserved
 * @author: zhuqiang3
 * @version:
 * @Date: 2022-07-06 18:19
 * @Modified By:
 * @Description: TODO
 * @see
 */
public class Main8088 {


    public static void main(String [] args){
        MessageHandlerContext handlerContext = MessageHandlerContext.INSTANCE;
        handlerContext.addHandler(new ElectionMessageHandle());
        handlerContext.addHandler(new LeaderMessageHandle());
        Main.main8088();
    }
}
