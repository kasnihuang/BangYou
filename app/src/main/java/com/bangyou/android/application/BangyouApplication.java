package com.bangyou.android.application;

import android.app.Application;

import com.bangyou.android.datalayer.DataServiceForData;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * Created by zhongwq on 16/5/1.
 */
public class BangyouApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);
        DataServiceForData.Instance(this).initData();
    }
}
