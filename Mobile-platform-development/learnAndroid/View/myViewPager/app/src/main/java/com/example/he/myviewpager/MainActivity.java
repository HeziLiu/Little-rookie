package com.example.he.myviewpager;

import android.content.Intent;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, myViewPager.class);
                startActivity(intent);
            }
        });

        TextView textView= (TextView) findViewById(R.id.ttt);
        TransitionDrawable drawable= (TransitionDrawable) textView.getBackground();
        drawable.startTransition(3000);

        ImageView imageView= (ImageView) findViewById(R.id.clip_image);
        ClipDrawable clip= (ClipDrawable) imageView.getBackground();
        clip.setLevel(8000);//设置等级（0～10000），表示剩余80%,裁剪了20%


    }
}
