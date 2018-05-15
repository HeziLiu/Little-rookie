package com.ifly.shendong.smarthomelayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by shendong on 2017/3/29.
 */

public class RomLayout extends RelativeLayout {

    public ImageView romImage;
    public TextView  romName;
    public TextView romState;

    public RomLayout(Context context) {
        this(context,null);
    }

    public RomLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context){
        View view=LayoutInflater.from(context).inflate(R.layout.rom_layout,this);
        romImage= (ImageView) view.findViewById(R.id.rom_image);
        romName= (TextView) view.findViewById(R.id.rom_name);
        romState= (TextView) view.findViewById(R.id.rom_state);
    }


}
