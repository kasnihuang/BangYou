package com.bangyou.android.db;

public class DbDo<T> {
	
	public DbDo( String id, T obj){
		this.obj = obj;
		this.id = id;
	}
	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public T getObj() {
		return obj;
	}
	public void setObj(T obj) {
		this.obj = obj;
	}
	private T obj;
	

}
