package com.example.he.t1;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * Created by he on 2016/11/30.
 */

public class movingImage extends ImageView    {
    private boolean press=false;
    public movingImage(Context context) {
        super(context);
    }

    public movingImage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public movingImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setLocation(int x, int y) {
//        this.setFrame(x, y - this.getHeight(), x + this.getWidth(), y);
        this.setFrame(x-50, y - this.getHeight()-100, x + this.getWidth()-50, y-100);
    }

    // 移动
    public boolean autoMouse(MotionEvent event) {
        boolean rb = false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:

                this.setLocation((int) event.getX(), (int) event.getY());
//                this.setLocation((int) event.getRawX(), (int) event.getRawY());
                rb = true;
                break;
        }
        return rb;
    }


    public void setPress(boolean press) {
        this.press = press;
    }

    public boolean isPress() {
        return press;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        press=true;
//        autoMouse(event);
        return false;
    }


}
