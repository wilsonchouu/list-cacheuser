package me.dudu.cacheuser.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import static me.dudu.cacheuser.db.CacheUserDao.COLUMN_NAME_AVATAR;
import static me.dudu.cacheuser.db.CacheUserDao.COLUMN_NAME_CACHE_TIME;
import static me.dudu.cacheuser.db.CacheUserDao.COLUMN_NAME_NICKNAME;
import static me.dudu.cacheuser.db.CacheUserDao.COLUMN_NAME_PARAMS;
import static me.dudu.cacheuser.db.CacheUserDao.COLUMN_NAME_USER_ID;
import static me.dudu.cacheuser.db.CacheUserDao.COLUMN_NAME_USER_IDENTIFIER;
import static me.dudu.cacheuser.db.CacheUserDao.TABLE_NAME;

/**
 * Author : zhouyx
 * Date   : 2017/11/16
 * Description : 本地数据库缓存
 */
class DbManager {

    private static DbManager ourInstance;
    private DbOpenHelper dbHelper;

    private DbManager(Context context) {
        dbHelper = DbOpenHelper.getInstance(context);
    }

    static synchronized DbManager getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new DbManager(context.getApplicationContext());
        }
        return ourInstance;
    }

    /**
     * 从数据库中取出数据
     *
     * @param key
     * @return
     */
    synchronized CacheUser get(String key) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        if (!database.isOpen()) {
            return null;
        }
        Cursor cursor = database.query(TABLE_NAME, new String[]{COLUMN_NAME_NICKNAME, COLUMN_NAME_AVATAR,
                        COLUMN_NAME_USER_ID, COLUMN_NAME_USER_IDENTIFIER, COLUMN_NAME_CACHE_TIME,
                        COLUMN_NAME_PARAMS}, COLUMN_NAME_USER_IDENTIFIER + "=?",
                new String[]{key}, null, null, null);
        if (cursor.moveToLast()) {
            CacheUser cacheUser = new CacheUser();
            String nickname = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_NICKNAME));
            String avatar = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_AVATAR));
            long userId = cursor.getLong(cursor.getColumnIndex(COLUMN_NAME_USER_ID));
            String userIdentifier = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_USER_IDENTIFIER));
            long cacheTime = cursor.getLong(cursor.getColumnIndex(COLUMN_NAME_CACHE_TIME));
            String params = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_PARAMS));

            cacheUser.setNickname(nickname);
            cacheUser.setAvatar(avatar);
            cacheUser.setUserId(userId);
            cacheUser.setUserIdentifier(userIdentifier);
            cacheUser.setCacheTime(cacheTime);
            cacheUser.setParams(params);

            cursor.close();
            return cacheUser;
        }
        cursor.close();
        return null;
    }

    /**
     * 存入数据到数据库
     *
     * @param key
     * @param value
     */
    synchronized void put(String key, CacheUser value) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        if (!database.isOpen()) {
            return;
        }
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_USER_IDENTIFIER, key);
        values.put(COLUMN_NAME_CACHE_TIME, value.getCacheTime());
        values.put(COLUMN_NAME_USER_ID, value.getUserId());
        if (value.getAvatar() != null) {
            values.put(COLUMN_NAME_AVATAR, value.getAvatar());
        }
        if (value.getNickname() != null) {
            values.put(COLUMN_NAME_NICKNAME, value.getNickname());
        }
        if (value.getParams() != null) {
            values.put(COLUMN_NAME_PARAMS, value.getParams());
        }
        database.replace(TABLE_NAME, null, values);
    }

}
