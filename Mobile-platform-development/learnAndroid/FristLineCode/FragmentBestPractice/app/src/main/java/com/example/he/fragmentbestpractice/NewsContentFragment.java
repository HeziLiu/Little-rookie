package com.example.he.fragmentbestpractice;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 新闻内容碎片
 * Created by he on 2016/4/6.
 */
public class NewsContentFragment extends Fragment {
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.news_content_frag, container, false);
        return view;
    }


    //刷新新闻
    public void refresh(String title, String content) {
        View visibilityLayout = view.findViewById(R.id.visibility_layout);
        visibilityLayout.setVisibility(View.VISIBLE);
        TextView newsTitleText = (TextView) visibilityLayout.findViewById(R.id.news_title);
        TextView newsContentTest = (TextView) visibilityLayout.findViewById(R.id.news_content);

        newsTitleText.setText(title);//刷新标题
        newsContentTest.setText(content);//刷新内容
    }


}
