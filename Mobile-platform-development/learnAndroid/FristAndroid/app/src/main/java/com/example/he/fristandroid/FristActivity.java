package com.example.he.fristandroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by he on 2016/3/15.
 */
public class FristActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      //隐藏标题栏 必须在加载布局之前
        setContentView(R.layout.fristlayout);//加载布局
        Button button1 = (Button) findViewById(R.id.button1);//获取布局文件中定义的元素
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(FristActivity.this, "这是第一个按钮", Toast.LENGTH_SHORT);
                //显示
                toast.show();
                //设置显示位置
                toast.setGravity(Gravity.CENTER, 10, 10);
            }
        });
    }
}
