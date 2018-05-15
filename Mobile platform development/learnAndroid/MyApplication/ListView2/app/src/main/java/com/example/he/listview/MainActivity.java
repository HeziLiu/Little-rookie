package com.example.he.listview;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.LayoutAnimationController;
import android.view.animation.ScaleAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 该项目实现ListView的常用功能：ListView的适配以及优化、ListView的点击事件，
 * ListView的下拉刷新， ListView的分页，ListView的滑动动画、ListView的上下文菜单
 */


public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private ListView listView;
    private List<ItemBean> itemBeanList;
    private myBaseAdapter adapter;
    private ScaleAnimation animation;//一个缩放动画
    private LayoutAnimationController controller;//界面动画控制器
    private SwipeRefreshLayout ly;

    private static final int OK = 1;
    private final Random random = new Random(47);

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case OK:
//                   //数据源随机排列
//                   for (ItemBean bean:itemBeanList)
//                        itemBeanList.set(random.nextInt(itemBeanList.size()),bean);
                    adapter.notifyDataSetChanged();//更新
                    ly.setRefreshing(false);//隐藏刷新图标

                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.lv);

        ly = (SwipeRefreshLayout) findViewById(R.id.ly);
        ly.setOnRefreshListener(this);
        ly.setColorSchemeResources(android.R.color.holo_red_light);//刷新时中间有动画的图标的颜色


        itemBeanList = new ArrayList<ItemBean>();
        adapter = new myBaseAdapter(this, itemBeanList);
        animation = new ScaleAnimation(0F, 1F, 0F, 1F);
        controller = new LayoutAnimationController(animation);

        init();//实例化ListView
        listView.setAdapter(adapter);
        this.registerForContextMenu(listView);//为ListView注册上下文菜单


        /**
         * 滑动时启动动画
         */
        listView.setRecyclerListener(new AbsListView.RecyclerListener() {

            @Override
            public void onMovedToScrapHeap(View view) {
                animation.setDuration(300);//设置动画时长0.3s
//                controller.setInterpolator(new BounceInterpolator());//为动画添加插值器
                controller.setOrder(LayoutAnimationController.ORDER_NORMAL);//设置动画顺序
                controller.setDelay(0.2F);//设置延迟
                listView.setLayoutAnimation(controller);//为listView添加界面动画，这样每个item都有动画效果
                listView.startLayoutAnimation();//启动界面动画
            }
        });

        /**
         * 为ListView添加点击事件监听器,
         */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ItemBean bean = itemBeanList.get(position);
                Toast.makeText(MainActivity.this, bean.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });


    }


    /**
     * 重写该方法创建上下文菜单
     */

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("这是上下文菜单");//上下文菜单的标题
        menu.setHeaderIcon(R.mipmap.ic_launcher);//上下文菜单的图标

        //添加上下文菜单选项
        menu.add(1, 1, 1, "菜单选项1");
        menu.add(1, 2, 1, "菜单选项2");
        menu.add(1, 3, 1, "菜单选项3");

    }

    /**
     * 上下文菜单的点击事件
     *
     * @param item
     * @return
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                Toast.makeText(this, "点击了菜单选项1", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(this, "点击了菜单选项2", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Toast.makeText(this, "点击了菜单选项3", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onContextItemSelected(item);
    }

    /**
     * 向ListView中加载数据
     */
    private void init() {
        for (int i = 0; i < 200; i++)
            itemBeanList.add(new ItemBean(R.mipmap.ic_launcher, "标题" + i, "内容" + i));
    }


    /**
     * 刷新时进行的耗时操作
     */
    @Override
    public void onRefresh() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = OK;

                //数据源随机排列
                for (ItemBean bean : itemBeanList)
                    itemBeanList.set(random.nextInt(itemBeanList.size()), bean);
                handler.sendMessageDelayed(message,2000);//2s后发送message，用于观测效果
            }
        }).start();

//        handler.sendEmptyMessageDelayed(OK, 2000);//发送空message到messageQueue,时延2s便于观测效果
    }
}
