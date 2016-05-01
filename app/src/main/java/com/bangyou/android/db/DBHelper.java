package com.bangyou.android.db;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBHelper {
	private final static String dbName          = "bangyo.db";
	private final static String TABLE_NAME      = "bangyodata";
	private final static String COLUM_NAME_DATA = "data";
	private final static String COLUM_NAME_ID   = "id";

	public final  static String MSG_ID          = "msg_id";
	public final  static String ORDER_ID        = "order_id";
	
	public static<T> T getObject(Context context, String id,TypeReference<T> tf){
		T  objList = null;
		SQLiteDatabase db = getDb(context);
		Cursor c = db.rawQuery("select * from  " + TABLE_NAME + " where id =?", new String[] { id });	
		if (c.moveToFirst()) {
			int columIndex =  c.getColumnIndex(COLUM_NAME_DATA);
			byte [] bData  =  c.getBlob(columIndex);
			//String data  =  c.getString(columIndex);
			String data = new String(bData);
			Log.e("uuuuuuuu", "data = " + data);
			objList = (T) JSON.parseObject(data, tf);
		}
		c.close();
		releaseDb(db);
		return objList;	
	}
	
	public static <T> void insertObject(Context context, String id, T object) {
		SQLiteDatabase db = getDb(context);
		db.execSQL("create TABLE IF NOT EXISTS " + TABLE_NAME + "(id varchar(255),data BLOB)");
		String data = JSON.toJSONString(object);
		Log.e("======Save ", data);
		ContentValues cvOfLiHua = new ContentValues();
		cvOfLiHua.put(COLUM_NAME_ID, id);
		cvOfLiHua.put(COLUM_NAME_DATA, data.getBytes());
	   long ret =  db.replace(TABLE_NAME, null, cvOfLiHua);
		releaseDb(db);
	}

	

	public static void delete(Context context, String id) {
		SQLiteDatabase db = getDb(context);
		db.execSQL("DELETE FROM " + TABLE_NAME + "  WHERE name='" + id + "'");
		releaseDb(db);
	}
	
	public static  void dropDB(Context context){
		context.deleteDatabase(dbName);
	}

	public static SQLiteDatabase getDb(Context context) {
		return context.openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);
	}

	public static void releaseDb(SQLiteDatabase db) {
		// SQLiteDatabase.releaseMemory();
		db.close();
	}
}
