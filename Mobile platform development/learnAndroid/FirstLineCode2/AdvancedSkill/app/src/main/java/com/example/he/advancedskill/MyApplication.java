package com.example.he.advancedskill;

import android.app.Application;
import android.content.Context;

/**
 * 定制Application管理程序中一些全局状态的信息
 * 要修该AndroidManifest.xml文件的<application>
 *     如下：
 * Created by he on 2016/5/14.
 */
public class MyApplication extends Application {
    private  static Context context;

    @Override
    public void onCreate() {
        context=getApplicationContext();
    }

    public  static Context getContext(){
        return  context;
    }
}
