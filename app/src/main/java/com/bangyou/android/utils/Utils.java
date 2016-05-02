/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bangyou.android.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.NotificationCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.bangyou.android.R;
import com.bangyou.android.dao.OrderInfo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class containing some static utility methods.
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
    
    public static String token;
    
    public static void initToken(Context context){
        TelephonyManager tmanager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        StringBuffer sb = new StringBuffer();
        String deviceId = tmanager.getDeviceId();
        if(!TextUtils.isEmpty(deviceId))
        {
            sb.append(deviceId);
        }
        
        sb.append("" + System.currentTimeMillis());
        
        token = md5(sb.toString());
    }
    

    
    
    public static String md5(String in)
    {
        try
        {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.reset();
            digest.update(in.getBytes());
            byte[] a = digest.digest();
            int len = a.length;
            StringBuilder sb = new StringBuilder(len << 1);
            for (int i = 0; i < len; i++)
            {
                sb.append(Character.forDigit((a[i] & 0xf0) >> 4, 16));
                sb.append(Character.forDigit(a[i] & 0x0f, 16));
            }
            return sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static int getNowHourOfDay(){
    	Calendar calendar = Calendar.getInstance();
    	return calendar.get(Calendar.HOUR_OF_DAY);
    }
    
    @SuppressLint("SimpleDateFormat") 
    public static String  getNowHourOfDayStr(long selecteTime,int offer){
    	Calendar  calendar = Calendar.getInstance();
    	calendar.setTimeInMillis(selecteTime);
    	int nowHour = calendar.get(Calendar.HOUR_OF_DAY);
        calendar.set(Calendar.HOUR_OF_DAY, nowHour - offer);
    	String soapDatatPattern = "yyyy-MM-dd'T'HH:mm:ss";
		SimpleDateFormat sFormat = new SimpleDateFormat(soapDatatPattern);
		return sFormat.format(calendar.getTime());
    }
    
    @SuppressLint("SimpleDateFormat") 
    public static String getNowTimeStr(){
    	Calendar  calendar = Calendar.getInstance();
    	String soapDatatPattern = "yyyy-MM-dd'T'HH:00:00";
		SimpleDateFormat sFormat = new SimpleDateFormat(soapDatatPattern);
		return sFormat.format(calendar.getTime());
    }

    @SuppressLint("SimpleDateFormat")
    public static String getNowDayStr(){
        Calendar calendar = Calendar.getInstance();
        String timeFormat = "yyyy-MM-dd";
        SimpleDateFormat sFormat = new SimpleDateFormat(timeFormat);
        return sFormat.format(calendar.getTime());
    }
    
  
    
    @SuppressLint("SimpleDateFormat")    
    public static String  getNowDayOfMonthStr(long selectedTime,int offerDay){
    	String soapDatatPattern = "yyyy-MM-dd'T'HH:mm:ss";
 
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTimeInMillis(selectedTime);
    	int nowDay = calendar.get(Calendar.DAY_OF_MONTH);
    	calendar.set(Calendar.DAY_OF_MONTH, nowDay - offerDay);
    	//calendar.set(Calendar.DAY_OF_MONTH, 1);
    	SimpleDateFormat sFormat = new SimpleDateFormat(soapDatatPattern);
    	return sFormat.format(calendar.getTime());
    	
    	/*int monthDayCount = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    	calendar.set(Calendar.DAY_OF_MONTH, monthDayCount);
    	beginEnds[1]=sFormat.format(calendar.getTime());
    	return beginEnds;*/
    }
    
    @SuppressLint("SimpleDateFormat") public static String  getShowTime(String strTime){
    	
    	String soapDatatPattern = "yyyy-MM-dd'T'HH:mm:ss";
    	String  showDataPattern =  "yyyy/MM/dd HH:mm:ss";
    	SimpleDateFormat origiFormat = new SimpleDateFormat(soapDatatPattern);
    	Date origiDate = null;
    	try {
			 origiDate = origiFormat.parse(strTime);
		} catch (ParseException e) {
			return strTime;
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} 
    	SimpleDateFormat showFormat = new SimpleDateFormat(showDataPattern);
       return showFormat.format(origiDate);	
    }
    
    @SuppressLint("SimpleDateFormat") public static String  getNewsShowTime(String strTime){
    
    	
    	String soapDatatPattern = "yyyy-MM-dd'T'HH:mm:ss";
    	String  showDataPattern =  "yyyy/MM/dd";
    	SimpleDateFormat origiFormat = new SimpleDateFormat(soapDatatPattern);
    	Date origiDate = null;
    	try {
			 origiDate = origiFormat.parse(strTime);
		} catch (ParseException e) {
			return strTime;
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} 
    	SimpleDateFormat showFormat = new SimpleDateFormat(showDataPattern);
       return showFormat.format(origiDate);	
    }
    
    @SuppressLint("SimpleDateFormat")   
    public static String getNowMothOfYeadStr(long selctedTime,int offerMonth){
      	String soapDatatPattern = "yyyy-MM-dd'T'HH:mm:ss";
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTimeInMillis(selctedTime);
    	int nowMonth= calendar.get(Calendar.MONTH);
    	calendar.set(Calendar.MONTH, nowMonth - offerMonth);
    	//calendar.set(Calendar.DAY_OF_MONTH, 1);
    	SimpleDateFormat sFormat = new SimpleDateFormat(soapDatatPattern);
    	return sFormat.format(calendar.getTime());	
    }
    @SuppressLint("SimpleDateFormat")  
    public static String getNowYearOfYears(long selectedTime,int offerYear){ 	
      	String soapDatatPattern = "yyyy-MM-dd'T'HH:mm:ss";
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTimeInMillis(selectedTime);
    	int nowYear = calendar.get(Calendar.YEAR);
    	calendar.set(Calendar.YEAR, nowYear - offerYear);
    	//calendar.set(Calendar.DAY_OF_MONTH, 1);
    	SimpleDateFormat sFormat = new SimpleDateFormat(soapDatatPattern);
    	return sFormat.format(calendar.getTime());	
    }

    public void saveBitmap(Bitmap bm, String name) {
        String path = Environment.getExternalStorageDirectory() + "/wps";
        File f = new File(path);
        if (!f.exists()) {
            f.mkdirs();
        }
        File inFile = new File(path, name + ".jpg");
        if (inFile.exists()) {
            inFile.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(inFile);
            bm.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Bitmap getHeaderBitmap(Context context) {
        String path = Environment.getExternalStorageDirectory() + "/wps/" + DataStoreUtil.getString(context, DataStoreUtil.USER_NAME) + "";
        File mfile=new File(path);
        if (mfile.exists()) {//若该文件存在
            return BitmapFactory.decodeFile(path);
        }
        return null;
    }
  
    
    public static int getNowDayofMoth(){
    	Calendar calendar = Calendar.getInstance();
    	return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static boolean isEmail(String email){
        if (null==email || "".equals(email)) return false;
        //Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
        Pattern p =  Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }

    //以下是关键，原本uri返回的是file:///...来着的，android4.4返回的是content:///...
    @SuppressLint("NewApi")
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri The Uri to query.
     * @param selection (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
    
    public static String get8LenStr(String str){
    	if(str.length() > 10){
    		String subStr = str.substring(0,10);
    		return subStr+"...";
    	}
    	return str;
    }
    
    public static String getLineCharShowDay(String iDate,int offset)
    {
      	String soapDatatPattern = "yyyy-MM-dd HH-mm";
    	Calendar calendar = Calendar.getInstance();
    	int nowDay= calendar.get(Calendar.DAY_OF_YEAR);
    	calendar.set(Calendar.MONTH, nowDay - offset);
    	SimpleDateFormat sFormat = new SimpleDateFormat(soapDatatPattern);
    	return sFormat.format(calendar.getTime());	
    }
    
    public static TimeOffSetRet getLineCharHourShow(long selectedTime,int offset){  
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTimeInMillis(selectedTime);
    	int nowHour = calendar.get(Calendar.HOUR_OF_DAY);
    	int disHour = nowHour - offset;
    	calendar.set(Calendar.HOUR_OF_DAY, disHour);
    	long now = System.currentTimeMillis();
    	if(calendar.getTimeInMillis() > now){
    		return null;
    	}
    	String hourPattern = "HH:mm";
    	SimpleDateFormat sFormat = new SimpleDateFormat(hourPattern);
    	TimeOffSetRet  ret = new TimeOffSetRet();
    	ret.strTime= sFormat.format(calendar.getTime());
    	ret.selectedTime = calendar.getTimeInMillis();
    	return ret ;
    }
    
    public static TimeOffSetRet getLineCharDayShow(long selectedTime,int offset){
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTimeInMillis(selectedTime);
    	int  nowDay =calendar.get(Calendar.DAY_OF_MONTH);
    	nowDay -= offset;
    	calendar.set(Calendar.DAY_OF_MONTH, nowDay);
    	long now = System.currentTimeMillis();
    	long offSetTime = calendar.getTimeInMillis();
    	if(offSetTime > now){
    		return null;
    	}
    	String dayPattern =  "MM-dd";
    	SimpleDateFormat sFormat = new SimpleDateFormat(dayPattern);
    	
    	sFormat.format(calendar.getTime());
    	TimeOffSetRet  ret = new TimeOffSetRet();
    	ret.strTime= sFormat.format(calendar.getTime());
    	ret.selectedTime = calendar.getTimeInMillis();
    	return ret;
    }
    
    public static TimeOffSetRet getLineCharMonthShow(long selectedTime,int offset){
    	Calendar calendar = Calendar.getInstance();  
    	calendar.setTimeInMillis(selectedTime);
    	int nowMoth  = calendar.get(Calendar.MONTH);
    	nowMoth -= offset;
    	long now = System.currentTimeMillis();
    	calendar.set(Calendar.MONTH, nowMoth);
    	long offSetTime = calendar.getTimeInMillis();
    	if(offSetTime > now){
    		return null;
    	}
    	String dayPattern =  "yyyy-MM";
    	SimpleDateFormat sFormat = new SimpleDateFormat(dayPattern);
    	sFormat.format(calendar.getTime());
    	TimeOffSetRet  ret = new TimeOffSetRet();
    	ret.strTime= sFormat.format(calendar.getTime());
    	ret.selectedTime = calendar.getTimeInMillis();
    	return ret;
    }
    
    public static TimeOffSetRet getLineCharYearShow(long selectedTime,int offset){
    	long now = System.currentTimeMillis();
    	Calendar calendar = Calendar.getInstance();  	
    	calendar.setTimeInMillis(selectedTime);
    	int nowYear = calendar.get(Calendar.YEAR);
    	nowYear -= offset;
    	calendar.set(Calendar.YEAR, nowYear);
   	
    	long offSetTime = calendar.getTimeInMillis();
    	if(offSetTime > now){
    		return null;
    	}
    	String dayPattern =  "yyyy";
    	SimpleDateFormat sFormat = new SimpleDateFormat(dayPattern);
    	sFormat.format(calendar.getTime());
    	TimeOffSetRet  ret = new TimeOffSetRet();
    	ret.strTime= sFormat.format(calendar.getTime());
    	ret.selectedTime = calendar.getTimeInMillis();
    	return ret;
    }
    
    
    public static final String getYMDH(long mils){	
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTimeInMillis(mils);
    	SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    	return sFormat.format(calendar.getTime());
    }
 

    public static int getMonthDis(long beginMil,long endMil){
        Calendar calendarBegin = Calendar.getInstance();
        Calendar calendarEnd = Calendar.getInstance();
        calendarBegin.setTimeInMillis(beginMil);
        calendarEnd.setTimeInMillis(endMil);
        int beginYear = calendarBegin.get(Calendar.YEAR);
        int endYear   = calendarEnd.get(Calendar.YEAR);


        int beginMonth = calendarBegin.get(Calendar.MONTH);
        int endMonth = calendarEnd.get(Calendar.MONTH);

        return (endYear - beginYear)*12+(endMonth - beginMonth);
    }
    
    public static  class TimeOffSetRet{
    public 	long   selectedTime;
    public 	String strTime;
    }
    
   public static  byte[] Bitmap2Bytes(Bitmap bm) {  
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);  
        return baos.toByteArray();  
    } 
   
   public static Bitmap Bytes2Bimap(byte[] b) {  
       if (b.length != 0) {  
           return BitmapFactory.decodeByteArray(b, 0, b.length);  
       } else {  
           return null;  
       }  
   } 
    
   
   public static byte[] decryptBASE64(String key){               
       return Base64.decode(key, Base64.DEFAULT);
   }
   
   public static String encryptBASE64(byte[] buff){
	   return Base64.encodeToString(buff, Base64.DEFAULT);
   }


    public static boolean isTopActivy(Context context,String cmpName){

        String[] activePackages;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            activePackages = getActivePackages(context);
        } else {
            activePackages = getActivePackagesCompat(context);
        }

        if (activePackages != null) {
            for (String activePackage : activePackages) {
                Log.e("===== top "," activi name ="+ activePackage);
                if (activePackage.equals(cmpName)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static  boolean appIsRunning(Context ctx,String packageName)
    {
        ActivityManager am = (ActivityManager) ctx.getSystemService(ctx.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = am.getRunningAppProcesses();
        if(runningAppProcesses!=null) {
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                if(runningAppProcessInfo.processName.startsWith(packageName)) {
                    Log.e("======== running pack "," pkg name = "+runningAppProcessInfo.processName);
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
				/*
				BACKGROUND=400 EMPTY=500 FOREGROUND=100
				GONE=1000 PERCEPTIBLE=130 SERVICE=300 ISIBLE=200
				 */
                Log.e(context.getPackageName(), "此appimportace ="
                        + appProcess.importance
                        + ",context.getClass().getName()="
                        + context.getClass().getName());
                if (appProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    Log.e(context.getPackageName(), "处于后台"
                            + appProcess.processName);
                    return true;
                } else {
                    Log.e(context.getPackageName(), "处于前台"
                            + appProcess.processName);
                    return false;
                }
            }
        }
        return false;
    }

    public static   String[] getActivePackagesCompat(Context context) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        final List<ActivityManager.RunningTaskInfo> taskInfo = activityManager.getRunningTasks(1);
        final ComponentName componentName = taskInfo.get(0).topActivity;
        final String[] activePackages = new String[1];
        activePackages[0] = componentName.getShortClassName();
        return activePackages;
    }

    public static String[] getActivePackages(Context context) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        final Set<String> activePackages = new HashSet<String>();
        final List<ActivityManager.RunningAppProcessInfo> processInfos = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo processInfo : processInfos) {
            if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                activePackages.addAll(Arrays.asList(processInfo.processName));
            }
        }
        return activePackages.toArray(new String[activePackages.size()]);
    }

    public static String  addDateHour(Date date, int hours){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        String datePatern = "yyyy-mm-dd hh:mm:00";
        SimpleDateFormat sFormat1 = new SimpleDateFormat(datePatern);

        String strTime = sFormat1.format(date);
        strTime += "--";
        strTime += hour;
        strTime +=":00";
        return strTime;
    }

    public static Date  coverDateToString(String date , String datePatern){
        SimpleDateFormat sFormat1 = new SimpleDateFormat(datePatern);
        try {
            return sFormat1.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String  subDate(Date date, int hours,String datePatern){
        SimpleDateFormat sFormat1 = new SimpleDateFormat(datePatern);
        long subTime = date.getTime() - hours * 3600 * 1000;
        Date nowDate = new Date();
        nowDate.setTime(subTime);
        return sFormat1.format(nowDate);
    }

    public static Date  subDate(Date date, int hours){
        long subTime = date.getTime() - hours * 3600 * 1000;
        Date nowDate = new Date();
        nowDate.setTime(subTime);
        return nowDate;
    }

    public static String disTime(Date date1, Date date2){
        String datePattern = "dd HH:mm";
        Date disDate = new Date(date2.getTime() - date1.getTime());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
        return simpleDateFormat.format(disDate);
    }


    private static  int notificationId;

    public void playNotification(OrderInfo orderInfo, Context  context) {
        notificationId++;
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // 1.创建一个NotificationCompat.Builder预对象
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        // 2.设置主要信息
        builder.setDefaults(Notification.DEFAULT_SOUND);
        builder.setContentTitle(orderInfo.getOrderTitle()); // 设置内容题目
        builder.setContentText(orderInfo.getAddr()); // 设置内容文本信息
        builder.setSmallIcon(R.mipmap.ic_launcher); // 设置小图标
        //builder.setContentInfo(alarm.getPumpHouseName()); // 设置内容info
        builder.setTicker(orderInfo.getOrderTitle()); // 对应的Notification对象create时，以消息在标题栏提示
        builder.setWhen(System.currentTimeMillis()); // 设置时间
        builder.setAutoCancel(true); // 默认点击对应的notification对象后，该对象消失

//        Intent broadcastIntent = new Intent(context, MyNotificationActivity.class);
//        broadcastIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        broadcastIntent.putExtra("msg_id", alarm.getXh());
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, notificationId, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

      //  builder.setContentIntent(pendingIntent);
        // 4.得到一个notification对象(根据builder预设置信息)
        manager.notify(notificationId, builder.build());
        //Log.e("p666666666666","news :"+alarm.getTitle());
    }
}
