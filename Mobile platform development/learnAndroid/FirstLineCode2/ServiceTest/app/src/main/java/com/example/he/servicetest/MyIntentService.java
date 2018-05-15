package com.example.he.servicetest;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * IntentService可以非常简单的创建一个异步的，会自动停止的服务
 * Created by he on 2016/4/30.
 */
public class MyIntentService  extends IntentService{

    public MyIntentService(){
        super("MyIntentService");//调用父类构造器
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //显示当前线程的ID
        Toast.makeText(this,"Thread id is"+Thread.currentThread().getId(),Toast.LENGTH_SHORT).show();
        Log.i("myIntentService","Thread id is"+Thread.currentThread().getId());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
