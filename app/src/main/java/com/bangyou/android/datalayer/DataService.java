package com.bangyou.android.datalayer;

import android.content.Context;

import com.alibaba.fastjson.TypeReference;
import com.bangyou.android.dao.MessageInfo;
import com.bangyou.android.dao.OrderInfo;
import com.bangyou.android.db.DBHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by zhongwq on 16/5/1.
 */
public class DataService {

    public static DataService  s_DataService;
    public static DataService Instance(){
        if(s_DataService == null){
            s_DataService = new DataService();
        }
        return s_DataService;
    }

    public Map<String, OrderInfo> getOrders() {
        return orders;
    }

    public void setOrders(Map<String, OrderInfo> orders) {
        this.orders = orders;
    }

    public List<MessageInfo> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageInfo> messages) {
        this.messages = messages;
    }

    public OrderInfo  getOrder(String orderId){
        return orders.get(orderId);
    }
    public OrderInfo  getRandOderInfo(){
        Iterator<OrderInfo> orderIter = orders.values().iterator();
        if(orderIter.hasNext()){
            return orderIter.next();
        }
        return null;
    }

    public MessageInfo getMessage(String uid){
        for(MessageInfo msg : messages){
            if(msg.getUid().equals(uid)){
                return msg;
            }
        }
        return null;
    }

    public void saveMsg(Context context){
        DBHelper.insertObject(context, DBHelper.MSG_ID, messages);
    }
    public void saveOrder(Context context){
        DBHelper.insertObject(context, DBHelper.ORDER_ID, orders);
    }

    public void loadOrderInfo(Context context){
        orders = DBHelper.getObject(context, DBHelper.ORDER_ID, new TypeReference<Map<String, OrderInfo>>(){});
    }

    public void loadMsgInfo(Context context){
        messages = DBHelper.getObject(context, DBHelper.MSG_ID, new TypeReference<List<MessageInfo>>(){});
    }

    public void addMessageInfo(MessageInfo msgInfo){
        messages.add(msgInfo);
    }
    public void addOrder(OrderInfo orderInfo){
        orders.put(orderInfo.getUuid(), orderInfo);
    }


    private Map<String, OrderInfo>  orders = new HashMap<String, OrderInfo>();
    private List<MessageInfo> messages  = new ArrayList<MessageInfo>();
}
