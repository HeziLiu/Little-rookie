package com.example.he.hexx;

import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by he on 2017/2/27.
 */

public class TitleLayout extends LinearLayout {

    private static final String TAG="TitleLayout";


    private View mView;
    private Bitmap bitmap;
    private int mWidth;//屏幕宽
    private int mHeight;//屏幕高


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public TitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mView = LayoutInflater.from(context).inflate(R.layout.title, this);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bj);
        DisplayMetrics metrics =MyUtils.getScreenMetrics(context);
        mWidth=metrics.widthPixels;


//        Drawable drawable = new BitmapDrawable(null, sacleBitmap(bitmap, 0.5f));
//        mView.setBackground(drawable);
//        startAnimator();
//        preforAnimate(bitmap.getWidth(),bitmap.getWidth()/2);

        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                startAnimator();
                preforAnimate(mWidth,mWidth/2);
                return false;
            }
        });

    }


    public   void startAnimator() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(mView, "scaleX", 1f, 0.8f);
        animator.setDuration(5000);
//        preforAnimate(mWidth,mWidth/2);
        Log.d(TAG, "startAnimator: ");
        animator.start();
    }

    private Bitmap sacleBitmap(Bitmap bitmap, int requestWidth) {
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
        return Bitmap.createBitmap(bitmap, width/2-requestWidth/2, 0, requestWidth,height/2);
    }


    public void preforAnimate(final int start, final int end) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(1, 100);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            private IntEvaluator evaluator = new IntEvaluator();

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int currentValue = (int) animation.getAnimatedValue();
                float fraction = animation.getAnimatedFraction();
                int requestWidth = evaluator.evaluate(fraction, start, end);
                Log.d(TAG, "Width "+bitmap.getWidth());
                Log.d(TAG, "requestWidth "+requestWidth);
                Drawable drawable = new BitmapDrawable(null, sacleBitmap(bitmap,requestWidth));
                mView.setBackground(drawable);
            }
        });
        valueAnimator.setDuration(5000).start();
    }


}
