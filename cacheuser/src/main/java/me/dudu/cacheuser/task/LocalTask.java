package me.dudu.cacheuser.task;

import android.support.annotation.NonNull;

import me.dudu.cacheuser.CacheUserLoader;
import me.dudu.cacheuser.db.CacheUser;
import me.dudu.cacheuser.interf.OnResultCallback;

/**
 * Author : zhouyx
 * Date   : 2017/12/29
 * Description : 本地缓存线程
 */
public class LocalTask extends BaseTask {

    public LocalTask(@NonNull CacheUserView view, @NonNull OnResultCallback onResultCallback) {
        super(view, onResultCallback);
    }

    @Override
    protected CacheUser doInBackground(String... strings) {
        if (getAttachedView() == null) {
            return null;
        }
        CacheUser cacheUser = cacheUserDao.getCacheUser(strings[0]);
        if (cacheUser != null && cacheUser.getNickname() != null && !CacheUserLoader.getInstance().isExpire(cacheUser.getCacheTime())) {
            return cacheUser;
        }
        return null;
    }

    @Override
    protected void onPostExecute(CacheUser cacheUser) {
        super.onPostExecute(cacheUser);
        if (getAttachedView() == null) {
            return;
        }
        onResultCallback.onResult(cacheUser);
    }

}
