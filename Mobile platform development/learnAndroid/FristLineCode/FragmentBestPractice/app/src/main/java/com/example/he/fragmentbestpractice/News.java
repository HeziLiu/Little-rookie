package com.example.he.fragmentbestpractice;

/**
 * 实体类用于存放新闻标题和内容
 * Created by he on 2016/4/5.
 */

public class News {
    private  String title;
    private  String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
