package com.example.he.recycleviewtest;

/**
 * Created by he on 2016/12/9.
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
