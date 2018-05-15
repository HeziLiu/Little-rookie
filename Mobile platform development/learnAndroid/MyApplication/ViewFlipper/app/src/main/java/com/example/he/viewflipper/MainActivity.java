package com.example.he.viewflipper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class MainActivity extends AppCompatActivity {
    private float X;
    private ViewFlipper flipper;
    private int resId[] = {R.drawable.pic1, R.drawable.pic2, R.drawable.pic3, R.drawable.pic4};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        flipper = (ViewFlipper) findViewById(R.id.vf);
        for (int i : resId)
            flipper.addView(getImage(i));
//
//        flipper.setInAnimation(this,R.anim.left_in);
//        flipper.setOutAnimation(this,R.anim.right_out);
//        flipper.setFlipInterval(3000);
//        flipper.startFlipping();
    }

    public ImageView getImage(int resId) {
        ImageView view = new ImageView(this);
        view.setBackgroundResource(resId);
        return view;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                X = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                //向右滑动，看前一页
                if (event.getX() - X > 100) {
                    flipper.setInAnimation(this, R.anim.left_in);
                    flipper.setOutAnimation(this, R.anim.left_out);
                    flipper.showPrevious();
                }

                //向左滑动，看后一页
                if (event.getX() - X < 100) {
                    flipper.setInAnimation(this, R.anim.right_in);
                    flipper.setOutAnimation(this, R.anim.right_out);
                    flipper.showNext();
                }
                break;
        }
        return super.onTouchEvent(event);
    }
}
