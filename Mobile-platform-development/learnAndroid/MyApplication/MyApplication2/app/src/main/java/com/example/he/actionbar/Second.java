package com.example.he.actionbar;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by he on 2016/10/19.
 */

public class Second extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.second_activity);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
