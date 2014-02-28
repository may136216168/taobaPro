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
 * ���ԣ�
 * 1�����ݿ��еı���
 * 2��ʱ�䣺���ʱ��������ʾ�������ڣ�����1�����ڣ�����һ��
 * 3�����������ԣ���ʾ�����б���
 * 4�����ݿ��һ������SQLiteDatabase
 * 
 * ������
 * 1���жϱ��ص����ݿ��д洢�ĳ����б��Ƿ���Ҫ����
 * 2�������µ����ݣ����ұ��浽���ǵ����ݿ�����
 * 3���ӷ������ϵõ����ݣ����������json��ʽ
 * 4������json
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
	
	//��ȡ���е�Json����
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
		//����ȥ�õ������б���ʲôʱ����£����µ����ʱ���Ǵ洢��preferences���������
		//���˵һ������û�и��£���ôȥ���£����û��һ�����ڣ�ʲôҲ����
		sqliteDb = db.getDb();
		//����һ����ѯ�ַ�������ѯ���һ����ʲôʱ����µ�
		String sqlStr = "select value from preference where key=?";
		Cursor cursor = sqliteDb.rawQuery(sqlStr, new String[]{"updatecity"});
		
		if(cursor.getCount() != 0){
			//������ȥ�ж��Ƿ���³����б�
			if(cursor.moveToFirst()){
				int i = cursor.getColumnIndex("value");
				String s2 = cursor.getString(i);
				//���ݿ�����һ�θ��µ�ʱ��
				long l = new Long(s2);
				cursor.close();
				
				//�ж����ʱ���Ƿ��Ѿ���ȥ��һ������
				long currentTime = System.currentTimeMillis();
				if((currentTime - l) >= (24*60*60*1000*7)){
					//��ɾ�����ݿ⣬Ȼ���ٵ�����ȥ������
					String deleStr = "delete from citylist";
					String dele4PreferencesTable = "delete from preferences where key = ?";
					sqliteDb.execSQL(deleStr);
					sqliteDb.execSQL(dele4PreferencesTable, new String[]{"updatecity"});
				}
			}
		}else{
			//��һ�����س����б�
			downAndSaveCityList();
		}
	}
	
	/**
	 * 1���ǵ�������ȥ������صĳ����б������
	 * 2����json��ʽ������json���������еĳ��зŵ�һ��Array����List
	 * 3�����ǽ���Щ���������ݷ������ݿ��У�����Ҫ����'ʱ��',��ʾʲôʱ����µĳ����б�.
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
		
			//���arrayList�����ݿ�����ȥ
			String sql1 = "insert into citylist(cityId,name,pinyin,json) values(?,?,?,?)";
			String sql2 = "insert into perferences(key,value,expire_time) values(?,?,?)";
			sqliteDb.beginTransaction();
			//����preferences�����
			Object[] aObject = new Object[3];
			aObject[0] = "updatecity";
			long l = System.currentTimeMillis();
			aObject[1] = l;
			aObject[2] = Integer.valueOf(65535);
			sqliteDb.execSQL(sql1, aObject);
			
			//��������б�
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
		//дһ��Sql��䣬�Ϳ��Դ���һ����
		//cityId,name,pinyin,json
		String sql = "CREATE��TABLE if not exists citylist" +
				"(cityId integer,name text,pinyin text,json,text);";
		boolean flag = db.execSql(sql);
	}
	
	/**
	 * ����Id��ó���ʵ��.
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
