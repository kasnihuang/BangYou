package com.bangyou.android.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by zhongwq on 16/5/1.
 */
public class Utils {
    public static String uuidGenerate() {
        return UUID.randomUUID().toString();
    }

    //把日期转为字符串
    public static String ConverToString(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return df.format(date);
    }
}
