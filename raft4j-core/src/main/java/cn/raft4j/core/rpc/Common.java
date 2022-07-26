package cn.raft4j.core.rpc;

import java.util.Random;

/**
 * @copyright Copyright 2017-2022 JD.COM All Right Reserved
 * @author: zhuqiang3
 * @version:
 * @Date: 2021-08-31 14:58
 * @Modified By:
 * @Description: TODO
 * @see
 */
public class Common {
    /**
     * @Author zhuqiang
     * @Date 2021/8/31
     * @Param
     * @return
     * @Description 随机范围内睡眠
     **/
    public static void randomSleep(int sleepRange){
        Random random = new Random();
        int sleep = random.nextInt(sleepRange)+1;
        sleep(sleep);

    }
    /**
     * @Author zhuqiang
     * @Date 2021/8/31
     * @Param
     * @return
     * @Description 睡眠
     **/
    public static  void sleep(long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
