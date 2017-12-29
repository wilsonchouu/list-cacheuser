package me.dudu.cacheuser.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static me.dudu.cacheuser.db.CacheUserDao.CACHE_USER_TABLE_CREATE;

/**
 * Author : zhouyx
 * Date   : 2017/11/16
 * Description : 本地数据库创建
 */
class DbOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "dudu_cache_user.db";
    private static final int DATABASE_VERSION = 1;
    private static DbOpenHelper ourInstance;

    private DbOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    static synchronized DbOpenHelper getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new DbOpenHelper(context.getApplicationContext());
        }
        return ourInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CACHE_USER_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
