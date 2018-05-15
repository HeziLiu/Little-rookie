package com.example.he.firstandroid2;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

/**
 * Created by he on 2016/3/22.
 */
public class normalActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.normal_layout);


    }
}
