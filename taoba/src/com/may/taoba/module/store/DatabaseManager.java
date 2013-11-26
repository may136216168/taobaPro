package com.may.taoba.module.store;

import java.util.HashMap;
import java.util.Map;

import com.may.taoba.module.TaoBaApplication;

import android.content.Context;

/*
 * 数据库管理类
 * */
public class DatabaseManager{

	private Context context;
	private Map dbCache;

	public DatabaseManager(){
		this(((Context) (TaoBaApplication.getInstance())));
	}

	public DatabaseManager(Context context1){
		context = context1;
		//数据库缓存map对象
		dbCache = new HashMap();
	}

	//打开数据库
	public Database openDatabase(String s){
		Database database;
		
		//测试数据库缓存中是否存在此数据库
		if (dbCache.containsKey(s)){
			//获取此数据库
			database = (Database)dbCache.get(s);
		}
		//不存在，创建数据库
		else{
			//创建数据库
			database = new Database(s);
			//将数据库保存到缓存对象中
			Object obj = dbCache.put(s, database);
		}
		return database;
	}
}
