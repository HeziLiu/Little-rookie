package com.example.he.slidedelete;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    private LinearLayout itemRoot;
    private int mLastX = 0;
    private final int MAX_WIDTH = 100;
    //将dip转换为pix
    private int dipToPx(Context context, int dip) {
        return (int) (dip * context.getResources().getDisplayMetrics().density + 0.5f);
    }
    private int maxLength=100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        itemRoot = (LinearLayout) findViewById(R.id.lin_root);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int scrollX = itemRoot.getScrollX();
        maxLength=dipToPx(this,MAX_WIDTH);
        int x = (int) event.getX();
        int newScrollX = scrollX + mLastX - x;
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if (newScrollX<0){
                newScrollX = 0;
            }else if (newScrollX >maxLength){
                newScrollX = maxLength;
            }
            itemRoot.scrollTo(newScrollX, 0);
        }
        if(event.getAction()==MotionEvent.ACTION_UP){
            if (scrollX > maxLength/2){
                newScrollX = maxLength;
            }else {
                newScrollX = 0;
            }
            itemRoot.scrollTo(newScrollX, 0);
        }
        mLastX = x;
        return super.onTouchEvent(event);
    }
}
