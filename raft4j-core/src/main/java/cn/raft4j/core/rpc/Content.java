package cn.raft4j.core.rpc;

import com.google.common.cache.*;

import java.util.concurrent.TimeUnit;

/**
 * @copyright Copyright 2017-2022 JD.COM All Right Reserved
 * @author: zhuqiang3
 * @version:
 * @Date: 2021-08-31 15:11
 * @Modified By:
 * @Description: TODO
 * @see
 */
public class Content {

    //缓存接口这里是LoadingCache，LoadingCache在缓存项不存在时可以自动加载缓存
    public static LoadingCache<String, SyncFuture> futureCache = CacheBuilder.newBuilder()
            //设置缓存容器的初始容量为10
            .initialCapacity(100)
            // maximumSize 设置缓存大小
            .maximumSize(10000)
            //设置并发级别为20，并发级别是指可以同时写缓存的线程数
            .concurrencyLevel(20)
            // expireAfterWrite设置写缓存后10秒钟过期
            .expireAfterWrite(10, TimeUnit.SECONDS)
            //设置缓存的移除通知
            .removalListener(new RemovalListener<Object, Object>() {
                @Override
                public void onRemoval(RemovalNotification<Object, Object> notification) {
//                    System.out.println("LoadingCache: "+notification.getKey()+" was removed, cause is "+ notification.getCause());
                }
            })
            //build方法中可以指定CacheLoader，在缓存不存在时通过CacheLoader的实现自动加载缓存
            .build(new CacheLoader<String, SyncFuture>() {
                @Override
                public SyncFuture load(String key) throws Exception {
                    // 当获取key的缓存不存在时，不需要自动添加
                    return null;
                }
            });

}
