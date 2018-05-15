package com.example.he.circledrawable;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

/**
 * 自定义Drawable
 * Created by he on 2016/12/21.
 */

public class CricleDrawable extends Drawable {

    private Paint mPaint;
    private Bitmap mBitmap;
    private int mWidth;


    public CricleDrawable(Bitmap mBitmap) {
        this.mBitmap = mBitmap;
        BitmapShader shader=new BitmapShader(mBitmap, Shader.TileMode.CLAMP,Shader.TileMode.CLAMP);
        mPaint=new Paint();
        mPaint.setShader(shader);//设置着色器
        mPaint.setAntiAlias(true);//平滑效果
        mWidth= Math.min(mBitmap.getWidth(),mBitmap.getHeight());

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(mWidth/2,mWidth/2,mWidth/2,mPaint);
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
        invalidateSelf();
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;//让该drawable支持和窗口一样的透明度
    }

    //因为是圆形，以mWidth为内部宽/高
    @Override
    public int getIntrinsicWidth() {
        return  mWidth;
    }

    @Override
    public int getIntrinsicHeight() {
        return  mWidth;
    }
}
