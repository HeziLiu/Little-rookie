package com.example.he.sample2;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * ListView上拉加载更多
 * Created by he on 2016/11/19.
 */


enum FooterState {
    UN_PREPARE, PREPARE, REFRESH
}


public class myListView extends ListView {
    private View footer;
    private int footerHeight;
    private int startY;
    private boolean bottom = false;
    private boolean firstState = true;

    private ProgressBar progressBar;
    private TextView textView;

    private FooterState state = FooterState.UN_PREPARE;

    public myListView(Context context) {
        super(context);
        initFooter(context);
    }

    public myListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFooter(context);
    }

    public myListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFooter(context);
    }

    private void initFooter(Context context) {
        footer = LayoutInflater.from(context).inflate(R.layout.footer_layout, null);
        footer.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        progressBar = (ProgressBar) footer.findViewById(R.id.progress_bar);
        textView = (TextView) footer.findViewById(R.id.tv);
        footerHeight = footer.getMeasuredHeight();
        paddingBottom(-footerHeight);
        this.addFooterView(footer);

    }

    protected void paddingBottom(int padding) {
        footer.setPadding(footer.getPaddingLeft(), footer.getPaddingTop(), footer.getPaddingRight(), padding);
        footer.invalidate();
    }


    private void onMove(MotionEvent event) {
        int lastY = (int) event.getY();
        int space = startY - lastY;
        int padding = space - footerHeight;
        if (padding < 30) {
            if (firstState)
                refreshView(FooterState.UN_PREPARE, padding);
        }
        if (padding > 30 && padding < 100) {
            firstState=false;
            Log.i("xxx","xx");
            refreshView(FooterState.PREPARE, padding);
        }
    }


    private void refreshView(FooterState s, int padding) {
        switch (s) {
            case UN_PREPARE:
                state = FooterState.UN_PREPARE;
                textView.setText("上拉加载更多");
                progressBar.setVisibility(View.GONE);
                paddingBottom(padding);
                break;
            case PREPARE:
                state = FooterState.PREPARE;
                textView.setText("即将加载新数据");
                progressBar.setVisibility(View.GONE);
                paddingBottom(padding);
                break;
            case REFRESH:
                state = FooterState.REFRESH;
                textView.setText("正在加载...");
                progressBar.setVisibility(View.VISIBLE);

                break;
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                int t = this.getLastVisiblePosition();
                //表示已经滑动到底部
                if (t == this.getCount() - 1) {
                    bottom = true;
                    startY = (int) ev.getY();
                }

                break;
            case MotionEvent.ACTION_MOVE:
                if (bottom){
                    onMove(ev);
                }
                break;
            case MotionEvent.ACTION_UP:
                bottom = false;
                firstState = true;
                if (state == FooterState.PREPARE)
                    refreshView(FooterState.REFRESH, 0);
                if (state == FooterState.UN_PREPARE)
                    paddingBottom(-footerHeight);
                break;
        }

        return super.onTouchEvent(ev);
    }


    public FooterState getState() {
        return state;
    }

    public int getFooterHeight() {
        return footerHeight;
    }
}
