package com.bangyou.android.datalayer;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;

import com.bangyou.android.R;
import com.bangyou.android.dao.MessageInfo;
import com.bangyou.android.dao.OrderInfo;
import com.bangyou.android.utils.Constants;
import com.bangyou.android.utils.DataStoreUtil;
import com.bangyou.android.utils.Utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.tz.UTCProvider;

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
            String title = msg[i].split("|")[0];
            String subTile = msg[i].split("|")[1];
            MessageInfo msgInfo = new MessageInfo();
            msgInfo.setMessageContent("content");
            msgInfo.setTile(title);
            msgInfo.setSubTitle(subTile);
            msgList.add(msgInfo);
        }
        DataService.Instance().setMessages(msgList);
        DataService.Instance().saveMsg(m_context);
    }

    private  void createOrders(){
        Resources res = m_context.getResources();
        String[]  orderTitles = res.getStringArray(R.array.orders);
        String[]  addrList   = res.getStringArray(R.array.orders);
        String[]  contackers  = res.getStringArray(R.array.contacters);
        String[]   contactTime = res.getStringArray(R.array.contact_time);
        String[]  telNos = res.getStringArray(R.array.telNo);
        DateTimeFormatter format = DateTimeFormat.forPattern(Constants.TIME_FORMAT);
        Map<String, OrderInfo> orderMap = new HashMap<String, OrderInfo>();
        for(int i = 0 ; i < orderTitles.length; ++i){
            String orderTitle = orderTitles[i];
            OrderInfo orderInfo = new OrderInfo(orderTitle, Constants.ORDER_TYPE_ACCEPTED);
            orderInfo.setUuid(Utils.uuidGenerate());
            orderInfo.setAddr(addrList[i%addrList.length]);
            orderInfo.setContactUser(contackers[i%contackers.length]);
            orderInfo.setContacted(false);
            DateTime orderConcateTime = DateTime.parse(contactTime[i%contactTime.length], format);
            orderInfo.setContactTime(new Date());
            orderInfo.setTeleNo(telNos[i%telNos.length]);
            orderInfo.setInstallTime(new Date());
            orderMap.put(orderInfo.getUuid(), orderInfo);
        }
        DataService.Instance().setOrders(orderMap);
        DataService.Instance().saveOrder(m_context);
    }
}
