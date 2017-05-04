package com.example.rumens.showtime.entity;

import java.io.Serializable;

/**
 * @author Zhaochen Ping
 * @create 2017/4/21
 * @description
 */

public class MultiItemEntity implements Serializable{
    protected int itemType;

    public MultiItemEntity(int itemType) {
        this.itemType = itemType;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }
}
