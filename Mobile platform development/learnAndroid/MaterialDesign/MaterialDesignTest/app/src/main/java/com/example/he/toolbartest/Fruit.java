package com.example.he.toolbartest;

/**
 * Javabean
 * Created by he on 2016/12/14.
 */

public class Fruit {

    private String name;
    private int imageId;

    public Fruit(String name, int nameId) {
        this.name = name;
        this.imageId = nameId;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }
}
