package com.example.he.actionbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by he on 2016/5/21.
 */
public class secondActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_layout);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // 为ActionBar扩展菜单项
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.activity_action, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
}
