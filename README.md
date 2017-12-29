### 前言
一套缓存框架。  
应用场景：一般可用于开发IM聊天室或者公共聊天窗时获取新加入联系人的个人信息（还可用于类似的所有场景）。  
数据路径：先从内存查找，内存不命中的情况下查找数据库，数据库查无数据情况下请求服务器，服务器返回数据后同步本地数据库及内存。  
内存策略：LRU  
数据库：SQLite

### 项目地址
[项目地址](https://github.com/wilsonchouu/list-cacheuser)

### 如何使用（详细可看demo实现）
* Step1：CacheUserLoader.getInstance().setOnBlockingProcess(xxx);  
注册服务器数据获取接口，以同步方式返回数据
* Step2：在适配器布局声明一个CacheUserView用于绑定数据
* Step3：CacheUserLoader.getInstance().load(xxx);  
在适配器渲染数据时调用加载方法，回调方法内编写相应的逻辑

### 集成使用
[![](https://www.jitpack.io/v/wilsonchouu/list-cacheuser.svg)](https://www.jitpack.io/#wilsonchouu/list-cacheuser)  
注：将“版本号”替换成上面的数字
##### Gradle:
```
allprojects {
    repositories {
        ...
        maven { url 'https://www.jitpack.io' }
    }
}
```
```
dependencies {
    compile 'com.github.wilsonchouu.list-cacheuser:版本号'
}
```

### TODO
* 合并请求

### After
如果有什么问题或建议可以提[ISSUE](https://github.com/wilsonchouu/list-cacheuser/issues)，我会尽量修改更新代码，感激不尽，可以的话就点个Star或Fork支持一下吧~

