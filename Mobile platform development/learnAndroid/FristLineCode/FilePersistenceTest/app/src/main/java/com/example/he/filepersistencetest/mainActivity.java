package com.example.he.filepersistencetest;
/**
 * 把数据保存到文件中和从文件中读取数据
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class mainActivity extends AppCompatActivity {
    private EditText edit_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        edit_text = (EditText) findViewById(R.id.edit_text);
        Button save = (Button) findViewById(R.id.save);
        final Button open = (Button) findViewById(R.id.open);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = edit_text.getText().toString();
                saveData(data);
                Toast.makeText(mainActivity.this, "数据保存成功", Toast.LENGTH_SHORT).show();
            }
        });

        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = openData();
                edit_text.setText(data);
            }
        });


    }

    //储存数据
    public void saveData(String data) {
        BufferedWriter writer = null;
        try { //覆盖写数据
            FileOutputStream fileOutputStream = openFileOutput("data", Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
            writer.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //读取数据
    public String openData() {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;

        try {
            FileInputStream inputStream = openFileInput("data");
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String data = null;
            while ((data = reader.readLine()) != null) {
                sb.append(data);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (reader!=null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();
    }


}
