package com.example.he.intentservicetest;

import android.app.Application;
import android.content.Context;

/**
 * Created by he on 2017/1/7.
 */

public class myApplication  extends Application{
    public static Context mContext;

    @Override
    public void onCreate() {
        mContext=getApplicationContext();
        super.onCreate();
    }

    public static Context getmContext() {
        return mContext;
    }
}
