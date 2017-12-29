package me.dudu.cacheuser.interf;

import me.dudu.cacheuser.db.CacheUser;

/**
 * Author : zhouyx
 * Date   : 2017/11/16
 * Description : 网络数据请求同步回调
 */
public interface OnBlockingProcess {
    CacheUser onBlockingProcess(String identifier);
}
