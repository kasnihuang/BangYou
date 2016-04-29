package com.bangyou.android.dao;

/**
 * Created by kasni.huang on 4/28/16.
 */
public class OrderTypeInfo {
    private int drawableId;
    private int textId;
    private int count;

    public OrderTypeInfo(int drawableId, int textId, int count) {
        this.drawableId = drawableId;
        this.textId = textId;
        this.count = count;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }

    public int getTextId() {
        return textId;
    }

    public void setTextId(int textId) {
        this.textId = textId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
