package com.example.he.gesturetest;

import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * 手势识别
 */

public class MainActivity extends AppCompatActivity {
    private ImageView image1;
    private GestureDetector detector;

    private GestureOverlayView overlayView;
    private ImageView image2;


    class myGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            if (e1.getX() - e2.getX() > 50) {
                Toast.makeText(MainActivity.this, "从右向左滑动", Toast.LENGTH_SHORT).show();
            } else if (e1.getX() - e2.getX() < 50) {
                Toast.makeText(MainActivity.this, "从左向右滑动", Toast.LENGTH_SHORT).show();
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image1 = (ImageView) findViewById(R.id.image1);
        detector = new GestureDetector(this, new myGestureDetector());
        image1.setClickable(true);

        image1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                detector.onTouchEvent(event);
                return true;
            }
        });


        overlayView = (GestureOverlayView) findViewById(R.id.gestureOver);
        image2= (ImageView) findViewById(R.id.image2);
        /**
         * 1、找到预设的手势文件
         * 2、加载手势文件
         * 3、识别 匹配
         */
        final GestureLibrary library = GestureLibraries.fromRawResource(this, R.raw.gestures);
        library.load();//加载手势文件

        //添加手势执行监听器
        overlayView.addOnGesturePerformedListener(new GestureOverlayView.OnGesturePerformedListener() {
            @Override
            public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {

                //读取手势库中的内容并识别
                ArrayList<Prediction> list=library.recognize(gesture);
                Prediction prediction=list.get(0);
                if (prediction.score>5.0){

                    if (prediction.name.equals("next"))
                        Toast.makeText(MainActivity.this,"匹配到向后的手势",Toast.LENGTH_SHORT).show();
                    if (prediction.name.equals("pervious"))
                        Toast.makeText(MainActivity.this,"匹配到向前的手势",Toast.LENGTH_SHORT).show();
                }else{
                    Snackbar.make(image2,"未找到该手势",Snackbar.LENGTH_SHORT).show();
                }

            }
        });

    }
}
