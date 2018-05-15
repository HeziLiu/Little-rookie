package com.example.he.gridviewtest;

/**
 * 每个Item对应的实体类
 * Created by he on 2016/10/27.
 */

public class Item {
    private int itemImageId;
    private String itemName;

    public Item(int itemImageId, String itemName) {
        this.itemImageId = itemImageId;
        this.itemName = itemName;
    }

    public int getItemImageId() {
        return itemImageId;
    }

    public String getItemName() {
        return itemName;
    }
}
