package com.example.he.firstandroid2;

import android.app.Activity;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

/**
 * Created by he on 2016/3/22.
 *
 * 体验活动的生命周期及使用Bundle保存临时数据防止活动被销毁时数据丢失。
 *
 *
 */
public class mainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(Tag.CREATOR.toString(),"onCreate");
        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题
        setContentView(R.layout.main_layout);
        //取出bundle中的数据
        if (savedInstanceState!=null){
            String data=savedInstanceState.getString("data_key");
//            Toast.makeText(mainActivity.this, data, Toast.LENGTH_SHORT).show();
            Log.i("mainActivity",data);
        }
        Button button1 =(Button)findViewById(R.id.normal_button);
        Button button2 = (Button) findViewById(R.id.dialog_button);


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainActivity.this,normalActivity.class);
                startActivity(intent);

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(mainActivity.this,dialogActivity.class);
                startActivity(intent);
            }
        });
    }

    //将临时数据保存到Bundle中,防止Activity被销毁时数据得不到保存
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String data="something you need to try";
        outState.putString("data_key",data);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(Tag.CREATOR.toString(),"onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("mainActivity","onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("mainActivity","onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("mainActivity","onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(Tag.CREATOR.toString(),"onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("mainActivity","onRestart");
    }
}
