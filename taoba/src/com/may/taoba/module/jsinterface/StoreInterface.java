package com.may.taoba.module.jsinterface;

import org.json.JSONArray;

import com.may.taoba.bean.KeyValue;
import com.may.taoba.bean.Preferences;
import com.may.taoba.module.ServicesManager;
import com.may.taoba.module.store.Database;
import com.may.taoba.module.store.DatabaseManager;

public class StoreInterface extends ScriptInterface{

	DatabaseManager dbManager;
	KeyValue keyValue;

	public StoreInterface(){
		DatabaseManager databasemanager = ServicesManager.getDatabaseManager();
		dbManager = databasemanager;
		KeyValue keyvalue = new KeyValue();
		keyValue = keyvalue;
	}

	public int execSql(String s, String s1)
	{
		int i;
		if (dbManager.openDatabase(s).execSql(s1))
			i = 1;
		else
			i = 0;
		return i;
	}

	public String getPreference(String s)
	{
		return Preferences.getInstance().get(s);
	}

	public String kvLoad(String s, String s1)
	{
		Database database = dbManager.openDatabase(s);
		String s2 = keyValue.getValueByKey(s1);
		String s3;
		if (s2 == null)
			s3 = "";
		else
			s3 = s2;
		return s3;
	}

	public void kvSave(String s, String s1, String s2, long l)
	{
		Database database = dbManager.openDatabase(s);
		keyValue.save(s1, s2, l);
	}

	public void openDatabase(String s)
	{
		Database database = dbManager.openDatabase(s);
	}

	public String query(String s, String s1)
	{
		Object aobj[][] = dbManager.openDatabase(s).query(s1);
		JSONArray jsonarray = new JSONArray();
		int i = 0;
		do
		{
			int j = aobj.length;
			if (i < j)
			{
				Object aobj1[] = aobj[i];
				JSONArray jsonarray1 = new JSONArray();
				int k = 0;
				do
				{
					int l = aobj1.length;
					if (k >= l)
						break;
					Object obj = aobj1[k];
					JSONArray jsonarray2 = jsonarray1.put(obj);
					k++;
				} while (true);
				JSONArray jsonarray3 = jsonarray.put(jsonarray1);
				i++;
			} else
			{
				return jsonarray.toString();
			}
		} while (true);
	}

	
	public void savePreference(String key, String value, long time){
		System.out.println("StoreBridge javascript call java savePreference key = "+key +" value = "+value);
		
		Preferences.getInstance().save(key, value, time);
	}
}
