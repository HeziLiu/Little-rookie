package com.example.he.myqq.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.he.myqq.LoginActivity;
import com.example.he.myqq.MainActivity;
import com.example.he.myqq.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 主界面title布局
 * Created by he on 2017/1/26.
 */

public class TitleLayout extends LinearLayout implements View.OnClickListener {

    private ImageView home;
    private Button news;
    private Button phone;
    private ImageView add;
    private Context mContext;

    private PopupWindow mPopup;
    private ListView mPopList;
    private List<Map<String, String>> moreList;

    private static int NUM_OF_VISIBLE = 3;//popupWindow中显示的item的数量


    public TitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.header, this);
        mContext = context;
        home = (ImageView) findViewById(R.id.home_);
        news = (Button) findViewById(R.id.news);
        phone = (Button) findViewById(R.id.phone);
        add = (ImageView) findViewById(R.id.add_);
        home.setOnClickListener(this);
        news.setOnClickListener(this);
        phone.setOnClickListener(this);
        add.setOnClickListener(this);

        initDate();
        initpopupWindow();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_:
                MainActivity.drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.news:
                Toast.makeText(mContext, "点击了消息按钮", Toast.LENGTH_SHORT).show();
                break;
            case R.id.phone:
                Toast.makeText(mContext, "点击了通话按钮", Toast.LENGTH_SHORT).show();
                break;
            case R.id.add_:
                if (mPopup.isShowing()) {
                    mPopup.dismiss();
                } else {
                    mPopup.showAsDropDown(add);
                }
                break;
            default:
                break;
        }

    }

    /**
     * 加载popupWindow中ListView的数据
     */
    private void initDate() {
        moreList = new ArrayList<Map<String, String>>();
        Map<String, String> map;
        map = new HashMap<>();
        map.put("share_key", "拍摄");
        moreList.add(map);
        map = new HashMap<String, String>();
        map.put("share_key", "修改");
        moreList.add(map);
        map = new HashMap<String, String>();
        map.put("share_key", "退出登录");
        moreList.add(map);

    }


    /**
     * 初始化popupWindow
     */
    private void initpopupWindow() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popupwindow_layout, null);
        mPopList = (ListView) view.findViewById(R.id.popup_list);
        mPopup = new PopupWindow(view);
        //让popupWindow中的ListView接收点击事件
        mPopup.setFocusable(true);
        //设置listAdapter
        mPopList.setAdapter(new SimpleAdapter(mContext, moreList, R.layout.popupwindow_item, new String[]{"share_key"}, new int[]{R.id.popup_list_item}));
        mPopList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (moreList.get(position).get("share_key").equals("退出登录")) {
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    mContext.startActivity(intent);
                    Activity a = (Activity) mContext;
                    a.finish();
                }
            }
        });

        //设置popupWindow的宽高

        mPopList.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        mPopup.setWidth(mPopList.getMeasuredWidth());
        mPopup.setHeight((mPopList.getMeasuredHeight()) * NUM_OF_VISIBLE);

        //控制pop点击屏幕其他地方消失
//        mPopup.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.popup_bj));
//        mPopup.setOutsideTouchable(true);
    }

}
