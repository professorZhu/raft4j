package cn.raft4j.core.message;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

/**
 * @copyright Copyright 2017-2022 JD.COM All Right Reserved
 * @author: zhuqiang3
 * @version:
 * @Date: 2022-06-30 15:12
 * @Modified By:
 * @Description: 通信主类
 * @see
 */
public class Message {

    private String uuid;

    private Integer type; // leader同步，投票，

    private String content; //内容


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Message{" +
                "uuid='" + uuid + '\'' +
                ", type=" + type +
                ", content='" + content + '\'' +
                '}';
    }
}
