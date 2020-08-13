package org.onap.so.adapters.nssmf.manager;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.onap.so.adapters.nssmf.enums.ActionType;
import org.onap.so.adapters.nssmf.exceptions.ApplicationException;
import org.onap.so.db.request.beans.ResourceOperationStatus;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
public class JobCache {
    private static LoadingCache<String, ResourceOperationStatus> cache;
//
//    public JobCache()  {
//        cache = CacheBuilder.newBuilder()
//                .maximumSize(1000)
//                .weakKeys()
//                .weakValues()
//                .expireAfterWrite(3, TimeUnit.HOURS)
//                .build(new CacheLoader<String, ResourceOperationStatus>() {
//                    @Override
//                    public ResourceOperationStatus load(@NonNull String key) throws Exception {
//
//                    }
//                });
//    }
//
//
//    public static void main(String[] args) {
//        JobCache.add("123", ActionType.ALLOCATE);
//        System.out.println("ww");
//    }
//
//
//    private ResourceOperationStatus getOperationStatus(String jobId) {
//
//    }
//
//    /**
//     * Callable callback 初始化
//     *
//     * 读取缓存数据 如果没有则回调数据源并(自动)写入缓存
//     * @param jobId   jobId
//     */
//    public static void add(String jobId, ActionType actionType) {
//        cache.put(jobId, actionType);
//    }
//
//    /**
//     * remove jobId in cache
//     * @param jobId cache key
//     */
//    public static void remove(String jobId){
//        cm.remove(jobId);
//    }
}
