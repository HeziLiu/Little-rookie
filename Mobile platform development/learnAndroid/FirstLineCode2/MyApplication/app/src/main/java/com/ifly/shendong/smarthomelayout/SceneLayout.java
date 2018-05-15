package com.ifly.shendong.smarthomelayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by shendong on 2017/3/31.
 */

public class SceneLayout extends RelativeLayout {

    public ImageView sceneImage;
    public TextView sceneName;


    public SceneLayout(Context context) {
        this(context, null);
    }

    public SceneLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.scene_layout, this);
        sceneImage = (ImageView) view.findViewById(R.id.scene_image);
        sceneName = (TextView) view.findViewById(R.id.scene_name);
    }


}
