package com.bangyou.android.datalayer;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;

import com.bangyou.android.R;
import com.bangyou.android.dao.MessageInfo;
import com.bangyou.android.dao.OrderInfo;
import com.bangyou.android.utils.Constants;
import com.bangyou.android.utils.DataStoreUtil;
import com.bangyou.android.utils.Utils;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhongwq on 16/5/1.
 */
public class DataServiceForData {
    private Context m_context;
    private static DataServiceForData s_DataServiceForData;
    public static DataServiceForData Instance(Context context){
        if(s_DataServiceForData == null){
            s_DataServiceForData = new DataServiceForData(context);
        }
        return s_DataServiceForData;
    }
    public DataServiceForData(Context context){
        this.m_context = context;

    }
    public void initData(){
        if(!isInited()){
            this.createMessages();
            this.createOrders();
            this.setInited();
            OrderInfo forNotfication = DataService.Instance().getRandOderInfo();
            if(forNotfication != null)
              Utils.playNotification(forNotfication, m_context);
        }
        else {
            DataService.Instance().loadMsgInfo(m_context);
            DataService.Instance().loadOrderInfo(m_context);
        }
    }

    private Map<String, OrderInfo> loadOrderInfoLocal(){

        return null;

    }

    private Map<String, MessageInfo> messageInfoLocal(){
        return null;

    }

    private final static String  INITDATA_KEY = "DATA_INITED";

    private boolean isInited(){
        String  initFlag = DataStoreUtil.getString(m_context, INITDATA_KEY);
        return !TextUtils.isEmpty(initFlag);
    }
    private void setInited(){
        DataStoreUtil.putString(m_context, INITDATA_KEY, "inited");
    }
    private void createMessages(){
        Resources res = m_context.getResources();
        String[]  msg = res.getStringArray(R.array.messages);
        List<MessageInfo> msgList = new ArrayList<MessageInfo>();
        for(int i = 0 ; i < msg.length; ++i){
            String[] msgs = msg[i].split("\\|");
            String title   = msgs[0];
            String subTile = msgs[1];
            String msgType = msgs[2];

            MessageInfo msgInfo = new MessageInfo();
            msgInfo.setUid(Utils.uuidGenerate());
            if(msgType.equals("order"))
                msgInfo.setMessageType(MessageInfo.MSG_TYPE_ROB);
            else
                msgInfo.setMessageType(MessageInfo.MSG_TYPE_NORMAL);
            msgInfo.setTitle(title);
            msgInfo.setSubtitle(subTile);
            msgList.add(msgInfo);
        }
        DataService.Instance().setMessages(msgList);
        DataService.Instance().saveMsg(m_context);
    }

    private  void createOrders(){
        Resources res = m_context.getResources();
        String[]  orderTitles = res.getStringArray(R.array.orders);
        String[]  addrList   = res.getStringArray(R.array.orders_addr);
        String[]  contackers  = res.getStringArray(R.array.contacters);
        String[]  contactTime = res.getStringArray(R.array.contact_time);
        String[]  telNos = res.getStringArray(R.array.telNo);

        Map<String, OrderInfo> orderMap = new HashMap<String, OrderInfo>();
        for(int i = 0 ; i < orderTitles.length; ++i){
            String orderTitle = orderTitles[i];
            OrderInfo orderInfo = new OrderInfo(orderTitle, Constants.ORDER_TYPE_ACCEPTED);
            orderInfo.setUuid(Utils.uuidGenerate());
            orderInfo.setAddr(addrList[i%addrList.length]);
            orderInfo.setContactUser(contackers[i%contackers.length]);
            orderInfo.setContacted(false);
            Date orderInstallTime = Utils.coverStringToDate(contactTime[i%contactTime.length], Constants.TIME_FORMAT);
            orderInfo.setContactTime(Utils.subDate(orderInstallTime, 24));
            orderInfo.setTeleNo(telNos[i%telNos.length]);
            orderInfo.setInstallTime(orderInstallTime);
            orderMap.put(orderInfo.getUuid(), orderInfo);
        }
        DataService.Instance().setOrders(orderMap);
        DataService.Instance().saveOrder(m_context);
    }
}
