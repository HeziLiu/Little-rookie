package com.example.he.listview;

/**
 * ListView中每个Item的实体类
 * Created by he on 2016/11/16.
 */

public class ItemBean {
    private int image;
    private String title;
    private String content;


    public ItemBean(int image, String title, String content) {
        this.image = image;
        this.title = title;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public int getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }
}
