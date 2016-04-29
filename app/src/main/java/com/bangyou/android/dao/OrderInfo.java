package com.bangyou.android.dao;

/**
 * Created by kasni.huang on 4/27/16.
 */
public class OrderInfo {
    private String orderContent;
    private int orderType;

    public OrderInfo(String orderContent, int orderType) {
        this.orderContent = orderContent;
        this.orderType = orderType;
    }

    public String getOrderContent() {
        return orderContent;
    }

    public void setOrderContent(String orderContent) {
        this.orderContent = orderContent;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }
}
