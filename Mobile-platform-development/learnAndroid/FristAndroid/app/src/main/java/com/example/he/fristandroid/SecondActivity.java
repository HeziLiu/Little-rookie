package com.example.he.fristandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by he on 2016/3/16.
 */
//第二个活动
public class SecondActivity extends Activity {
     //修改
    Intent intent ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题栏
        setContentView(R.layout.second_layout);//加载布局

        //修改
//        intent = getIntent();
//        String data = intent.getStringExtra("extra_action");//读取数据
//        Log.i("SecondActivity", data);




        Button button = (Button) findViewById(R.id.second_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SecondActivity.this, "this is SecondActivity button", Toast.LENGTH_SHORT).show();

                //修改
                intent=new Intent();
//                intent.removeExtra("extra_action");

                intent.putExtra("data_return", "hello firstActivity");
                setResult(RESULT_OK, intent);//向上一活动返回数据  很重要
                finish();
            }
        });


    }

    //重写 onBackPressed 方法 防止直接点返回键导致数据未传递
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("data_return", "hello firstActivity");
        setResult(RESULT_OK, intent);//向上一活动返回数据  很重要
        finish();
    }
}
