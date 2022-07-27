package cn.raft4j.core.protocol;

/**
 * @copyright Copyright 2017-2022 JD.COM All Right Reserved
 * @author: zhuqiang3
 * @version:
 * @Date: 2022-07-26 15:22
 * @Modified By:
 * @Description: TODO
 * @see
 */
public class RaftProtocol {

    public class RequestVoteReq {

        private Integer term;

        private Integer candidateId;

        private Long lastLogIndex;

        private Integer lastLogTerm;

        public Integer getTerm() {
            return term;
        }

        public void setTerm(Integer term) {
            this.term = term;
        }

        public Integer getCandidateId() {
            return candidateId;
        }

        public void setCandidateId(Integer candidateId) {
            this.candidateId = candidateId;
        }

        public Long getLastLogIndex() {
            return lastLogIndex;
        }

        public void setLastLogIndex(Long lastLogIndex) {
            this.lastLogIndex = lastLogIndex;
        }

        public Integer getLastLogTerm() {
            return lastLogTerm;
        }

        public void setLastLogTerm(Integer lastLogTerm) {
            this.lastLogTerm = lastLogTerm;
        }
    }

    public class RequestVoteResp{
        private Integer term;
        private boolean voteGranted;

        public Integer getTerm() {
            return term;
        }

        public void setTerm(Integer term) {
            this.term = term;
        }

        public boolean isVoteGranted() {
            return voteGranted;
        }

        public void setVoteGranted(boolean voteGranted) {
            this.voteGranted = voteGranted;
        }
    }

    public static RequestVoteReq RequestVoteReqBulider(){
        return new RaftProtocol().new RequestVoteReq();
    }

    public static RequestVoteResp RequestVoteRespBulider(){
        return new RaftProtocol().new RequestVoteResp();
    }
}
