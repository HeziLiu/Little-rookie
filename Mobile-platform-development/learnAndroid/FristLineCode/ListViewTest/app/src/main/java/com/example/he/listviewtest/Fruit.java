package com.example.he.listviewtest;

/**
 * Created by he on 2016/3/31.
 */
public class Fruit {
    private String name;
    private int imageId;

    public Fruit(int imageId, String name) {
        this.imageId = imageId;
        this.name = name;
    }

    public int getImageId() {
        return imageId;
    }

    public String getName() {
        return name;
    }
}
