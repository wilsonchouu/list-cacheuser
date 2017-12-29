package me.dudu.cacheuser;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import me.dudu.cacheuser.db.CacheUser;
import me.dudu.cacheuser.interf.OnBlockingProcess;
import me.dudu.cacheuser.interf.OnResultCallback;
import me.dudu.cacheuser.task.CacheUserView;
import me.dudu.cacheuser.task.LocalTask;
import me.dudu.cacheuser.task.NetworkTask;

/**
 * Author : zhouyx
 * Date   : 2017/11/16
 * Description : 缓存加载器
 */
public class CacheUserLoader {

    private static CacheUserLoader ourInstance;
    private final ExecutorService localExecutor = Executors.newCachedThreadPool();//本地数据请求线程池
    private final ExecutorService networkExecutor = Executors.newFixedThreadPool(2);//网络数据请求线程池
    private OnBlockingProcess onBlockingProcess;
    private long expireTime = 2 * 60 * 60 * 1000;//默认缓存过期失效时间2小时

    private CacheUserLoader() {
    }

    public static synchronized CacheUserLoader getInstance() {
        if (ourInstance == null) {
            ourInstance = new CacheUserLoader();
        }
        return ourInstance;
    }

    /**
     * 设置同步回调
     *
     * @param onBlockingProcess
     */
    public void setOnBlockingProcess(OnBlockingProcess onBlockingProcess) {
        this.onBlockingProcess = onBlockingProcess;
    }

    /**
     * 加载缓存数据，先访问本地缓存，如不命中则访问网络数据
     *
     * @param view
     * @param identifier
     * @param onResultCallback
     */
    public void load(final CacheUserView view, final String identifier, final OnResultCallback onResultCallback) {
        if (view == null || identifier == null || onResultCallback == null) {
            return;
        }
        OnResultCallback localCallback = new OnResultCallback() {
            @Override
            public void onResult(CacheUser cacheUser) {
                if (cacheUser != null) {
                    onResultCallback.onResult(cacheUser);
                    view.clearTask();
                    return;
                }
                OnResultCallback networkCallback = new OnResultCallback() {
                    @Override
                    public void onResult(CacheUser cacheUser) {
                        onResultCallback.onResult(cacheUser);
                        view.clearTask();
                    }
                };
                NetworkTask networkTask = new NetworkTask(view, networkCallback, onBlockingProcess);
                view.setTask(networkTask);
                networkTask.executeOnExecutor(networkExecutor, identifier);
            }
        };
        LocalTask localTask = new LocalTask(view, localCallback);
        view.setTask(localTask);
        localTask.executeOnExecutor(localExecutor, identifier);
    }

    /**
     * 设置缓存时间（单位：毫秒）
     *
     * @param expireTime
     */
    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    /**
     * 缓存数据是否过时
     *
     * @return true:过时;false:未过时;
     */
    public boolean isExpire(long cacheTime) {
        return System.currentTimeMillis() - cacheTime > expireTime;
    }

}
