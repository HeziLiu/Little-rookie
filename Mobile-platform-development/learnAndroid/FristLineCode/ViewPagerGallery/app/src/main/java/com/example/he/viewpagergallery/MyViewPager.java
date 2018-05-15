package com.example.he.viewpagergallery;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * Created by he on 2017/5/2.
 */

public class MyViewPager extends ViewPager {
    private int position=0;
    public MyViewPager(Context context) {
        this(context,null);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        setChildrenDrawingOrderEnabled(true);
    }


    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
        position = getCurrentItem();

        if(position<0){
            return i;
        }else{

            if(i == childCount - 1){//这是最后一个需要刷新的item
//
//                if(position>i){
//
//                    position=i;
//
//                }

                return position;

            }

            if(i == position){//这是原本要在最后一个刷新的item
                return childCount - 1;
            }

        }

        return i;
    }
}
