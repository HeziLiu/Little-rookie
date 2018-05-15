package com.example.he.sharedpreferencestest;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends Activity {
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.edit_text);
        Button save_button = (Button) findViewById(R.id.save);
        Button open_button = (Button) findViewById(R.id.open);

        //保存数据
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = editText.getText().toString();
                Calendar calendar = Calendar.getInstance();
                SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                editor.putString("输入的内容", input);
                editor.putString("数据保存的时间", calendar.getTime().toString());
                editor.commit();//提交数据
                Toast.makeText(MainActivity.this, "数据保存成功", Toast.LENGTH_SHORT).show();
            }
        });

        //获取数据
        open_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
                editText.setText(sharedPreferences.getString("输入的内容", null));
                Toast.makeText(MainActivity.this, "数据保存时间" + sharedPreferences.getString("数据保存的时间", "未保存过数据"), Toast.LENGTH_SHORT).show();
            }
        });


    }
}
