package com.ifly.shendong.smarthomelayout;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by shendong on 2017/3/28.
 */

public class ViewUtils {

    private static final String TAG = ViewUtils.class.getSimpleName();

    private static PackageManager pm;
    private static Context mContext;
    private ImageView image;

    private List<String> titleList;
    private Map<String, List<String[]>> mDatas;


    private GridView mGridView;
    private GridView mDeviceManage;

    public View initSmartHome(final Context context, @NonNull Map<String, List<String[]>> datas) {
        mContext = context;
        mDatas = datas;
        pm = context.getPackageManager();
        View view = LayoutInflater.from(context).inflate(R.layout.smart_base_layout, null, false);

        mGridView = (GridView) view.findViewById(R.id.grid_view);
        image = (ImageView) view.findViewById(R.id.more);
        mDeviceManage= (GridView) view.findViewById(R.id.device_manage);

        initTitle(view, datas.keySet());
        return view;
    }


    private void initTitle(final View view, final Set<String> title) {
        ListView titleListView = (ListView) view.findViewById(R.id.title_list_view);
        titleList = new ArrayList<>();
        for (String t : title) {
            titleList.add(t);
        }
        TitleAdapter adapter = new TitleAdapter(titleList);
        titleListView.setAdapter(adapter);
        titleOnItemClickAndRereshLayout(titleListView);
        titleOnItemSelectedAndRereshLayout(titleListView);


    }

    private void titleOnItemClickAndRereshLayout(ListView titleListView) {

        titleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(mContext, "xxx", Toast.LENGTH_SHORT).show();
                view.setBackgroundResource(R.drawable.smart_select);
                view.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.title_select_anim));
                refreshLayout(position);
            }
        });


//        titleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                view.clearAnimation();
//                view.setBackgroundResource(R.drawable.title_select);
//                refreshLayout(position);
//            }
//        });
    }


    private void titleOnItemSelectedAndRereshLayout(final ListView titleListView) {

        final View[] preView = new View[1];
        titleListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, final View view, final int position, long id) {

                view.setBackgroundResource(R.drawable.smart_select);
                view.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.title_select_anim));

                if (preView[0] != null) {
                    preView[0].startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.unselect_anim));
                    preView[0].setBackgroundResource(0);
                }
                preView[0] = view;


                //焦点移动到右边后更改背景
                titleListView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus == false) {
                            view.clearAnimation();
                            view.setBackgroundResource(0);
                            ViewGroup ly = (ViewGroup) view;
                            ViewGroup ly2 = (ViewGroup) ly.getChildAt(0);
                            View tv = ly2.getChildAt(0);
                            tv.setBackgroundResource(R.drawable.title_select);

                        } else {
                            view.setBackgroundResource(R.drawable.smart_select);
                            view.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.title_select_anim));
                            ViewGroup ly = (ViewGroup) view;
                            ViewGroup ly2 = (ViewGroup) ly.getChildAt(0);
                            View tv = ly2.getChildAt(0);
                            tv.setBackgroundResource(0);
                        }
                    }
                });

                refreshLayout(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });
    }


    private void refreshLayout(int position) {

        //更新场景模式
        if (position == 0) {
            initScene( mDatas.get(titleList.get(position)));
        }
        //更新设备管理
        else if (position == titleList.size() - 1) {
            initDevice( mDatas.get(titleList.get(position)));
        } else {
            initRom( mDatas.get(titleList.get(position)));
        }

    }


    private void initScene( List<String[]> sceneDatasList) {
        final View[] preView = {null};
        mDeviceManage.setVisibility(View.GONE);
        mGridView.setAdapter(null);
        final MySceneAdapter adapter = new MySceneAdapter(sceneDatasList);
        mGridView.setAdapter(adapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //position 
                Toast.makeText(parent.getContext(), "点击了"+position, Toast.LENGTH_SHORT).show();
                view.startAnimation(AnimationUtils.loadAnimation(parent.getContext(), R.anim.select_anim));
                if (preView[0] != null) {
                    preView[0].startAnimation(AnimationUtils.loadAnimation(parent.getContext(), R.anim.unselect_anim));
                }
                preView[0] = view;
                adapter.setSelectItem(position, view);
            }
        });

//        mGridView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                adapter.setSelectItem(position, view);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

    }

    private void initRom(List<String[]> romDatasList) {
        final View[] preView = {null};
        mDeviceManage.setVisibility(View.GONE);
        mGridView.setAdapter(null);
        final MyRomAdapter adapter = new MyRomAdapter(romDatasList);
        mGridView.setAdapter(adapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(parent.getContext(), "点击了", Toast.LENGTH_SHORT).show();
                view.startAnimation(AnimationUtils.loadAnimation(parent.getContext(), R.anim.select_anim));
                if (preView[0] != null) {
                    preView[0].startAnimation(AnimationUtils.loadAnimation(parent.getContext(), R.anim.unselect_anim));
                }
                preView[0] = view;
                adapter.setSelectItem(position, view);
            }
        });


    }

    private void initDevice(List<String[]> deviceDatasList) {
        final View[] preView = {null};
        mGridView.setAdapter(null);
        mDeviceManage.setVisibility(View.VISIBLE);
        final MyDeviceAdapter adapter = new MyDeviceAdapter(deviceDatasList);
        mDeviceManage.setAdapter(adapter);
        mDeviceManage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(parent.getContext(), "点击了", Toast.LENGTH_SHORT).show();
                view.startAnimation(AnimationUtils.loadAnimation(parent.getContext(), R.anim.select_anim));
                if (preView[0] != null) {
                    preView[0].startAnimation(AnimationUtils.loadAnimation(parent.getContext(), R.anim.unselect_anim));
                }
                preView[0] = view;
                adapter.setSelectItem(position, view);
            }
        });
    }

}


class TitleAdapter extends BaseAdapter {

    private List<String> mList;

    TitleAdapter(List<String> list) {
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.title_item, null, false);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) view.findViewById(R.id.title_);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.title.setText(mList.get(position));
        return view;
    }

    @Override
    public boolean isEnabled(int position) {
        return super.isEnabled(position);
    }

    class ViewHolder {
        TextView title;
    }
}

class MySceneAdapter extends BaseAdapter {

    private List<String[]> datasList;
    private int selectPosition = -1;
    private View preView = null;

    public MySceneAdapter(List<String[]> datasList) {
        this.datasList = datasList;
    }

    @Override
    public int getCount() {
        return datasList.size();
    }

    @Override
    public Object getItem(int position) {
        return datasList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setSelectItem(int position, View view) {
        selectPosition = position;
        preView = view;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;
        if (convertView == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.scene_layout, parent, false);
            holder = new ViewHolder();
            holder.sceneImage = (ImageView) view.findViewById(R.id.scene_image);
            holder.sceneName = (TextView) view.findViewById(R.id.scene_name);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
//        holder.sceneImage.setBackgroundResource(Integer.valueOf(datasList.get(position)[0]));
//        holder.sceneName.setText(datasList.get(position)[1]);
        holder.sceneName.setText(datasList.get(position)[0]);

        if (selectPosition == position) {
            view.startAnimation(AnimationUtils.loadAnimation(parent.getContext(), R.anim.select_anim));
        } else if (preView != null) {
            preView.startAnimation(AnimationUtils.loadAnimation(parent.getContext(), R.anim.unselect_anim));
        }

        return view;
    }

    class ViewHolder {
        ImageView sceneImage;
        TextView sceneName;
    }

}


class MyRomAdapter extends BaseAdapter {

    private List<String[]> datasList;
    private int selectPosition = -1;
    private View preView = null;

    public MyRomAdapter(List<String[]> datasList) {
        this.datasList = datasList;
    }

    @Override
    public int getCount() {
        return datasList.size();
    }

    @Override
    public Object getItem(int position) {
        return datasList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setSelectItem(int position, View view) {
        selectPosition = position;
        preView = view;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;
        if (convertView == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rom_layout, parent, false);
            holder = new ViewHolder();
            holder.deviceImage = (ImageView) view.findViewById(R.id.rom_image);
            holder.deviceName = (TextView) view.findViewById(R.id.rom_name);
            holder.deviceState = (TextView) view.findViewById(R.id.rom_state);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.deviceImage.setBackgroundResource(Integer.valueOf(datasList.get(position)[0]));
        holder.deviceName.setText(datasList.get(position)[1]);
//        holder.deviceState.setText(datasList.get(position)[2]);

        if (selectPosition == position) {
            view.startAnimation(AnimationUtils.loadAnimation(parent.getContext(), R.anim.select_anim));
        } else if (preView != null) {
            preView.startAnimation(AnimationUtils.loadAnimation(parent.getContext(), R.anim.unselect_anim));
        }

        return view;
    }

    class ViewHolder {
        ImageView deviceImage;
        TextView deviceName;
        TextView deviceState;
    }

}


class MyDeviceAdapter extends BaseAdapter {

    private List<String[]> datasList;
    private int selectPosition = -1;
    private View preView = null;
    private static PackageManager pm;

    public MyDeviceAdapter(List<String[]> datasList) {
        this.datasList = datasList;
    }

    @Override
    public int getCount() {
        return datasList.size();
    }

    @Override
    public Object getItem(int position) {
        return datasList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setSelectItem(int position, View view) {
        selectPosition = position;
        preView = view;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;
        if (convertView == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_layout, parent, false);
            holder = new ViewHolder();
            holder.deviceImage = (ImageView) view.findViewById(R.id.device_ic);
            holder.deviceName = (TextView) view.findViewById(R.id.device_name);
            view.setTag(holder);
            if (pm == null) {
                pm = parent.getContext().getPackageManager();
            }
        } else {
            holder = (ViewHolder) view.getTag();
        }

        String packName = datasList.get(position)[0];
        Drawable d = getAppIcon(packName);
        String name = getAppName(packName);

        holder.deviceImage.setImageDrawable(d);
        holder.deviceName.setText(name);

        if (selectPosition == position) {
            view.startAnimation(AnimationUtils.loadAnimation(parent.getContext(), R.anim.select_anim));
        } else if (preView != null) {
            preView.startAnimation(AnimationUtils.loadAnimation(parent.getContext(), R.anim.unselect_anim));
        }

        return view;
    }

    class ViewHolder {
        ImageView deviceImage;
        TextView deviceName;
    }


    private String getAppName(String packName) {
        try {
            ApplicationInfo info = pm.getApplicationInfo(packName, 0);
            return info.loadLabel(pm).toString();
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    private Drawable getAppIcon(String packName) {
        try {
            ApplicationInfo info = pm.getApplicationInfo(packName, 0);
            return info.loadIcon(pm);
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
        return null;
    }


}





