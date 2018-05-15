package com.example.he.asyncloding;

/**
 * Created by he on 2016/11/21.
 */

public class ItemBean {
    private String imageUrl;
    private String title;
    private String content;

    public ItemBean(String imageUrl, String content, String title) {
        this.content = content;
        this.imageUrl = imageUrl;
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }
}
