package com.example.he.widgettest;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class myAppWidget extends AppWidgetProvider {
    private static String time;
  private  static   ComponentName componentName;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                ComponentName cn, String t) {
        time=t;
        componentName=cn;
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.my_app_widget);
        views.setTextViewText(R.id.appwidget_text, time);
        appWidgetManager.updateAppWidget(cn, views);
    }

    //刷新widget
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
//            updateAppWidget(context, appWidgetManager, componentName,time);
        super.onUpdate(context,appWidgetManager,appWidgetIds);
    }

    //第一个widget添加到屏幕上执行
    @Override
    public void onEnabled(Context context) {
        context.startService(new Intent(context,MyService.class));
        // Enter relevant functionality for when the first widget is created
    }


    //最后一个widget从屏幕中移除
    @Override
    public void onDisabled(Context context) {
       super.onDisabled(context);
        context.stopService(new Intent(context,MyService.class));
    }
}

