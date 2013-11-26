package com.may.taoba.bean;

import com.may.taoba.R.string;
import com.may.taoba.module.store.Bean;
import com.may.taoba.module.store.Database;

/**
 * ������ȥ����һ�����ݿ��еı�kv
 * �����������Ҫ��ŵ��ǹؼ��ֺ�ֵ�ļ��ϣ�������ŵ�����������
 * �����еĹ����й�,��ô��ֻ��Ҫ��ɱ�Ĵ�ȡ.
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
	 * �������ݵ�kv����.
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
