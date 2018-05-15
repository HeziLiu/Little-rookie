package com.example.he.asyncloding;

import android.app.Application;
import android.content.Context;

/**
 * 用于获取全局Context,注意要修改AndroidManifest.xml中的相关信息,一定要使用完成的包名
 * Created by he on 2016/11/28.
 */

public class myApplication extends Application {
    private  static Context context;

    @Override
    public void onCreate() {
        context=getApplicationContext();
    }

    public  static Context getContext(){
        return  context;
    }

}
