package com.example.he.fristandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

/**
 * Created by he on 2016/3/19.
 * 响应打开一个网页
 */
public class ThirdActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.third_layout);

        Intent intent = getIntent();
        String data = intent.getStringExtra("extra_action");//读取数据
        Log.i("SecondActivity", data);

    }
}
