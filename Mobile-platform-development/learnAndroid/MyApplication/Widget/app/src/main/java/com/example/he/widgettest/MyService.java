package com.example.he.widgettest;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 该服务用于更新widget
 */
public class MyService extends Service {

    private Timer timer;
    private SimpleDateFormat date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                AppWidgetManager manager=AppWidgetManager.getInstance(MyService.this);
                String time=date.format(new Date());
                ComponentName cn=new ComponentName(getApplicationContext(),myAppWidget.class);
                myAppWidget.updateAppWidget(MyService.this,manager,cn,time);
            }
        }, 0, 1000);

    }




    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer=null;
    }
}
