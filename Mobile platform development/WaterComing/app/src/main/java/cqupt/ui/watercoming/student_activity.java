package cqupt.ui.watercoming;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import tool.MyOpenHelper;
/**
 * Created by 78914 on 2017/6/12.
 */
public class student_activity extends Activity implements View.OnClickListener {
    FragmentManager manager;//获取manager对Fragment布局文件进行操作
    FragmentTransaction ft;//获取操作类型
    Fragment_home fragment_home;//三个Fragment类对象
    Fragment_order fragment_order;
    Fragment_my fragment_my;
    Intent Data;      //用于接收学生登陆界面传送的数据
    //用于获取图片按钮资源
    ImageView home;
    ImageView order;
    ImageView my;
    //用于存放传送过来的学生信息
    String name;
    String num;
    private MyOpenHelper dbHelper;//对数据库进行操作
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//Activity无标题设置
        setContentView(R.layout.student_layout);
        //得到传送的数据
        Data=getIntent();
        //验证是否传输过来
        String it=Data.getStringExtra("haveData");
        if (it!=null&&it.equals("true")){
            //得到学生姓名
            name=Data.getStringExtra("name");
        }
        //获取数据库当前版本
        dbHelper=MyOpenHelper.getInstance(this);
        //获取数据库读操作
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select num from student where name=?", new String[]{name});
        if (cursor.moveToNext()) {
            //得到学生寝室号用于下单时的学生信息
            num = cursor.getString(cursor.getColumnIndex("num"));}
        //关闭数据库
        dbHelper.close();
        //关闭游标
        cursor.close();
        //绑定控件ID
        bindID();
        manager=getFragmentManager();
        ft=manager.beginTransaction();
        //单例模式 首先进入主页面
        if (fragment_home==null){
            fragment_home=new Fragment_home();
        }
        //添加Fragment视图到容器中
        ft.add(R.id.frame_content,fragment_home);
        //提交事务
        ft.commit();
    }
    private void bindID() {
        //获取图片按钮ID
        home= (ImageView) findViewById(R.id.btnHome);
        order= (ImageView) findViewById(R.id.btnOrder);
        my= (ImageView) findViewById(R.id.btnMy);
        //设置点击事件
        home.setOnClickListener(this);
        order.setOnClickListener(this);
        my.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        //开始事务
        ft=manager.beginTransaction();
        //单例模式
        if (fragment_home!=null){
            //隐藏
            ft.hide(fragment_home);
        }
        if (fragment_order!=null){
            //隐藏
            ft.hide(fragment_order);
        }
        if (fragment_my!=null){
            //隐藏
            ft.hide(fragment_my);
        }
        //通过ID获取点击事件
        switch (v.getId()){
            case R.id.btnHome:
                //单例模式
                if (fragment_home==null){
                    fragment_home=new Fragment_home();
                    ft.add(R.id.frame_content,fragment_home);
                }else {
                    //显示
                    ft.show(fragment_home);
                    //按钮点击切换图片
                    home.setImageResource(R.drawable.home_2);
                    order.setImageResource(R.drawable.order_1);
                    my.setImageResource(R.drawable.my_1);
                }
                break;
            case R.id.btnOrder:
                //单例模式
                if (fragment_order==null){
                    fragment_order=new Fragment_order();
                    //利用Bundle对象给Fragment传值用于个人中心相应数据的显示
                    Bundle bundle=new Bundle();
                    bundle.putString("DATA",name);
                    bundle.putString("DATA2",num);
                    fragment_order.setArguments(bundle);
                    ft.add(R.id.frame_content,fragment_order);
                }else {
                    //显示
                    ft.show(fragment_order);
                    //按钮点击切换图片
                    home.setImageResource(R.drawable.home_1);
                    order.setImageResource(R.drawable.order_2);
                    my.setImageResource(R.drawable.my_1);
                }
                break;
            case R.id.btnMy:
                //单例模式
                if (fragment_my==null){
                    fragment_my=new Fragment_my();
                    //利用Bundle对象给Fragment传值用于个人中心相应数据的显示
                    Bundle bundle=new Bundle();
                    bundle.putString("DATA",name);
                    bundle.putString("DATA2",num);
                    fragment_my.setArguments(bundle);
                    ft.add(R.id.frame_content,fragment_my);
                }else {
                    //显示
                    ft.show(fragment_my);
                    //按钮点击切换图片
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
