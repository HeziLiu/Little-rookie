package com.example.he.animationtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Android 动画主要分为四种类型：Tween Animation(变换动画)、Frame Animation(帧动画)、
 * Layout Animation(界面动画)、Property Animation(属性动画)
 *
 * Tween Animation 为单个View添加动画
 * Layout Animation 为一个ViewGroup添加动画，每个字View都具有动画效果
 * 需要使用界面动画控制器
 *
 *
 * <p>
 * 其中每种动画又可分为四种动画效果：
 * Alpha:渐变透明动画
 * Scale:渐变尺寸缩放动画
 * Translate:位置移动动画
 * Rotate:旋转动画
 * 每种动画都可以通过 xml和动态加载的方式实现
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button alpha;
    private Button scale;
    private Button translate;
    private Button rotate;
    private ImageView imageView;

    private Button zh_1;
    private Button zh_2;
    private Button activity_b;
    private Button layout_button;
    private Button frame_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alpha = (Button) findViewById(R.id.alpha_button);
        scale = (Button) findViewById(R.id.scale_button);
        translate = (Button) findViewById(R.id.translate_button);
        rotate = (Button) findViewById(R.id.rotate_button);
        imageView = (ImageView) findViewById(R.id.imageV);
        zh_1 = (Button) findViewById(R.id.zh_1);
        zh_2 = (Button) findViewById(R.id.zh_2);
        activity_b = (Button) findViewById(R.id.a_button);
        layout_button= (Button) findViewById(R.id.layout_button);
        frame_button= (Button) findViewById(R.id.frame_button);

        alpha.setOnClickListener(this);
        scale.setOnClickListener(this);
        translate.setOnClickListener(this);
        rotate.setOnClickListener(this);
        zh_1.setOnClickListener(this);
        zh_2.setOnClickListener(this);
        activity_b.setOnClickListener(this);
        layout_button.setOnClickListener(this);
        frame_button.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Animation animation;
        switch (v.getId()) {
            case R.id.alpha_button:
                imageView.setImageResource(R.mipmap.ic_launcher);
                animation = AnimationUtils.loadAnimation(this, R.anim.alpha_anim);
                imageView.startAnimation(animation);
                break;
            case R.id.scale_button:
                imageView.setImageResource(R.mipmap.ic_launcher);
                animation = AnimationUtils.loadAnimation(this, R.anim.scale_anim);
                imageView.startAnimation(animation);
                break;
            case R.id.translate_button:
                imageView.setImageResource(R.mipmap.ic_launcher);
                animation = AnimationUtils.loadAnimation(this, R.anim.translate_anim);
                imageView.startAnimation(animation);
                break;
            case R.id.rotate_button:
                imageView.setImageResource(R.mipmap.ic_launcher);
                animation = AnimationUtils.loadAnimation(this, R.anim.rotate_anim);
                imageView.startAnimation(animation);
                break;
            case R.id.zh_1:
                imageView.setImageResource(R.mipmap.ic_launcher);
                //组合动画,两种动画同时执行
                animation = AnimationUtils.loadAnimation(this, R.anim.zh1_anim);
                imageView.startAnimation(animation);
                break;
            case R.id.zh_2:
                imageView.setImageResource(R.mipmap.ic_launcher);
                //组合动画2两种动画连续执行
                animation = AnimationUtils.loadAnimation(this, R.anim.translate_anim);
                imageView.startAnimation(animation);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        //动画1结束后执行动画2
                        imageView.setAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.rotate_anim));
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                break;
            case R.id.a_button:
                //切换Activity时的动画效果
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
                //第一个参数是第二个Activity进入的效果，第二个参数是第一个Activity退出的效果
                overridePendingTransition(R.anim.act_in, R.anim.act_out);//设置切换Activity动画效果

                break;

            case R.id.layout_button:
                //界面动画
                Intent intent2 = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent2);
                break;

            case R.id.frame_button:
                //逐帧动画
                imageView.setImageResource(R.drawable.frame_anim);
                break;
        }
    }
}
