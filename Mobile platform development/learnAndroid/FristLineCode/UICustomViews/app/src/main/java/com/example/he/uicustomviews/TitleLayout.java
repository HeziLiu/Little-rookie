package com.example.he.uicustomviews;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * 自定义控件并添加响应事件
 * Created by he on 2016/3/29.
 */
public class TitleLayout extends LinearLayout {
    public TitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.title, this);
        Button back_button = (Button) findViewById(R.id.title_back);
        Button go_button = (Button) findViewById(R.id.title_edit);

        back_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setMessage("是否确定退出");
                dialog.setCancelable(false);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "再会", Toast.LENGTH_SHORT).show();
                        //哪个活动使用该控件，在返回时销毁
                        ((Activity) getContext()).finish();
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.show();

//                        ((Activity) getContext()).finish();
            }
        });
        go_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog dialog = new ProgressDialog(getContext());
                dialog.setMessage("努力加载中....");
                dialog.setCancelable(true);
                dialog.show();
            }
        });
    }
}
