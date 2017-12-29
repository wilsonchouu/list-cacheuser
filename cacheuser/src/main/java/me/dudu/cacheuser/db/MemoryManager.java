package me.dudu.cacheuser.db;

import android.support.v4.util.LruCache;

/**
 * Author : zhouyx
 * Date   : 2017/11/16
 * Description : lru缓存
 */
class MemoryManager {

    private static MemoryManager ourInstance;

    private MemoryManager() {
        long maxMemory = Runtime.getRuntime().maxMemory();
        int cacheSize = (int) (maxMemory / 6);
        cache = new LruCache<String, CacheUser>(cacheSize) {
            @Override
            protected int sizeOf(String key, CacheUser value) {
                return value.getSize();
            }
        };
    }

    static synchronized MemoryManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new MemoryManager();
        }
        return ourInstance;
    }

    private LruCache<String, CacheUser> cache;

    /**
     * 存入数据到内存
     *
     * @param key
     * @param value
     */
    void put(String key, CacheUser value) {
        cache.put(key, value);
    }

    /**
     * 从内存中取出数据
     *
     * @param key
     * @return
     */
    CacheUser get(String key) {
        return cache.get(key);
    }

}
