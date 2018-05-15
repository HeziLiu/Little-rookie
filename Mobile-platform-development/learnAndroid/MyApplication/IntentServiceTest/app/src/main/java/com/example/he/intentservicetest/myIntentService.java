package com.example.he.intentservicetest;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by he on 2017/1/7.
 */

public class myIntentService extends IntentService {
    public static final String TAG="myIntentService";

    public myIntentService() {
        super(TAG);
    }



    @Override
    protected void onHandleIntent(Intent intent) {
        String info=intent.getStringExtra("action");
        Log.d(TAG, "onHandleIntent: "+info);
    }


}
