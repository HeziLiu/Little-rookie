package com.ifly.shendong.windowmanagertest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    private WindowManager manager;
    private Button create;
    private WindowManager.LayoutParams windowLayoutParams;
    private ImageView image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }


    private void init() {
        create = (Button) findViewById(R.id.create);
        create.setOnClickListener(this);
        manager = (WindowManager) getSystemService(WINDOW_SERVICE);


    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.create) {
            image = new ImageView(this);
            image.setBackgroundResource(R.mipmap.ic_launcher);
            windowLayoutParams = new WindowManager.LayoutParams();
            windowLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
            windowLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
            windowLayoutParams.gravity = Gravity.TOP | Gravity.LEFT;
            windowLayoutParams.x = 100;
            windowLayoutParams.y = 300;
            windowLayoutParams.width = FrameLayout.LayoutParams.WRAP_CONTENT;
            windowLayoutParams.height = FrameLayout.LayoutParams.WRAP_CONTENT;
            image.setOnTouchListener(this);
            manager.addView(image, windowLayoutParams);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                int x = (int) event.getRawX();
                int y = (int) event.getRawY();
                windowLayoutParams.x = x;
                windowLayoutParams.y = y;
                manager.updateViewLayout(image, windowLayoutParams);
                break;
        }
        return true;
    }
}
