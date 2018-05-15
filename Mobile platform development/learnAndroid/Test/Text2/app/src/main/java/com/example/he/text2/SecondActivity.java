package com.example.he.text2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by he on 2016/3/19.
 */
public class SecondActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secondlayout);

        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent();
                intent2.putExtra("data_return", "hello firstActivity");
                setResult(RESULT_OK, intent2);
                finish();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = getIntent();
                String data = intent1.getStringExtra("first_action");
                Toast.makeText(SecondActivity.this, data, Toast.LENGTH_SHORT).show();
//                Log.i("SecondActivity", data);
            }
        });

    }


    @Override
    public void onBackPressed() {
        Intent intent2 = new Intent();
        intent2.putExtra("data_return", "hello firstActivity");
        setResult(RESULT_OK, intent2);
        finish();
    }
}
