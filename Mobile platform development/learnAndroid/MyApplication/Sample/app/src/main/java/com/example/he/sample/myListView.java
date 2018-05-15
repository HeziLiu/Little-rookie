package com.example.he.sample;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by he on 2016/11/17.
 */

/**
 * header下拉时的三种种状态，UN_PREPARE表示滑动的幅度不够不需要刷新，PREPARE表示的含义是现在松手就能刷新，REFRESH表示正在刷新
 */
enum State {
    UN_PREPARE, PREPARE, REFRESH
}


public class myListView extends ListView  {
    private static final String TAG = "ListView";

    private View header;
    private int headerHeight;
    private int startY;
    private boolean top;//用于判断是否在顶部滑动
    private boolean firstState = true;//是否是第一次进行状态判断

    private State state = State.UN_PREPARE;//滑动的状态，用于listView滑动事件的处理，见MainActivity.class

    private TextView title;
    private ImageView image;
    private ProgressBar bar;

    //下面的变量用于滑动删除
    private int mLastX;
    private int Max_Width = 100;//在布局中隐藏的 删除  宽度为100dp
    private int maxLength;
    private Context mContext;
    private static LinearLayout  lin_root;



    /**
     * 将dp转换为px
     *
     * @param context
     * @param dip
     * @return
     */
    private int dipToPx(Context context, int dip) {
        return (int) (dip * context.getResources().getDisplayMetrics().density + 0.5f);
    }


    public myListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public myListView(Context context) {
        super(context);
        initView(context);

    }

    public myListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    /**
     * 加载header视图
     *
     * @param context
     */
    private void initView(Context context) {
        mContext = context;
        header = LayoutInflater.from(context).inflate(R.layout.head, null);
        title = (TextView) header.findViewById(R.id.tip);
        image = (ImageView) header.findViewById(R.id.refresh);
        bar = (ProgressBar) header.findViewById(R.id.progress);
        header.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);//测量head
        headerHeight = header.getMeasuredHeight();//获取高度
        topPadding(-headerHeight);
        this.addHeaderView(header);
    }

    /**
     * 修改header的PaddingTop值达到隐藏header、随着滑动慢慢出现的效果
     *
     * @param topPadding
     */
    protected void topPadding(int topPadding) {
        header.setPadding(header.getPaddingLeft(), topPadding,
                header.getPaddingRight(), header.getPaddingBottom());
        header.invalidate();
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //下面的代码用于滑动删除
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        maxLength = dipToPx(mContext, Max_Width);

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                int t = this.getFirstVisiblePosition();
                //t为0表示到达屏幕顶部
                if (t == 0) {
                    top = true;
                    startY = (int) ev.getY();
                }

                //下面的代码用于滑动删除
                int position = pointToPosition(x, y);
                int firstPosition = getFirstVisiblePosition();

                Log.i(TAG, "onTouchEvent:  position" + position+" firstPosition "+firstPosition);
                if (position != INVALID_POSITION) {
                    lin_root = (LinearLayout) getChildAt(position - firstPosition);
                }
            }
                break;
            case MotionEvent.ACTION_UP: {
                top = false;
                firstState = true;
                //在状态2时松手则切换到状态3的视图
                if (state == State.PREPARE)
                    refreshView(State.REFRESH, 0);
                //在状态1时松手则恢复原样
                if (state == State.UN_PREPARE)
                    topPadding(-headerHeight);

                //下面的代码用于滑动删除
                int scrollX = lin_root.getScrollX();
                int newScrollX;
                if (scrollX > maxLength / 2) {
                    newScrollX = maxLength;
                } else {
                    newScrollX = 0;
                }
                  lin_root.scrollTo(newScrollX, 0);
            }
            break;
            case MotionEvent.ACTION_MOVE: {
                if (top)
                    onMove(ev);
                //下面的代码用于滑动删除
//                int scrollX = bean.lin_root.getScrollX();
                int scrollX = lin_root.getScrollX();
                int newScrollX = scrollX + mLastX - x;
                if (newScrollX < 0) {
                    newScrollX = 0;
                } else if (newScrollX > maxLength) {
                    newScrollX = maxLength;
                }
                lin_root.scrollTo(newScrollX, 0);
            }
            break;
        }
        mLastX = x;
        return super.onTouchEvent(ev);
    }



    /**
     * 手指正在滑动时进行状态判断
     *
     * @param event
     */
    private void onMove(MotionEvent event) {
        int lastY = (int) event.getY();
        int space = lastY - startY;
        int padding = space - headerHeight;
        Log.i("padding", "" + padding);
        //状态1
        if (padding < 30) {
            Log.i("state", "1");
            if (firstState)
                refreshView(State.UN_PREPARE, padding);
        }
        //状态2
        else if (padding > 30 && padding < 100) {
            firstState = false;
            refreshView(State.PREPARE, padding);
        }

    }


    /**
     * 根据不同的状态，完成相应的操作
     */
    private void refreshView(State s, int padding) {
        switch (s) {
            case UN_PREPARE:
                topPadding(padding);
                state = State.UN_PREPARE;//更新状态
                bar.setVisibility(View.GONE);
                image.setVisibility(View.VISIBLE);
                title.setText("下拉刷新");

                break;
            case PREPARE:
                image.setVisibility(View.VISIBLE);
                bar.setVisibility(View.GONE);
                topPadding(padding);
                state = State.PREPARE;//更新状态

                //更新header中的视图
                /**
                 * 创建旋转动画达到向下的箭头朝上的目的,旋转180度，以自身为中心
                 */
                RotateAnimation animation = new RotateAnimation(0, 180, RotateAnimation.RELATIVE_TO_SELF, 0.5F, RotateAnimation.RELATIVE_TO_SELF, 0.5F);
                animation.setDuration(0);//0毫秒内完成效果
                animation.setFillAfter(true);//图片的显示为执行动画后的样子
                image.startAnimation(animation);
                title.setText("松手进行刷新");

                topPadding(padding);
                break;
            case REFRESH:
                image.setVisibility(View.GONE);//隐藏image
                bar.setVisibility(View.VISIBLE);//显示bar
                //更新header中的视图
                image.clearAnimation();//清除image上的动画
                title.setText("正在刷新");
                state = State.REFRESH;//更新状态
                break;
        }
    }

    public int getHeaderHeight() {
        return headerHeight;
    }

    public State getState() {
        return state;
    }

}
