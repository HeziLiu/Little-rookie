package com.example.he.notificationtest;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mian_layout);

        Button button = (Button) findViewById(R.id.send_notice);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取manager
                NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                Notification notification;
                Intent intent=new Intent(MainActivity.this,NotificationActivity.class);
                PendingIntent pendingIntent=PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);//获取pendingIntent实例
                Notification.Builder builder = new Notification.Builder(MainActivity.this).setSmallIcon(R.mipmap.ic_launcher).setTicker("this is ticker");
                //发送通知时手机震动,要申请权限 .setVibrate(vibrates)
//                long []vibrates={0,1000,1000,1000};
                 //setLights设置通知灯闪烁  setLights(Color.GREEN,1000,1000)
                //使用默认效果
                notification=builder.setContentTitle("this is text").setContentText("hello").setContentIntent(pendingIntent).setDefaults(Notification.DEFAULT_ALL).build();
                notification.flags=notification.FLAG_SHOW_LIGHTS;
                manager.notify(1,notification);//启动通知
            }
        });
    }
}
