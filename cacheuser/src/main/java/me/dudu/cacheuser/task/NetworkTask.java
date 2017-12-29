package me.dudu.cacheuser.task;

import android.support.annotation.NonNull;

import me.dudu.cacheuser.db.CacheUser;
import me.dudu.cacheuser.interf.OnBlockingProcess;
import me.dudu.cacheuser.interf.OnResultCallback;

/**
 * Author : zhouyx
 * Date   : 2017/12/29
 * Description : 网络请求线程
 */
public class NetworkTask extends BaseTask {

    private OnBlockingProcess onBlockingProcess;

    public NetworkTask(@NonNull CacheUserView view, @NonNull OnResultCallback onResultCallback, @NonNull OnBlockingProcess onBlockingProcess) {
        super(view, onResultCallback);
        this.onBlockingProcess = onBlockingProcess;
    }

    @Override
    protected CacheUser doInBackground(String... strings) {
        if (getAttachedView() == null) {
            return null;
        }
        CacheUser cacheUser = onBlockingProcess.onBlockingProcess(strings[0]);
        if (cacheUser != null) {
            cacheUserDao.saveCacheUser(cacheUser);
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
