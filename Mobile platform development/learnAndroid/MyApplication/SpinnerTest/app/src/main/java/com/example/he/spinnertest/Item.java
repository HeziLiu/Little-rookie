package com.example.he.spinnertest;

/**
 * Created by he on 2016/10/27.
 */

public class Item {
    private int imageID;
    private String name;

    public Item(int imageID, String name) {
        this.imageID = imageID;
        this.name = name;
    }

    public int getImageID() {
        return imageID;
    }

    public String getName() {
        return name;
    }
}
