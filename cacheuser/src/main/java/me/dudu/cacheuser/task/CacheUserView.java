package me.dudu.cacheuser.task;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.lang.ref.WeakReference;

/**
 * Author : zhouyx
 * Date   : 2017/11/16
 * Description :
 */
public class CacheUserView extends View {

    private WeakReference<BaseTask> taskWeakReference;

    public CacheUserView(Context context) {
        super(context);
    }

    public CacheUserView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CacheUserView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setTask(BaseTask task) {
        this.taskWeakReference = new WeakReference<>(task);
    }

    public void clearTask() {
        taskWeakReference.clear();
        taskWeakReference = null;
    }

    public BaseTask getTask() {
        return taskWeakReference == null ? null : taskWeakReference.get();
    }

}
