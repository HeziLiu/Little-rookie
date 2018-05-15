package com.example.he.fristandroid;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by he on 2016/3/15.
 * 两个Activity通过Intent相互交换数据，在Activity中用一个Button 选择是否接受数据，不能直接写在onCreate中否则在Activity1 中获
 * 取从Activity2发送的数据时会崩溃，原因是因为在Activity中会通过Intent再创建一个Activity1的界面 而Activity1在内存中是存在的。
 */
public class FirstActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);      //隐藏标题栏 必须在加载布局之前
        setContentView(R.layout.firstlayout);//加载布局
        Button button1 = (Button) findViewById(R.id.button1);//获取布局文件中定义的元素
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.first_button3);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(FirstActivity.this, "即将跳转", Toast.LENGTH_SHORT);
                //显示
                toast.show();
                //设置显示位置
                toast.setGravity(Gravity.CENTER, 10, 10);

                Intent intent = new Intent(Intent.ACTION_VIEW);//Intent.ACTION_VIEW为Android系统的内置动作
                intent.setData(Uri.parse("http://www.baidu.com"));//解析URL
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FirstActivity.this, "即将跳转", Toast.LENGTH_SHORT).show();
//                向下一个活动传递数据
                String data = "Hello SecondActivity";
                String data3 = "hello ThirdActivity";
//                显示Intent
                //数据传给SecondActivity.class，并从SecondActivity得到数据， 程序崩溃
//                Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
                //将数据给ThirdActivity,并从SecondActivity得到数据，测试程序是否会崩溃,测试成功，程序不会崩溃
                Intent intent = new Intent(FirstActivity.this, ThirdActivity.class);

                intent.putExtra("extra_action", data3);//传递数据
                startActivity(intent);

//                //隐式intent,参数指定action名
//                Intent intent = new Intent("Action_Start");
//                //添加category如果没有在AndroidManifest.xml中SecondActivity的intent-filter 中申明添加的Category就会报错
//                intent.addCategory("Second_category");
//                startActivity(intent);


                //当SecondActivity退出时从中获取数据
                Intent intent2 = new Intent(FirstActivity.this, SecondActivity.class);
                startActivityForResult(intent2, 1); //如果requestCode为负数 则 效果和startActivity()一样

            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"));
                startActivity(intent);
            }
        });
    }

    //获取返回数据
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    String data1 = data.getStringExtra("data_return");
                    Log.i("FirstActivity", data1);
                }
                Log.i("FirstActivity", "错误");
                break;
        }
    }

    @Override   //创建菜单选项
    public boolean onCreateOptionsMenu(Menu menu) {
        //给当前活动创建菜单
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override//获取菜单响应
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_item:
                //显示提醒
                Toast.makeText(FirstActivity.this, "you clicked add item", Toast.LENGTH_SHORT).show();
                break;
            case R.id.remove_item:
                Toast.makeText(FirstActivity.this, "you clicked remove item", Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }

}
