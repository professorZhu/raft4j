import cn.raft4j.core.Note;
import cn.raft4j.core.RaftManager;

import java.util.HashMap;
import java.util.Map;

/**
 * @copyright Copyright 2017-2022 JD.COM All Right Reserved
 * @author: zhuqiang3
 * @version:
 * @Date: 2022-06-30 14:56
 * @Modified By:
 * @Description: TODO
 * @see
 */
public class Note8088 {
    public static void main(String [] args) throws InterruptedException {
        RaftManager raftManager = new RaftManager();
        Note note = new Note();
        note.setPort(8088);
        raftManager.setNote(note);


        Map<String,Note> noteMap= new HashMap<>();
        Note note1 = new Note();
        note1.setIp("127.0.0.1");
        note1.setPort(8089);
        noteMap.put("key1",note1);
        raftManager.setNoteMap(noteMap);

        raftManager.start();

        Thread.sleep(100000L);

    }
}
