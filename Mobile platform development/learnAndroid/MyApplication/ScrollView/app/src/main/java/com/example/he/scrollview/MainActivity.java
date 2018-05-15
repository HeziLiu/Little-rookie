package com.example.he.scrollview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ScrollView scrollView;
    private TextView textView;
    private  int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scrollView = (ScrollView) findViewById(R.id.sv);
        textView = (TextView) findViewById(R.id.tv);
        //设置监听
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_UP:
//                    case MotionEvent.ACTION_MOVE:

                if (scrollView.getScrollY() == 0) {

                    Toast.makeText(MainActivity.this, "滑动到了顶部" + i++, Toast.LENGTH_SHORT).show();
                    return false;
                }
                //滑动到了底部，追加信息
                if (scrollView.getChildAt(0).getMeasuredHeight() <= scrollView.getHeight() + scrollView.getScrollY())
                    textView.append("xxxxxxxxxxxxxx");
                        break;
                }
                return false;
            }
        });
    }
}
