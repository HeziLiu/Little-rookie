package com.example.he.animator;

import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button button1 = (Button) findViewById(R.id.button_1);
        final Button button2 = (Button) findViewById(R.id.button_2);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {

                performAnimate(button1);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performAnimate(button2,button2.getWidth(),500);
            }
        });


    }

    private void performAnimate(View view) {
        ViewWrapper wrapper = new ViewWrapper(view);
        ObjectAnimator.ofInt(wrapper, "width", 500).setDuration(5000).start();
    }


    private static class ViewWrapper {
        private View mView;

        public ViewWrapper(View mView) {
            this.mView = mView;
        }

        public int getWidth() {
            return mView.getLayoutParams().width;
        }

        public void setWidth(int width) {
            mView.getLayoutParams().width = width;
            mView.requestLayout();
        }

    }

    private void performAnimate(final View view, final int start, final int end) {
        ValueAnimator animator=ValueAnimator.ofInt(1,100);//设置进度范围,用于计算比例
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            private IntEvaluator evaluator=new IntEvaluator();//整型估值器

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                /**
                 * 获取进度比例，因为上面指定了进度范围，下面指定了动画时间5s,那么属性动画的值会在
                 * 5s内从1变到100从而获得进度比例，然后通过进度比例和start，end之间的计算，得出该显示的宽度
                 */
                float fraction=animation.getAnimatedFraction();
                view.getLayoutParams().width=evaluator.evaluate(fraction,start,end);
                view.requestLayout();

            }
        });

        animator.setDuration(5000).start();

    }


}
