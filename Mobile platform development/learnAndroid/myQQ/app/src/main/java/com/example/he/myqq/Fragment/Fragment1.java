package com.example.he.myqq.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.he.myqq.R;
import com.example.he.myqq.UI.refreshlistview.PullToRefreshBase;
import com.example.he.myqq.UI.refreshlistview.PullToRefreshListView;
import com.example.he.myqq.UI.swipelistview.SwipeMenu;
import com.example.he.myqq.UI.swipelistview.SwipeMenuListView;
import com.example.he.myqq.utils.MessageBean;
import com.example.he.myqq.utils.MyApplication;
import com.example.he.myqq.NewsMessageActivity;
import com.example.he.myqq.utils.imageloader.loader.ImageLoader;
import com.example.he.myqq.utils.imageloader.utils.MyUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by he on 2017/1/29.
 */

public class Fragment1 extends Fragment implements AbsListView.OnScrollListener {

    private List<MessageBean> mAppList;
    private ImageLoader mImageLoader;
    private AppAdapter mAdapter;
    private PullToRefreshListView ptrlv;
    private Activity mActivity;
    private View mView;

    private boolean mIsListViewIdle = true;
    private boolean mCanGetBitmapFromNetWork = false;
    private boolean mIsWifi = false;

    private static final int LOAD_UP = 1;
    private static final int LOAD_DOWN = 2;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LOAD_UP:
                    loadUpMore();
                    ptrlv.setOnRefreshComplete();
                    break;
                case LOAD_DOWN:
                    loadBottomMore();
                    ptrlv.setOnRefreshComplete();
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1_layout, container, false);
        mView = view;
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = getActivity();
        mAppList = new ArrayList<MessageBean>();
        mImageLoader = ImageLoader.build(mActivity.getApplicationContext());
//        for (int i = 0; i < 20; i++) {
//            mAppList.add(new MessageBean("你好", "item" + i, "2017-1-30", "xxx"));
//        }

        ptrlv = (PullToRefreshListView) mView.findViewById(R.id.listView);
        initListView();
        initDate();
//        initView();
        ptrlv.setOnScrollListener(this);//ListView设置滑动监听

    }


    @Override
    public void onResume() {
        initView();
        super.onResume();

    }

    /**
     * ptrlv相关的一些初始化
     * setFunctionConfig  设置该控件的功能
     * REFRESH        下拉刷新，上拉加载更多
     * SWIPE          侧滑删除
     * ALL            以上两个功能都有
     */
    private void initListView() {
        ptrlv.init(MyApplication.getContext());
        ptrlv.setFunctionConfig(PullToRefreshListView.FunctionConfig.ALL);

        /**
         * 下拉刷新，上拉加载更多时有用
         */
        ptrlv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<SwipeMenuListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<SwipeMenuListView> refreshView) {
//                loadUpMore();

                handler.sendEmptyMessageDelayed(LOAD_UP, 2000);
//                ptrlv.setOnRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<SwipeMenuListView> refreshView) {
//                loadBottomMore();

                handler.sendEmptyMessageDelayed(LOAD_DOWN, 2000);
//                ptrlv.setOnRefreshComplete();
            }
        });

        /**
         * 设置listview条目的点击事件
         */
        ptrlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(mActivity, NewsMessageActivity.class);
                startActivity(intent);
            }
        });

        /**
         * 设置侧滑删除事件
         */
        ptrlv.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                mAppList.remove(position);
                mAdapter.notifyDataSetChanged();
                Toast.makeText(mActivity.getApplicationContext(), "delete" + position, Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        mAdapter = new AppAdapter();
        ptrlv.setAdapter(mAdapter);

    }


    /**
     * 上拉刷新需要加载加载的数据
     */
    protected void loadUpMore() {
//        SystemClock.sleep(2000);
        mAppList.add(new MessageBean("你好", "zcc", "2002", "xxx"));
//        mAppList.add("A");
        mAdapter.notifyDataSetChanged();


    }

    /**
     * 下拉加载更多需要加载的数据
     */

    protected void loadBottomMore() {
//        SystemClock.sleep(2000);

        /**
         * 根据获取的数据判断，上拉加载是否可用，没有更多数据时可以禁止
         */
        ptrlv.setPullLoadEnabled(true);

    }

    /**
     * 初始化数据
     */
    private void initDate() {
        String[] imageUrls = {
                "http://b.hiphotos.baidu.com/zhidao/pic/item/a6efce1b9d16fdfafee0cfb5b68f8c5495ee7bd8.jpg",
                "http://pic47.nipic.com/20140830/7487939_180041822000_2.jpg",
                "http://pic41.nipic.com/20140518/4135003_102912523000_2.jpg",
                "http://img2.imgtn.bdimg.com/it/u=1133260524,1171054226&fm=21&gp=0.jpg",
                "http://h.hiphotos.baidu.com/image/pic/item/3b87e950352ac65c0f1f6e9efff2b21192138ac0.jpg",
                "http://pic.58pic.com/58pic/13/00/22/32M58PICV6U.jpg",
                "http://picview01.baomihua.com/photos/20120629/m_14_634765948339062500_11778706.jpg",
                "http://h.hiphotos.baidu.com/zhidao/wh%3D450%2C600/sign=429e7b1b92ef76c6d087f32fa826d1cc/7acb0a46f21fbe09cc206a2e69600c338744ad8a.jpg",
                "http://pica.nipic.com/2007-12-21/2007122115114908_2.jpg",
                "http://cdn.duitang.com/uploads/item/201405/13/20140513212305_XcKLG.jpeg",
                "http://photo.loveyd.com/uploads/allimg/080618/1110324.jpg",
                "http://img4.duitang.com/uploads/item/201404/17/20140417105820_GuEHe.thumb.700_0.jpeg",
                "http://cdn.duitang.com/uploads/item/201204/21/20120421155228_i52eX.thumb.600_0.jpeg",
                "http://www.renyugang.cn/emlog/content/plugins/kl_album/upload/201004/852706aad6df6cd839f1211c358f2812201004120651068641.jpg"
        };
        int i = 97;
        for (String url : imageUrls) {
            Date currentTime = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
            String time = formatter.format(currentTime);
            mAppList.add(new MessageBean("你好", "" + (char) i++, time, url));
        }
        mIsWifi = MyUtils.isWifi(mActivity.getApplicationContext());
        if (mIsWifi) {
            mCanGetBitmapFromNetWork = true;
        }
    }


    /**
     * 如果无wifi弹出一个AlterDialog
     */
    private void initView() {
        if (!mIsWifi) {

            AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
            builder.setMessage("初次使用会从网络下载大概5MB的图片，确认要下载吗？");
            builder.setTitle("注意");
            builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mCanGetBitmapFromNetWork = true;
                    mAdapter.notifyDataSetChanged();
                }
            });
            builder.setNegativeButton("否", null);
            builder.show();

//            DialogFragment dialogFragment=new DialogFragment();
//            dialogFragment.



        }
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == NumberPicker.OnScrollListener.SCROLL_STATE_IDLE) {
            mIsListViewIdle = true;
            mAdapter.notifyDataSetChanged();
        } else {
            mIsListViewIdle = false;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }


    class AppAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mAppList.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            ViewHolder holder;
            MessageBean bean = mAppList.get(position);
            if (convertView == null) {
                view = LayoutInflater.from(mActivity.getApplication()).inflate(R.layout.item_list_app, parent, false);
                holder = new ViewHolder();
                holder.icon = (ImageView) view.findViewById(R.id.user_friends_pic);
                holder.name = (TextView) view.findViewById(R.id.user_friends_name);
                holder.info = (TextView) view.findViewById(R.id.user_friends_info);
                holder.time = (TextView) view.findViewById(R.id.user_friends_time);
                view.setTag(holder);
            } else {
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }

            ImageView imageView = holder.icon;
            final String uri = mAppList.get(position).getUri();
            final String tag = (String) imageView.getTag();
            if (!uri.equals(tag)) {
                imageView.setImageResource(R.mipmap.ic_launcher);
            }
            if (mIsListViewIdle && mCanGetBitmapFromNetWork) {
                imageView.setTag(uri);

                //异步加载图片
                mImageLoader.bindBitmap(uri, imageView, 100, 100);
//                mImageLoader.bindBitmap(uri, imageView, 300, 300);
            }
            holder.name.setText(bean.getName());
            holder.info.setText(bean.getInfo());
            holder.time.setText(bean.getTime());

            return view;
        }

        class ViewHolder {
            ImageView icon;
            TextView name;
            TextView info;
            TextView time;

        }

        @Override
        public Object getItem(int position) {
            return mAppList.get(position);
        }
    }


}
