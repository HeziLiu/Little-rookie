package com.example.he.myviewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * 自定义ViewGroup实现ViewPager的功能
 * Created by he on 2016/12/19.
 */

public class UI_ViewGroup extends ViewGroup {

    private int mChildrenSize;//viewGroup的子View的个数
    private int mChildWidth;//总宽
    private int mChildIndex;//子View的index

    //记录上次滑动的坐标
    private int mLastX = 0;
    private int mLastY = 0;

    //记录上次滑动的坐标(onInterceptHoverEvent)
    private int mLastXIntercept = 0;
    private int mLastYIntercept = 0;


    private Scroller mScroller;//实现View的弹性滑动
    private VelocityTracker mVelocityTracker;//滑动速率监控

    public UI_ViewGroup(Context context) {
        super(context);
        init();
    }

    public UI_ViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public UI_ViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        if (mScroller == null) {
            mScroller = new Scroller(getContext());
            mVelocityTracker = VelocityTracker.obtain();
        }

    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                intercepted = false;
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();//停止动画
                    intercepted = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastXIntercept;
                int deltaY = y - mLastYIntercept;
                //如果左右滑动的距离大于上下滑动的距离，拦截该事件
                if (Math.abs(deltaX) > Math.abs(deltaY))
                    intercepted = true;
                else
                    intercepted = false;
                break;
            case MotionEvent.ACTION_UP:
                intercepted = false;
                break;
        }


        mLastX = x;
        mLastY = y;
        mLastXIntercept = x;
        mLastYIntercept = y;
        return intercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mVelocityTracker.addMovement(event);//添加事件
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();

                }
                break;

            case MotionEvent.ACTION_MOVE:
                int deltaX=x-mLastX;
                int deltaY=y-mLastY;
                scrollBy(-deltaX,0);
                break;
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                mVelocityTracker.computeCurrentVelocity(1000);
                float xVelocity = mVelocityTracker.getXVelocity();
                //用于手指很快的滑动
                if (Math.abs(xVelocity) > 50) {
                    mChildIndex = xVelocity > 0 ? mChildIndex - 1 : mChildIndex + 1;
                }else {
                    //根据滑动的距离判断显示哪个子View
                    mChildIndex = (scrollX + mChildWidth / 2) / mChildWidth;
                }
                //处理已经是第一个子View还继续向右滑动，已经已经是最后一个子View还继续向左滑动
                mChildIndex = Math.max(0, Math.min(mChildIndex, mChildrenSize - 1));
                int dx = mChildIndex * mChildWidth - scrollX;
                smoothScrollBy(dx, 0);
                mVelocityTracker.clear();
                break;
        }

        mLastX = x;
        mLastY = y;

        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidth = 0;
        int measureHeight = 0;
        final int childCount = getChildCount();
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int widthSpaceSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpaceSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);

        if (childCount == 0) {
            setMeasuredDimension(0, 0);
        } else if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            final View childView = getChildAt(0);
            measureWidth = childCount * childView.getMeasuredWidth();
            measureHeight = childView.getMeasuredHeight();
            setMeasuredDimension(measureWidth, measureHeight);
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            final View childView = getChildAt(0);
            measureWidth = childCount * childView.getMeasuredWidth();
            setMeasuredDimension(measureWidth, heightSpaceSize);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            final View childView = getChildAt(0);
//            measureHeight = childView.getMeasuredHeight();
//            setMeasuredDimension(widthSpaceSize, measureHeight);
            setMeasuredDimension(widthSpaceSize, childView.getMeasuredHeight());
        }

    }


    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        int childLeft = 0;
        final int childCount = getChildCount();
        mChildrenSize = childCount;
        for (int j = 0; j < childCount; j++) {
            final View childView = getChildAt(j);
            if (childView.getVisibility() != View.GONE) {
                final int childWidth = childView.getMeasuredWidth();
                mChildWidth=childWidth;
                childView.layout(childLeft, 0, childLeft + childWidth, childView.getMeasuredHeight());
                childLeft += childWidth;
            }
        }

    }

    private void smoothScrollBy(int dx, int dy) {
        mScroller.startScroll(getScrollX(), 0, dx, 0, 500);
        invalidate();//View重绘
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        mVelocityTracker.recycle();
        super.onDetachedFromWindow();
    }
}
