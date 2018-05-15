package com.example.he.actionbar;

import android.app.Application;
import android.content.Context;

/**
 * Created by he on 2016/10/19.
 */

public class myApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }


}
