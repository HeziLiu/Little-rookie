package com.example.he.broadcastbestpractice;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * 登录界面，可将信息保存到SharedPreferences中
 * Created by he on 2016/4/10.
 */
public class MainActivity extends BaseActivity {
    private ForceOfflineReceiver forceOfflineReceiver;//动态注册
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        Button button= (Button) findViewById(R.id.force_offline);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent("FORCE_OFFLINE");
                sendBroadcast(intent);//发送广播
            }
        });

        /**
         * 动态注册，依附于活动MainActivity
         */
        IntentFilter filter=new IntentFilter("FORCE_OFFLINE");
        forceOfflineReceiver=new ForceOfflineReceiver();
        registerReceiver(forceOfflineReceiver,filter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(forceOfflineReceiver);
    }
}
