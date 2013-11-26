package com.may.taoba.module;

import com.may.taoba.module.net.NetworkService;
import com.may.taoba.module.store.DatabaseManager;

import android.content.Context;

/**
 * 这是管理所有服务的一个类
 * @author ZhangJian
 *
 */
public class ServicesManager {

	protected static Context parancontext;
	//管理一个地址服务
	
	//管理数据库服务
	protected static DatabaseManager databaseManager;
	
	//网络服务
	protected static NetworkService newworkService;
	
	//电话状态的服务
	protected static TelephoneService telephoneService;
	
	//GPS的位置服务
	
	public ServicesManager() {
	}
	
	public static DatabaseManager getDatabaseManager() {
		return databaseManager;
	}

	public static NetworkService getNewworkService() {
		return newworkService;
	}

	public static TelephoneService getTelephoneService() {
		return telephoneService;
	}

	/**
	 * 初始化所有的服务.
	 * 服务初始化只会执行一次，是在Application里面执行的.
	 */
	public static void initServices(Context context){
		parancontext = context;
		databaseManager = new DatabaseManager(context);
		newworkService = new NetworkService();
	}
}
