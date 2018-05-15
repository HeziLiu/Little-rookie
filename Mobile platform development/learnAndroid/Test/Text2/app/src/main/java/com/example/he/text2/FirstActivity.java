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
public class FirstActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstlayout);
        Button first_button =(Button)findViewById(R.id.first_button);
        Button second_button=(Button)findViewById(R.id.second_button);

        //向SecondActivity传数据的按钮
        first_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(FirstActivity.this,SecondActivity.class);
                intent.putExtra("first_action","hello secondActivity");
                startActivity(intent);
            }
        });

        //从SecondActivity得到数据的按钮
        second_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(FirstActivity.this,SecondActivity.class);
                startActivityForResult(intent,1);
            }
        });


    }



    //读取返回信息
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case  1:
                if (resultCode==RESULT_OK){
                    String s=data.getStringExtra("data_return");
                    Toast.makeText(FirstActivity.this, s, Toast.LENGTH_SHORT).show();
//                    Log.i("FirstActivity",s);
                }
                break;
        }
    }
}
