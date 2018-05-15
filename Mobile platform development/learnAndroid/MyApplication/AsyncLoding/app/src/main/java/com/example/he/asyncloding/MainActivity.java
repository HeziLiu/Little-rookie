package com.example.he.asyncloding;

import android.os.AsyncTask;
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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 此项目包含的内容：1、ListView的item布局以及加载优化
 * 2、从网络中加载ListView的item（异步的方式），使用LruCache缓存BitMap
 * 3、ListView的下拉刷新，刷新一次更新ListView中的数据（从网络中获取资源只有30条）
 * 4、ListView的点击事件,需要使用全局Context
 * 5、创建上下文菜单,长按ListView中的item弹出菜单选项
 * 6、ListView的滚动动画效果（效果不理想）
 */


public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private ListView listView;
    private List<ItemBean> itemList;
    private myAdapter adapter;
    private ItemBean tempArray[];
    private volatile int tempIndex;

    private SwipeRefreshLayout refreshLayout;//下拉刷新，官方提供的工具
    private ScaleAnimation animation;//一个缩放动画
    private LayoutAnimationController controller;//界面动画控制器


    private static final String URL = "http://www.imooc.com/api/teacher?type=4&num=30";//JSON数据的URL
    private static final int OK = 1;
    private NewsAsyncTask task;
    private ProgressBar bar;
    private static boolean first = true;//如果刚刷新了一次页面则为true,用于刷新页面后不自动加载，必须滑动才能加载的情况

    public void setFirst(boolean first) {
        this.first = first;
    }

    public boolean isFirst() {
        return first;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case OK:
                    int i;
                    int j = 0;
                    //更新ListView中的数据，在这更新的原因是，如果在onRefresh()方法中更新，在刷新时滑动ListView程序会crash
                    for (i = (tempIndex % tempArray.length); i < (tempIndex % tempArray.length) + tempArray.length / 2; i++) {
                        itemList.set(j++, tempArray[i % tempArray.length]);
                    }
                    tempIndex = i;
                    first = true;
                    adapter.notifyDataSetChanged();//更新数据
                    refreshLayout.setRefreshing(false);//隐藏刷新图标
                    listView.setEnabled(true);
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.lv);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_light);
        bar = (ProgressBar) findViewById(R.id.progress_bar);
        task = new NewsAsyncTask();
        task.execute(URL);

        animation = new ScaleAnimation(0F, 1F, 0F, 1F);
        controller = new LayoutAnimationController(animation);
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


    }


    /**
     * 刷新数据
     */
    @Override
    public void onRefresh() {
        handler.sendEmptyMessageDelayed(OK, 2000);
    }

    class NewsAsyncTask extends AsyncTask<String, Void, List<ItemBean>> {
        @Override
        protected void onPreExecute() {
            listView.setVisibility(View.GONE);
            super.onPreExecute();
        }

        @Override
        protected List<ItemBean> doInBackground(String... params) {
            return getData(params[0]);
        }

        //
        @Override
        protected void onPostExecute(List<ItemBean> list) {
            super.onPostExecute(list);
            listView.setVisibility(View.VISIBLE);
            bar.setVisibility(View.GONE);
            itemList = new ArrayList<ItemBean>();
            tempArray = new ItemBean[list.size()];
            tempArray = list.toArray(tempArray);
//            synchronized (this) {
            int i;
            for (i = 0; i < tempArray.length / 2; i++) {
                itemList.add(tempArray[i]);
            }
            tempIndex = i;
//            }

//            itemList.addAll(list);
            adapter = new myAdapter(itemList, MainActivity.this, listView, new MainActivity());
            listView.setAdapter(adapter);
        }
    }


    /**
     * 返回一个添加ItemBean的List
     *
     * @param url
     * @return
     */

    private List<ItemBean> getData(String url) {
        List<ItemBean> list = new ArrayList<ItemBean>();
        try {
            //URL.openStream 方法已将将网络连接部分封装好了
            //等同于new URL(url).openConnection().getInputStream();
            String json = readStream(new URL(URL).openStream());

            /**
             * 解析JSON
             */

            JSONObject object;
            ItemBean item;

            object = new JSONObject(json);
            JSONArray array = object.getJSONArray("data");
            //jsonArray中每个元素都是jasonObject
            for (int i = 0; i < array.length(); i++) {
                object = array.getJSONObject(i);
                String imageUrl = object.getString("picSmall");
                String title = object.getString("name");
                String content = object.getString("description");
                item = new ItemBean(imageUrl, title, content);
                list.add(item);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;

    }

    /**
     * 返回JSON格式的字符串
     *
     * @param inputStream
     * @return
     */

    private String readStream(InputStream inputStream) {
        String result = "";
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String line = "";

            while ((line = reader.readLine()) != null) {
                result += line;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
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

}

