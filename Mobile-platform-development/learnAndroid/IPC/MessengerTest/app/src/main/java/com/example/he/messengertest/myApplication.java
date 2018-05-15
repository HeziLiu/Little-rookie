package com.example.he.messengertest;

import android.app.Application;
import android.content.Context;

/**
 * Created by he on 2016/12/26.
 */

public class myApplication extends Application {
    private static   Context mContext;

    @Override
    public void onCreate() {
        mContext=getApplicationContext();
        super.onCreate();
    }

    public  static Context getContext(){
        return mContext;
    }

}
