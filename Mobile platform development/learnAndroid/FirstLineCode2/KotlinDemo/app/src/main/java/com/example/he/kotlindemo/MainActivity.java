package com.example.he.kotlindemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * 展示Java类调用Kotlin类
 */
public class MainActivity extends AppCompatActivity {

    private TextView textV;
    private Button go;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        KotlinClass kotlinClass=new KotlinClass();
        int sum=kotlinClass.sum(10,60);
        textV= (TextView) findViewById(R.id.text_);
        textV.setText(""+sum);

        go= (Button) findViewById(R.id.go);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,SecondActivity.class);
                startActivity(intent);
            }
        });
    }
}
