package com.example.he.viewpagergallery;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by he on 2017/5/2.
 */

public class MyViewPagerAdapter extends PagerAdapter {

    private List<View> mList;
    private ViewGroup parent;

    public MyViewPagerAdapter(List<View> mList) {
        this.mList = mList;
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object ;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mList.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        parent=container;
        container.addView(mList.get(position));
        return mList.get(position);
    }





}
