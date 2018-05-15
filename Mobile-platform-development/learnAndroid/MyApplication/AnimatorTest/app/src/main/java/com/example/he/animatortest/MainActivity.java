package com.example.he.animatortest;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final float N = 120F;//每次弹出，收回菜单每个图标移动的距离

    private ImageView imageView;
    private Button button;
    private Button value_anim_button;
    private int res[] = {R.id.imageView_a, R.id.imageView_b, R.id.imageView_c, R.id.imageView_d, R.id.imageView_e, R.id.imageView_f, R.id.imageView_g, R.id.imageView_h};//图片ID数组
    private List<ImageView> imageViewList;

    private float anim_Y;//图片的起始Y坐标
    private boolean flag = false;//false表示菜单第一次按下，则弹出菜单，true表示第二次按下，则收回菜单

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.image_);
        button = (Button) findViewById(R.id.button_);
        value_anim_button = (Button) findViewById(R.id.value_button);
        imageView.setOnClickListener(this);
        button.setOnClickListener(this);
        value_anim_button.setOnClickListener(this);

        imageViewList = new ArrayList<ImageView>();

        for (int i = 0; i < res.length; i++) {
            ImageView anim_image = (ImageView) findViewById(res[i]);
            anim_Y = anim_image.getY();
            anim_image.setOnClickListener(this);
            imageViewList.add(anim_image);


        }


    }

    //在XML中配置了onClick属性
    public void click(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", 0F, 1F);
        animator.setDuration(1000);
        animator.start();
        //给动画添加监听事件
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Toast.makeText(MainActivity.this, "动画执行完毕", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_:
                Toast.makeText(this, "点了图片的位置", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_:
                /**
                 * 如果用传统的Animation,即使图片已经移动到了坐标为(100,0)的位置但是点击原来图片的位置（0.0）
                 * 还是会有Toast信息
                 */
//                TranslateAnimation animation = new TranslateAnimation(0, 100, 0, 0);
//                animation.setDuration(1000);
//                animation.setFillAfter(true);
//                imageView.startAnimation(animation);

                /**
                 * 使用属性动画中的ObjectAnimator则不会出现这种情况
                 */

                //平移和旋转动画同时进行
//                ObjectAnimator.ofFloat(imageView,"translationX",0F,100F).setDuration(1000).start();
//                ObjectAnimator.ofFloat(imageView,"rotation",0F,360F).setDuration(1000).start();

                /**
                 * 使用AnimatorSet，为imageView添加动画集合，效果和上面一样,增强了对属性动画的控制
                 */

                ObjectAnimator animator1 = ObjectAnimator.ofFloat(imageView, "translationX", 0F, 100F);
                ObjectAnimator animator2 = ObjectAnimator.ofFloat(imageView, "rotation", 0F, 360F);
                ObjectAnimator animator3 = ObjectAnimator.ofFloat(imageView, "translationY", 0, 100);
                AnimatorSet set = new AnimatorSet();
//                set.playTogether(animator1,animator2);//添加动画效果,同时进行
//                set.playSequentially(animator1,animator2);//添加动画效果,相继进行
                set.play(animator1).with(animator2);//右移加旋转
                set.play(animator3).after(animator2);//最后下移
                set.setDuration(1000);
                set.start();

                break;

            //展开动画
            case R.id.imageView_a:
                if (!flag)
                    startAnim();
                else
                    closeAnim();
                break;
            case R.id.imageView_b:
                break;
            case R.id.imageView_c:
                break;
            case R.id.imageView_d:
                break;
            case R.id.imageView_e:
                break;
            case R.id.imageView_f:
                break;
            case R.id.imageView_g:
                break;

            //ValueAnimator演示
            case R.id.value_button:
                //获取从1到100的数值
                ValueAnimator valueAnimator=ValueAnimator.ofInt(1,100);
                valueAnimator.setDuration(5000);//5s
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        Integer value= (Integer) animation.getAnimatedValue();
                        value_anim_button.setText(""+value);
                        if(value==100)
                            value_anim_button.setText("完毕");
                    }
                });
                valueAnimator.start();
                break;

        }
    }

    /**
     * 展开菜单
     */
    private void startAnim() {
        ObjectAnimator animator = null;
        for (int i = 1; i < res.length; i++) {
            animator = ObjectAnimator.ofFloat(imageViewList.get(i), "translationY", anim_Y, -N * i);
            animator.setDuration(1000);
            animator.setInterpolator(new BounceInterpolator());//添加插值器，小球弹动效果
            animator.start();
        }

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                //弹出动画结束后将flag变为true
                flag = true;
                super.onAnimationEnd(animation);
            }
        });

    }


    /**
     * 收回菜单
     */

    private void closeAnim() {
        ObjectAnimator animator = null;
        for (int i = 1; i < res.length; i++) {
            animator = ObjectAnimator.ofFloat(imageViewList.get(i), "translationY", -N * i, anim_Y);
            animator.setDuration(1000);
            animator.setStartDelay(i * 300);
            animator.start();
        }

        animator.addListener(new AnimatorListenerAdapter() {
            //弹出动画结束后将flag变为true
            @Override
            public void onAnimationEnd(Animator animation) {
                flag = false;
                super.onAnimationEnd(animation);
            }
        });
    }


}
