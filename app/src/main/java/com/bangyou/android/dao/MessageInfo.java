package com.bangyou.android.dao;

/**
 * Created by kasni.huang on 4/27/16.
 */
public class MessageInfo {
    public static final int MSG_TYPE_ROB = 0;
    public static final int MSG_TYPE_NORMAL = 1;

    private String messageContent;
    private int messageType;

    public MessageInfo(String messageContent, int messageType) {
        this.messageContent = messageContent;
        this.messageType = messageType;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }
}
