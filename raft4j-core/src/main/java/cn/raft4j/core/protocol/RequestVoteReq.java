package cn.raft4j.core.protocol;

/**
 * @copyright Copyright 2017-2022 JD.COM All Right Reserved
 * @author: zhuqiang3
 * @version:
 * @Date: 2022-07-26 15:20
 * @Modified By:
 * @Description: TODO
 * @see
 */
public class RequestVoteReq {

    private Integer term;

    private Integer candidateId;

    private Long lastLogIndex;

    private Integer lastLogTerm;

}
