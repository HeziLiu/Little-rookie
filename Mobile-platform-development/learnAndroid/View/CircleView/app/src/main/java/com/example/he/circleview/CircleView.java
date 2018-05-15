package com.example.he.circleview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 继承自View需要自己处理padding和warp_content,margin由父容器处理无需自己处理
 * Created by he on 2016/12/14.
 */

public class CircleView extends View {

    private static final String TAG = "myView";

    //处理warp_content,设置默认值
    private int mWidth = 200;
    private int mHeight = 400;
    private int mColor = Color.RED;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Context mContext;


    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        //获取属性
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircleView);
        mColor = array.getColor(R.styleable.CircleView_circle_color, Color.BLACK);
        array.recycle();
        mPaint.setColor(mColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(mWidth, mHeight);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(mWidth, heightSize);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSize, mHeight);
        } else {
            setMeasuredDimension(widthSize, heightSize);
        }


    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        final int paddingLeft = getPaddingLeft();
        final int paddingRight = getPaddingRight();
        final int paddingTop = getPaddingTop();
        final int paddingBottom = getPaddingBottom();
        int width = getWidth() - paddingLeft - paddingRight;
        int height = getHeight() - paddingTop - paddingBottom;
        /**
         * 下面这个是根据布局文件中layout_width和layout_height的数值画出相应的圆
         */
//        int radius = Math.min(width, height) / 2;
//        //圆心和半径考虑到View四周的padding，做出相应的调整
//        canvas.drawCircle(paddingLeft + width / 2, paddingTop + height / 2, radius, mPaint);


        /**
         * 下面是画出指定大小的圆，为了方便展示View随手指移动
         */
        int radius = 50;
        //圆心和半径考虑到View四周的padding，做出相应的调整
        canvas.drawCircle(50, 50, radius, mPaint);


    }

    /**
     * @param event
     * @return
     */

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                int x = (int) event.getX();
                int y = (int) event.getY();
                /**
                 * mScrollX是View左边缘的X坐标减去View内容左边缘的X坐标，因此如果手指是从左向右滑，
                 则mScrollX为负值，反之正值，mScrollY也是一样的,见源码
                 */
                scrollTo(-x, -y);
        }
        return true;
    }

}
