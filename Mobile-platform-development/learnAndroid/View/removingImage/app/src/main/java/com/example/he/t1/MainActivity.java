package com.example.he.t1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

/**
 * 一个随手指移动的ImageView
 */

public class MainActivity extends AppCompatActivity {

    private movingImage image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image = (movingImage) findViewById(R.id.imageV);
        image.setClickable(true);
    }

    /**
     * 这样保证了只有按住图片拖动时图片才会动
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (image.isPress())
            image.autoMouse(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                image.setPress(false);
                break;
        }
        return false;
    }


}
