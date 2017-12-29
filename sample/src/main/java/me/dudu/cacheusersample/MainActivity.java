package me.dudu.cacheusersample;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import me.dudu.cacheuser.CacheUserLoader;
import me.dudu.cacheuser.db.CacheUser;
import me.dudu.cacheuser.interf.OnBlockingProcess;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private List<String> identifiers = new ArrayList<>();//初始数据
    private List<CacheUser> cacheUsers = new ArrayList<>();//模拟网络数据
    private ContactAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView list = findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ContactAdapter(this, identifiers);
        list.setAdapter(adapter);

        initData();
        initLoader();
    }

    private void initData() {
        for (int i = 0; i < 100; i++) {
            CacheUser cacheUser = new CacheUser();
            cacheUser.setUserIdentifier(i + "");
            cacheUser.setNickname("测试" + i);
            cacheUser.setAvatar("");
            cacheUser.setUserId(i);
            cacheUser.setParams("");
            cacheUsers.add(cacheUser);
            identifiers.add(i + "");
        }
        adapter.notifyDataSetChanged();
    }

    private void initLoader() {
        CacheUserLoader.getInstance().setOnBlockingProcess(new OnBlockingProcess() {
            @Override
            public CacheUser onBlockingProcess(String identifier) {
                //模拟网络同步请求
                Log.e(TAG, "开始请求: " + identifier);
                SystemClock.sleep(1000);
                CacheUser cacheUser = null;
                for (CacheUser user : cacheUsers) {
                    if (identifier.equals(user.getUserIdentifier())) {
                        cacheUser = user;
                        break;
                    }
                }
                Log.e(TAG, "结束请求: " + identifier);
                return cacheUser;
            }
        });
    }

}
