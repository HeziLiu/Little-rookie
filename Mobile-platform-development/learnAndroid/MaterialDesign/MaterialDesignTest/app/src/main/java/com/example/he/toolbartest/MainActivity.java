package com.example.he.toolbartest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 基本上所有的控件都需要添加依赖
 * MaterialDesignTest内容包括
 * 1、Toolbar控件逐渐代替ActionBar
 * 2、侧滑菜单--->DrawerLayout控件
 * DrawerLayout是一个布局，在布局中允许放入两个直接子控件，
 * 第一个子控件是主屏幕显示的内容，第二个子控件是滑动菜单显示的内容
 * 3、NavigationView
 * 使用NavigationView需要 menu（用于显示具体的菜单项） 和 headerLayout（显示头部布局）
 * 4、FloatingActionButton
 * 一个具有悬浮效果的Button,功能和普通button一样
 * 5、Snackbar
 * 和Toast一样是提醒用户的一种工具，但是toast只能提醒用户发生了什么，不能交互
 * SnackBar可以添加交互按钮
 * 6、CoordinatorLayout，见main.xml
 * FrameLayout的加强版，普通情况下和FrameLayout的作用相同
 * CoordinatorLayout可以监听其所有的子控件的各种事件，然后做出适当的响应
 * 7、CardView
 * 一种布局方式，实现了卡片式的布局效果，本质是一个FrameLayout,在fruit_item.xml中属于
 * 8、RecyclerView
 * ListView的升级版，增长逐步的代替ListView，使用的方法和ListView差不多
 * ################
 *  9、AppBarLayout,必须是CoordinatorLayout的子布局
 * 实际上是一个垂直的LinearLayout,内部做了很多滚动事件的封装，这里使用时为了解决recyclerView遮挡toolbar
 * ################
 * 10、CollapsingToolbarLayout 可折叠式标题栏 必须作为是AooBarLayout的直接子布局使用，不能单独使用
 * ###############
 * 11、SwipeRefreshLayout
 * 官方提供的下拉刷新控件
 * 12、NestedScrollView，在ScrollView的基础上增加了嵌套响应滚动事件的功能
 */
public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private FloatingActionButton actionButton;
    private static Fruit[] mFruits;
    private List<Fruit> fruitList = new ArrayList<Fruit>();
    private RecyclerView recyclerView;
    private FruitAdapter adapter;
    private SwipeRefreshLayout refreshLayout;


    static {
        mFruits = new Fruit[]{new Fruit("Apple", R.drawable.apple), new Fruit("Banana", R.drawable.banana),
                new Fruit("Orange", R.drawable.orange), new Fruit("Watermelon", R.drawable.watermelon),
                new Fruit("Pear", R.drawable.pear), new Fruit("Grape", R.drawable.grape),
                new Fruit("Pineapple", R.drawable.pineapple), new Fruit("Strawberry", R.drawable.strawberry),
                new Fruit("Cherry", R.drawable.cherry), new Fruit("Mango", R.drawable.mango)};
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        actionButton = (FloatingActionButton) findViewById(R.id.fab);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_View);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.SwipeRefresh);
        adapter = new FruitAdapter(fruitList);
        initFruit();
        //设置recyclerView的显示布局
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //在ActionBar中显示导航按钮
            actionBar.setDisplayHomeAsUpEnabled(true);
            //在ActionBar中设置导航按钮的图标
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        //将在navigationView中的call默认设置为选中
        navigationView.setCheckedItem(R.id.nav_call);
        //navigationView子菜单的监听事件
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                drawerLayout.closeDrawers();//关闭侧滑菜单
                return true;
            }
        });

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setAction添加交互
                Snackbar.make(v, "Snackbar显示的内容", Snackbar.LENGTH_SHORT).setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "Snackbar中添加的交互", Toast.LENGTH_SHORT).show();
                    }
                }).show();
            }
        });

        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        refreshLayout.setOnRefreshListener(this);
    }


    /**
     * 创建选择菜单，即Toolbar中显示的内容
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    /**
     * 选择菜单的点击
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.backup:
                Toast.makeText(this, "you click backup", Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                Toast.makeText(this, "you click delete", Toast.LENGTH_SHORT).show();
                break;
            case R.id.settings:
                Toast.makeText(this, "you click settings", Toast.LENGTH_SHORT).show();
                break;
            //按了导航按钮，弹出侧滑菜单
            case android.R.id.home:
                //与xml中drawerLayout的第二个子控件中layout_gravity属性保持一致）
                drawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return true;
    }


    /**
     * 初始化数据
     */
    private void initFruit() {
        fruitList.clear();
        Random random = new Random();
        for (int i = 0; i < 50; i++) {
            fruitList.add(mFruits[random.nextInt(mFruits.length)]);
        }
    }


    @Override
    public void onRefresh() {
        refreshFruits();
    }

    private void refreshFruits() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //返回主线程
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initFruit();
                        adapter.notifyDataSetChanged();
                        refreshLayout.setRefreshing(false);//隐藏刷新按钮
                    }
                });

            }
        }).start();
    }

}
