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

public class DeviceLayout extends RelativeLayout {
    public ImageView deviceImage;
    public TextView deviceName;

    public DeviceLayout(Context context) {
        this(context,null);
    }

    public DeviceLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);

    }

    public void initView(Context context){
        View view= LayoutInflater.from(context).inflate(R.layout.device_layout,this);
        deviceImage= (ImageView) view.findViewById(R.id.device_ic);
        deviceName= (TextView) view.findViewById(R.id.device_name);

    }

}
