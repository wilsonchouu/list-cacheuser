package me.dudu.cacheuser.db;

import android.content.Context;

/**
 * Author : zhouyx
 * Date   : 2017/11/16
 * Description : 数据访问层
 */
public class CacheUserDao {

    static final String TABLE_NAME = "dudu_cache_user";
    static final String COLUMN_NAME_NICKNAME = "nickname";//业务昵称
    static final String COLUMN_NAME_AVATAR = "avatar";//业务头像url
    static final String COLUMN_NAME_USER_ID = "user_id";//业务用户id
    static final String COLUMN_NAME_USER_IDENTIFIER = "user_identifier";//依赖系统用户id
    static final String COLUMN_NAME_CACHE_TIME = "cache_time";//缓存时间
    static final String COLUMN_NAME_PARAMS = "params";//额外数据字段（json格式）

    static final String CACHE_USER_TABLE_CREATE = "CREATE TABLE "
            + CacheUserDao.TABLE_NAME + " ("
            + CacheUserDao.COLUMN_NAME_NICKNAME + " NTEXT, "
            + CacheUserDao.COLUMN_NAME_AVATAR + " TEXT, "
            + CacheUserDao.COLUMN_NAME_USER_ID + " INTEGER, "
            + CacheUserDao.COLUMN_NAME_USER_IDENTIFIER + " TEXT PRIMARY KEY, "
            + CacheUserDao.COLUMN_NAME_CACHE_TIME + " INTEGER, "
            + CacheUserDao.COLUMN_NAME_PARAMS + " TEXT); ";

    private static CacheUserDao ourInstance;
    private DbManager dbManager;
    private MemoryManager mmManager;

    private CacheUserDao(Context context) {
        dbManager = DbManager.getInstance(context);
        mmManager = MemoryManager.getInstance();
    }

    public static synchronized CacheUserDao getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new CacheUserDao(context.getApplicationContext());
        }
        return ourInstance;
    }

    /**
     * 获取本地缓存
     *
     * @param key
     * @return
     */
    public synchronized CacheUser getCacheUser(String key) {
        CacheUser cacheUser = mmManager.get(key);
        if (cacheUser == null) {
            cacheUser = dbManager.get(key);
            if (cacheUser != null) {
                mmManager.put(key, cacheUser);
            }
        }
        return cacheUser;
    }

    /**
     * 保存缓存数据
     *
     * @param value
     */
    public synchronized void saveCacheUser(CacheUser value) {
        value.setCacheTime(System.currentTimeMillis());
        mmManager.put(value.getUserIdentifier(), value);
        dbManager.put(value.getUserIdentifier(), value);
    }

}
