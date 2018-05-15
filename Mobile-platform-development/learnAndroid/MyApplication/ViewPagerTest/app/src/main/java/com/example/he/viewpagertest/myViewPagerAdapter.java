package com.example.he.viewpagertest;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by he on 2016/11/2.
 */

public class myViewPagerAdapter extends PagerAdapter {
    private List<View> viewList;
    private List<String> titleList;

    myViewPagerAdapter(List<View> list, List<String> titleList) {
        viewList = list;
        this.titleList = titleList;
    }


    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewList.get(position));
        return viewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewList.get(position));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }
}
