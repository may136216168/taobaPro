package com.may.taoba.bean;

import com.may.taoba.R.string;
import com.may.taoba.module.store.Bean;
import com.may.taoba.module.store.Database;

/**
 * 这个类会去操作一个数据库中的表，kv
 * 这个表里面主要存放的是关键字和值的集合，这个表存放的数据与具体的
 * 程序中的功能有关,那么这只需要完成表的存取.
 * @author ZhangJian
 *
 */
public class KeyValue extends Bean {

	@Override
	public void createTable() {
		Object[] aobj = new Object[1];
		aobj[0] = "kv";
		String sql = "CREATE TABLE if not exists kv(key TEXT PRIMARY KEY,value TEXT,expire_time INTEGER);";
		boolean flag = db.execSql(sql);
	}
	
	/**
	 * 保存数据到kv表中.
	 */
	public void save(String s,String s1,long l){
		String s2 = "REPLACE INTO kv(key,value,expire_time) VALUES(?,?,?)";
		Database database = db;
		Object[] aobj1 = new Object[3];
		aobj1[0] = s;
		aobj1[1] = s1;
		Long long1 = Long.valueOf(l);
		aobj1[2] = long1;
		boolean flag = database.execSql(s2, aobj1);
	}
	
	public String getValueByKey(String s){
		StringBuilder stringbuilder = new StringBuilder("SELECT value from kv WHERE key=? AND (expire_time=-1 OR expire_time>");
		long l = System.currentTimeMillis() / 1000L;
		String s1 = stringbuilder.append(l).append(")").toString();
		Object[] aobj = new Object[1];
		aobj[0] = "kv";
		String s2 = stringbuilder.toString();
		Database database = db;
		String[] as = new String[1];
		as[0] = s;
		return database.getSingleString(s2, as);
	}
}
