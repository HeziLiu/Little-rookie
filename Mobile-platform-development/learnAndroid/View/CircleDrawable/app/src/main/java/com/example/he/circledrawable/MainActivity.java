package com.example.he.circledrawable;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

/**
 * 自定义Drawable实现圆形图片
 */


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imageView= (ImageView) findViewById(R.id.image_V);
        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.nav_icon);
        imageView.setImageDrawable(new CricleDrawable(bitmap));

    }
}
