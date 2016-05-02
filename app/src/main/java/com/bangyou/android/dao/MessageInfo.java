package com.bangyou.android.dao;


/**
 * Created by kasni.huang on 4/27/16.
 */
public class MessageInfo {
    public static final int MSG_TYPE_ROB = 0;
    public static final int MSG_TYPE_NORMAL = 1;
    private String uid;

    private String title;
    private String subtitle;
    private int messageType;


    public MessageInfo(String title, String subtitle, int messageType) {
        this.title = title;
        this.subtitle = subtitle;
        this.messageType = messageType;
    }
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public MessageInfo(){

    }


    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }
}
