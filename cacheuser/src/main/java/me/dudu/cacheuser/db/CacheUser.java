package me.dudu.cacheuser.db;

/**
 * Author : zhouyx
 * Date   : 2017/11/16
 * Description : 缓存对象
 */
public class CacheUser {

    private String nickname;
    private String avatar;
    private long userId;
    private String userIdentifier;
    private long cacheTime;
    private String params;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUserIdentifier() {
        return userIdentifier;
    }

    public void setUserIdentifier(String userIdentifier) {
        this.userIdentifier = userIdentifier;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getCacheTime() {
        return cacheTime;
    }

    public void setCacheTime(long cacheTime) {
        this.cacheTime = cacheTime;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public int getSize() {
        return toString().getBytes().length;
    }

    @Override
    public String toString() {
        return "CacheUser{" +
                "nickname='" + nickname + '\'' +
                ", avatar='" + avatar + '\'' +
                ", userId=" + userId +
                ", userIdentifier='" + userIdentifier + '\'' +
                ", cacheTime=" + cacheTime +
                ", params='" + params + '\'' +
                '}';
    }

}
