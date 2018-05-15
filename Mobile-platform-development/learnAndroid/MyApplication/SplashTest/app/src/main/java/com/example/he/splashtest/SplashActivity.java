package com.example.he.splashtest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * splash
 * Created by he on 2017/1/24.
 */

public class SplashActivity extends AppCompatActivity {

    private static final int OK = 1;
    private static final String TAG = "splashActivity";
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == OK) {
                Log.d(TAG, "handleMessage: ");
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setClass(getApplication(), ViewPagerActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity_layout);
        handler.sendEmptyMessageDelayed(OK,2000);
        Log.d(TAG, "onCreate: ");
    }
}
