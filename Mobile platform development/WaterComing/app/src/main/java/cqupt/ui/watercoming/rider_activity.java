package cqupt.ui.watercoming;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
/**
 * Created by 78914 on 2017/6/12.
 */
public class rider_activity extends Activity implements View.OnClickListener{
    FragmentManager manager;//获取manager对Fragment布局文件进行操作
    FragmentTransaction ft;//获取操作类型
    Fragment_home_rider fragment_home;//三个Fragment类对象
    Fragment_order_rider fragment_order;
    Fragment_my_rider fragment_my;
    //用于获取图片按钮资源
    ImageView home;
    ImageView order;
    ImageView my;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.rider_layout);
        //控件绑定ID
        bindID();
        //获取manager对象
        manager=getFragmentManager();
        //开始事务
        ft=manager.beginTransaction();
        if (fragment_home==null){
            fragment_home=new Fragment_home_rider();
        }
        //给容器添加Fragment布局
        ft.add(R.id.frame_content_rider,fragment_home);
        //提交事务
        ft.commit();
    }
    private void bindID() {
        //获取住布局三个底部图片按钮
        home= (ImageView) findViewById(R.id.btnHome_rider);
        order= (ImageView) findViewById(R.id.btnOrder_rider);
        my= (ImageView) findViewById(R.id.btnMy_rider);
        //三个按钮点击事件
        home.setOnClickListener(this);
        order.setOnClickListener(this);
        my.setOnClickListener(this);
    }
    //点击事件具体实现方法
    @Override
    public void onClick(View v) {
        //开始事务
        ft=manager.beginTransaction();
        if (fragment_home!=null){
            //隐藏Fragment
            ft.hide(fragment_home);
        }
        if (fragment_order!=null){
            ft.hide(fragment_order);
            //隐藏Fragment
        }
        if (fragment_my!=null){
            ft.hide(fragment_my);
            //隐藏Fragment
        }
        //通过控件id来进行操作
        switch (v.getId()){
            case R.id.btnHome_rider:
                //单例模式
                if (fragment_home==null){
                    fragment_home=new Fragment_home_rider();
                    ft.add(R.id.frame_content_rider,fragment_home);
                }else {
                    //显示
                    ft.show(fragment_home);
                    //点击后图片切换效果
                    home.setImageResource(R.drawable.home_2);
                    order.setImageResource(R.drawable.order_1);
                    my.setImageResource(R.drawable.my_1);
                }
                break;
            case R.id.btnOrder_rider:
                //单例模式
                if (fragment_order==null){
                    fragment_order=new Fragment_order_rider();
                    ft.add(R.id.frame_content_rider,fragment_order);
                }else {
                    //显示
                    ft.show(fragment_order);
                    //点击后图片切换效果
                    home.setImageResource(R.drawable.home_1);
                    order.setImageResource(R.drawable.order_2);
                    my.setImageResource(R.drawable.my_1);
                }
                break;
            case R.id.btnMy_rider:
                //单例模式
                if (fragment_my==null){
                    fragment_my=new Fragment_my_rider();
                    ft.add(R.id.frame_content_rider,fragment_my);
                }else {
                    //显示
                    ft.show(fragment_my);
                    //点击后图片切换效果
                    home.setImageResource(R.drawable.home_1);
                    order.setImageResource(R.drawable.order_1);
                    my.setImageResource(R.drawable.my_2);
                }
                break;
            default:
                break;
        }
        //提交事务
        ft.commit();
    }
}
