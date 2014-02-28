package com.may.taoba.bean;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.may.taoba.module.store.Bean;
import com.may.taoba.module.store.Database;
import com.may.taoba.tools.StringTool;

/**
 * 属性：
 * 1、数据库中的表名
 * 2、时间：这个时间用来表示更新周期，比如1个星期，更新一次
 * 3、单例的属性，表示城市列表本事
 * 4、数据库的一个属性SQLiteDatabase
 * 
 * 方法：
 * 1、判断本地的数据库中存储的城市列表是否需要更新
 * 2、下载新的数据，并且保存到我们的数据库里面
 * 3、从服务器上得到数据，这个数据是json格式
 * 4、解析json
 * @author ZhangJian
 *
 */
public class CitiesList extends Bean {

	private SQLiteDatabase sqliteDb;
	private final static String webStrUrl = "http://192.168.1.1";
	private static CitiesList instance;
	
	public CitiesList() {
	}
	
	public static CitiesList getInstance()
	{
		if (instance == null)
			instance = new CitiesList();
		return instance;
	}
	
	//获取所有的Json数据
	public String getAllJson(){
		Object aobj[] = new Object[1];
		aobj[0] = "citylist";
		String s = StringTool.simpleFormat("select json from %s where json <> '';", aobj);
		//String s = "select ";
		Object aobj1[][] = db.query(s);
		String s1;
		if (aobj1.length > 0 && aobj1[0].length > 0)
			s1 = aobj1[0][0].toString();
		else
			s1 = "";
		return s1;
	}
	
	public void initCities(){
		//首先去得到城市列表是什么时候更新，更新的这个时间是存储在preferences这个表里面
		//如果说一个星期没有更新，那么去更新，如果没有一个星期，什么也不做
		sqliteDb = db.getDb();
		//构造一个查询字符串，查询最近一次是什么时候更新的
		String sqlStr = "select value from preference where key=?";
		Cursor cursor = sqliteDb.rawQuery(sqlStr, new String[]{"updatecity"});
		
		if(cursor.getCount() != 0){
			//在这里去判断是否更新城市列表
			if(cursor.moveToFirst()){
				int i = cursor.getColumnIndex("value");
				String s2 = cursor.getString(i);
				//数据库最新一次更新的时间
				long l = new Long(s2);
				cursor.close();
				
				//判断这个时间是否已经过去了一个星期
				long currentTime = System.currentTimeMillis();
				if((currentTime - l) >= (24*60*60*1000*7)){
					//先删除数据库，然后再到网上去更新它
					String deleStr = "delete from citylist";
					String dele4PreferencesTable = "delete from preferences where key = ?";
					sqliteDb.execSQL(deleStr);
					sqliteDb.execSQL(dele4PreferencesTable, new String[]{"updatecity"});
				}
			}
		}else{
			//第一次下载城市列表
			downAndSaveCityList();
		}
	}
	
	/**
	 * 1、是到服务器去下载相关的城市列表的数据
	 * 2、是json格式，解析json，并把所有的城市放到一个Array或者List
	 * 3、就是将这些解析的数据放在数据库中，并且要更新'时间',表示什么时候更新的城市列表.
	 */
	private void downAndSaveCityList(){
		String s = "";
//		NetworkService.shareInstance()
		if(s == null){
			return;
		}
		ArrayList<City> arrayList = new ArrayList<City>();
		try {
			JSONObject json1 = new JSONObject(s);
			JSONArray jsonArray = json1.getJSONArray("cities");
			int length = jsonArray.length();
			
			for (int i = 0; i < length; i++) {
				JSONObject json = jsonArray.getJSONObject(i);
				City city = new City();
				city.id = json.getString("id");
				city.name = json.getString("name");
				city.pinyin = json.getString("pinyin");
				arrayList.add(city);
			}
		
			//存放arrayList到数据库里面去
			String sql1 = "insert into citylist(cityId,name,pinyin,json) values(?,?,?,?)";
			String sql2 = "insert into perferences(key,value,expire_time) values(?,?,?)";
			sqliteDb.beginTransaction();
			//更新preferences这个表
			Object[] aObject = new Object[3];
			aObject[0] = "updatecity";
			long l = System.currentTimeMillis();
			aObject[1] = l;
			aObject[2] = Integer.valueOf(65535);
			sqliteDb.execSQL(sql1, aObject);
			
			//插入城市列表
			Iterator iterator = arrayList.iterator();
			while (iterator.hasNext()) {
				City city = (City)iterator.next();
				Object[] aObject2 = new Object[4];
				aObject2[0] = city.id;
				aObject2[1] = city.name;
				aObject2[2] = city.pinyin;
				aObject2[3] = 0;
				sqliteDb.execSQL(sql2, aObject2);
			}
			sqliteDb.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			sqliteDb.endTransaction();
		}
	}
	
	@Override
	public void createTable() {
		//写一个Sql语句，就可以创建一个表
		//cityId,name,pinyin,json
		String sql = "CREATE　TABLE if not exists citylist" +
				"(cityId integer,name text,pinyin text,json,text);";
		boolean flag = db.execSql(sql);
	}
	
	/**
	 * 根据Id获得城市实例.
	 * @param id
	 * @return
	 */
	public City getCityById(String id){
		City city = null;
		String[] ag = new String[]{id};
		Database db1 = db;
		String sqlStr = "select * from citylist where CityId = ?";
		Object[][] abObject = db1.query(sqlStr, ag);
		if(abObject.length > 0 && abObject[0].length > 0){
			Object abObj[] = abObject[0];
			city = new City();
			city.id = abObj[0].toString();
			city.name = abObj[1].toString();
			city.pinyin = abObj[2].toString();
		}
		return city;
	}

}
