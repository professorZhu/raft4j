package cn.raft4j.core;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @copyright Copyright 2017-2022 JD.COM All Right Reserved
 * @author: zhuqiang3
 * @version:
 * @Date: 2022-07-06 10:55
 * @Modified By:
 * @Description: 集群上下文
 * @see
 */
public class NoteContext {

    public static NoteContext INSTANCE = new NoteContext();

    private NoteContext(){}

    private static Note localNote;
    /**
     * 集群中的所有节点
     */
    private static Set<Note> notes = new HashSet<>();

    public void addNode(Note note){
        notes.add(note);
    }

    public void addAllNote(List<Note> list){
        notes.addAll(list);
    }
    public void remove(Note note){
        notes.remove(note);
    }

    public Set<Note> getAllNote(){
        return notes;
    }

    public void setLocalNote(Note note){
        localNote = note;
    }

    public Note getLocalNote(){
        return localNote;
    }
}
