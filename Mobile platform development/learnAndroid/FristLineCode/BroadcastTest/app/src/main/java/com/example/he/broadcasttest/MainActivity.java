package com.example.he.broadcasttest;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
    private netWorkChange netWorkChange;
    private IntentFilter intentFilter;
    private IntentFilter intentFilter2;

    private LocalBroadcastManager manager;
    private LocalReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_layout);
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        netWorkChange = new netWorkChange();
        registerReceiver(netWorkChange, intentFilter);//注册广播

        Button button = (Button) findViewById(R.id.button);
        Button button2 = (Button) findViewById(R.id.button2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("my_broadcast");
                sendOrderedBroadcast(intent, null);
            }
        });


        manager = LocalBroadcastManager.getInstance(this);//获取实例
        intentFilter2 = new IntentFilter();
        intentFilter2.addAction("LOCAL_RECEIVER");
        receiver = new LocalReceiver();
        manager.registerReceiver(receiver, intentFilter2);//注册本地监听器

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("LOCAL_RECEIVER");
                manager.sendBroadcast(intent);//发送本地广播
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(netWorkChange);
        manager.unregisterReceiver(receiver);
    }
}

//网络变化接收器
class netWorkChange extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            Toast.makeText(context, "网络已连接", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "网络未连接", Toast.LENGTH_SHORT).show();
        }

    }
}

//本地广播接收器
class LocalReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "this is local receiver", Toast.LENGTH_SHORT).show();
    }
}
