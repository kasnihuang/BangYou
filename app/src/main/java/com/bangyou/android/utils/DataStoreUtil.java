package com.bangyou.android.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class DataStoreUtil {
	
	public final static String   HISTORY_COUNT = "history_count";
	public final static String  CITY_ID = "city_id";
	public final static String  CITY_NAME = "city_name";
	public final static String  USER_NAME = "user_name";
	public final static String  USER_XH = "user_xh";
	public final static String  PASS_WORD ="pass_word";
	public final static String  NEWS_LATEST_TIME = "news_latest_time";
	
	public final static String   COLLECTION_COUNT = "collection_count";
	public final static String   COLLECTION_PREFIX = "my_collection_";
	
	
	public static String getString(Context context,String key){
	    final SharedPreferences sharedata = context.getSharedPreferences("data", Context.MODE_PRIVATE); 
	    return sharedata.getString(key, null);
	}
	public static void   putString(Context context,String key,String value){
		final SharedPreferences sharedata = context.getSharedPreferences("data", Context.MODE_PRIVATE);
	   SharedPreferences.Editor  editor = sharedata.edit();
	   editor.putString(key, value);
	   editor.commit();	   
	 //  Log.i("------  put  ","key= "+key+" value = "+value);
	}
	
	public static String print(Context context ,String fileName){
		
	     String path = "data/data/com.weipai.pumpmis/shared_prefs/data.xml";
	     
	     StringBuffer buff = new StringBuffer();  
	        try {  
	            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)));  
	            String str;  
	            while ((str = reader.readLine()) != null) {  
	                buff.append(str + "\n");  
	            }  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	        return buff.toString();  
	    } 
	
}
