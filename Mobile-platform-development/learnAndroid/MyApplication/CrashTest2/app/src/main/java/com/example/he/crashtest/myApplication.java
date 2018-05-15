package com.example.he.crashtest;

import android.app.Application;

/**
 * Created by he on 2017/1/10.
 */

public class myApplication extends Application {
    private static myApplication sInstance;
    @Override
    public void onCreate() {
        sInstance=this;
        CrashHandler handler=CrashHandler.getInstance();
        handler.init(this);
        super.onCreate();
    }


    public static myApplication getsInstance(){
        return sInstance;
    }

}
