package com.example.he.fragmentbestpractice;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 新闻标题碎片
 * Created by he on 2016/4/6.
 */
public class NewsTitleFragment extends Fragment implements AdapterView.OnItemClickListener {
    private ListView newsTitlleListView;
    private List<News> newsList;
    private NewsAdapter adapter;
    private boolean isTwoPane;

    //初始化新闻标题及内容(不同)
    private void getNews() {
        newsList = new ArrayList<News>();
        News news1 = new News();
        news1.setTitle("this is first newsTitle");
        news1.setContent("this is first newsContent");
        newsList.add(news1);
        News news2 = new News();
        news2.setTitle("this is second newsTitle");
        news2.setContent("this is second newsContent");
        newsList.add(news2);
    }


//    //将碎片与活动绑定并完成新闻的初始化
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        getNews();
        adapter = new NewsAdapter(activity, R.layout.news_item, newsList);
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_title_frag, container, false);
        newsTitlleListView = (ListView) view.findViewById(R.id.news_title_list_view);
        newsTitlleListView.setAdapter(adapter);
        newsTitlleListView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity().findViewById(R.id.news_content_layout) != null) {
            isTwoPane = true;//找到news_content_layout布局时为双页模式
        } else {
            isTwoPane = false;//找不到news_content_layout布局时为单页模式
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        News news = newsList.get(position);
        if (isTwoPane) {
            //如果是双页模式，刷新 NewsContentFragment中的内容
            NewsContentFragment newsContentFragment = (NewsContentFragment) getFragmentManager().findFragmentById(R.id.news_content_fragment);
            newsContentFragment.refresh(news.getTitle(),news.getContent());
        }
        else {
            //如果是单页模式，直接启动 NewsContentFragment
            NewsContentActivity.actionStart(getActivity(),news.getTitle(),news.getContent());
        }
    }
}
