package com.example.he.viewpagergallery;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * 利用ViewPager打造画廊效果
 */
public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private List<View> viewList;
    private MyViewPagerAdapter adapter;
    private int pagerWidth;
    private static final String TAG = " MainActivity  ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        viewList = new ArrayList<>();

        init();
        //必须要大于page中数,因为重写了viewpager的方法
        mViewPager.setOffscreenPageLimit(10);

//        pagerWidth = (int) (getResources().getDisplayMetrics().widthPixels * 3.0f / 5.0f);
//        ViewGroup.LayoutParams lp = mViewPager.getLayoutParams();
//        if (lp == null) {
//            lp = new ViewGroup.LayoutParams(pagerWidth, ViewGroup.LayoutParams.MATCH_PARENT);
//        } else {
//            lp.width = pagerWidth;
//        }
//        mViewPager.setLayoutParams(lp);
        Log.d(TAG, "onCreate: " + mViewPager.getPageMargin());
        mViewPager.setPageMargin(-120);
        adapter = new MyViewPagerAdapter(viewList);
        mViewPager.setAdapter(adapter);
//        mViewPager.setCurrentItem(viewList.size()/2);
        mViewPager.setPageTransformer(true, new MyTransformation());


    }

    private void init() {

//        LinearLayout v1= (LinearLayout) LayoutInflater.from(this).inflate(R.layout.item,null,false);
//        ImageView im= (ImageView) v1.findViewById(R.id.image_);
//        Glide.with(this).load(R.drawable.a1).asBitmap().into(im);
//        im.setScaleType(ImageView.ScaleType.FIT_XY);
//        viewList.add(v1);


        ImageView im1 = new ImageView(this);
        Glide.with(this).load(R.drawable.a1).asBitmap().into(im1);
        im1.setScaleType(ImageView.ScaleType.FIT_XY);
        Log.d(TAG, "init: "+im1.getWidth());
        viewList.add(im1);
        ImageView im2 = new ImageView(this);
        im2.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        Glide.with(this).load(R.drawable.a2).into(im2);
        im2.setScaleType(ImageView.ScaleType.FIT_XY);
        viewList.add(im2);
        ImageView im3 = new ImageView(this);
        Glide.with(this).load(R.drawable.a3).into(im3);
        im3.setScaleType(ImageView.ScaleType.FIT_XY);
        viewList.add(im3);
        ImageView im4 = new ImageView(this);
        Glide.with(this).load(R.drawable.a4).into(im4);
        im4.setScaleType(ImageView.ScaleType.FIT_XY);
        viewList.add(im4);
        ImageView im5 = new ImageView(this);
        Glide.with(this).load(R.drawable.a5).into(im5);
        im5.setScaleType(ImageView.ScaleType.FIT_XY);
        viewList.add(im5);

        ImageView im6 = new ImageView(this);
        Glide.with(this).load(R.drawable.a5).into(im6);
        im6.setScaleType(ImageView.ScaleType.FIT_XY);
        viewList.add(im6);

        ImageView im7 = new ImageView(this);
        Glide.with(this).load(R.drawable.a5).into(im7);
        im7.setScaleType(ImageView.ScaleType.FIT_XY);
        viewList.add(im7);


    }


    class MyTransformation implements ViewPager.PageTransformer {

        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;
        private static final float MAX_ROTATE = 30;

        @Override
        public void transformPage(View page, float position) {
            float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
            float rotate = 20 * Math.abs(position);

            if (position <= -1) {
            } else if (position < 0) {
                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);
//                page.setRotationY(rotate);
            } else if (position > 0 && position < 1) {
                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);
//                page.setRotationY(-rotate);
            } else if (position >= 1) {
                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);
//                page.setRotationY(-rotate);
            }
        }
    }


}
