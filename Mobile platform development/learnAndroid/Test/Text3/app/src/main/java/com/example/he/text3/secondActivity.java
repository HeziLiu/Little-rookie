package com.example.he.text3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by he on 2016/3/22.
 */
public class secondActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String data = intent.getBundleExtra("intent_data").getString("bundle_data");
        Toast.makeText(secondActivity.this, data, Toast.LENGTH_SHORT).show();
    }
}
