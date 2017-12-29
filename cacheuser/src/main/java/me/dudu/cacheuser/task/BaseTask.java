package me.dudu.cacheuser.task;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;

import me.dudu.cacheuser.db.CacheUser;
import me.dudu.cacheuser.db.CacheUserDao;
import me.dudu.cacheuser.interf.OnResultCallback;

/**
 * Author : zhouyx
 * Date   : 2017/12/29
 * Description :
 */
public abstract class BaseTask extends AsyncTask<String, Void, CacheUser> {

    CacheUserDao cacheUserDao;
    private WeakReference<CacheUserView> viewWeakReference;
    OnResultCallback onResultCallback;

    BaseTask(@NonNull CacheUserView view, @NonNull OnResultCallback onResultCallback) {
        this.cacheUserDao = CacheUserDao.getInstance(view.getContext());
        this.viewWeakReference = new WeakReference<>(view);
        this.onResultCallback = onResultCallback;
    }

    CacheUserView getAttachedView() {
        CacheUserView view = viewWeakReference.get();
        if (view == null) {
            return null;
        }
        BaseTask task = view.getTask();
        if (this == task) {
            return view;
        }
        return null;
    }

}
