package com.may.taoba.test.module.store;

import com.may.taoba.module.store.Database;
import com.may.taoba.module.store.DatabaseManager;

import android.test.AndroidTestCase;
import android.util.Log;

public class DatabaseManagerTest extends AndroidTestCase {

	public void testOpendatabase() throws Exception{
		DatabaseManager databaseManager = new DatabaseManager();
		Database database = databaseManager.openDatabase("gerydedu.db");
		assertNotNull(database);
	}
	
	public void testQuery() throws Exception{
		DatabaseManager databaseManager = new DatabaseManager();
		Database database = databaseManager.openDatabase("gerydedu.db");
		String sql = "select * from t_class where t_name=\'LiXi\'";
		Log.e("Print Sql", sql);
		Object[][] ds = database.query(sql);
		if(ds != null){
			Object obj = ds[0][1];
			Log.e("Query Result:>>", obj.toString());
			assertEquals("LiXi", obj.toString());
		}
	}
	
	public void testQuery1() throws Exception{
		DatabaseManager databaseManager = new DatabaseManager();
		Database database = databaseManager.openDatabase("gerydedu.db");
		String sql = "select * from t_class where t_name=?";
		Log.e("Print Sql", sql);
		Object[][] ds = database.query(sql, new String[]{"ZhangSan"});
		if(ds != null){
			Object obj = ds[0][1];
			Log.e("Query Result:>>", obj.toString());
			assertEquals("ZhangSan", obj.toString());
		}
	}
}
