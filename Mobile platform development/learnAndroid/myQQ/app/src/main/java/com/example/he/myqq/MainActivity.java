package com.example.he.myqq;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;

import com.example.he.myqq.Fragment.Fragment1;
import com.example.he.myqq.Fragment.Fragment2;
import com.example.he.myqq.Fragment.Fragment3;
import com.example.he.myqq.utils.SharedPreferrenceHelper;

import java.util.ArrayList;

/**
 * 主界面
 */
public class MainActivity extends FragmentActivity {
    public static DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private ArrayList<Fragment> fragmentsList = new ArrayList<Fragment>();
    private RadioGroup group;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int theme = SharedPreferrenceHelper.getTheme(this);
        setTheme(theme);//设置主题
        setContentView(R.layout.activity_main);
        //设置状态栏
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.parseColor("#66B3FF"));
        }

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_View);
        group = (RadioGroup) findViewById(R.id.rg);

        // 给group设置监听事件，在监听事件实现fragment之间的切换
        RadioGroup.OnCheckedChangeListener listener = new MyOnCheckedChangeListener();
        group.setOnCheckedChangeListener(listener);

        // 选中首页，否则开始启动的时候画面展示白板
        group.check(R.id.rb_1);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            /**
             * 设置侧滑菜单选项中的点击事件
             * @param item
             * @return
             */
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.night:
                        int theme = SharedPreferrenceHelper.getTheme(MainActivity.this);
                        if (theme == R.style.AppTheme) {
                            SharedPreferrenceHelper.setTheme(MainActivity.this, R.style.AppThemeDark);
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                            startActivity(intent);
                            finish();
                        } else {
                            SharedPreferrenceHelper.setTheme(MainActivity.this, R.style.AppTheme);
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                            startActivity(intent);
                            finish();
                        }

                        break;
                    default:
                        break;
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });

    }

    private class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {
        // 在构造方法中创造fragment
        public MyOnCheckedChangeListener() {
            // 将new出来的fragment放置在集合中，以便后续取用
            fragmentsList.add(new Fragment1());
            fragmentsList.add(new Fragment2());
            fragmentsList.add(new Fragment3());
        }

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            // 当选中某一个radio的时候，就展现某一个fragment,用到fragment的事务
            FragmentTransaction ft = getSupportFragmentManager()
                    .beginTransaction();
            switch (checkedId) {
                case R.id.rb_1:
                    ft.replace(R.id.fl, fragmentsList.get(0));
                    break;
                case R.id.rb_2:
                    ft.replace(R.id.fl, fragmentsList.get(1));
                    break;
                case R.id.rb_3:
                    ft.replace(R.id.fl, fragmentsList.get(2));
                    break;
                default:
                    break;
            }
            // 最后事务一定要提交
            ft.commit();
        }

    }

}
