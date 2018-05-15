package com.example.he.servicebestpractice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

/**
 * 使用服务和广播接收器实现后台定时任务
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout);
        Intent intent=new Intent(this,LongRunningService.class);
        startService(intent);//开启服务
    }
}
