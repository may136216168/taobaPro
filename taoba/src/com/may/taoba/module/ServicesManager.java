package com.may.taoba.module;

import com.may.taoba.module.net.NetworkService;
import com.may.taoba.module.store.DatabaseManager;

import android.content.Context;

/**
 * ���ǹ������з����һ����
 * @author ZhangJian
 *
 */
public class ServicesManager {

	protected static Context parancontext;
	//����һ����ַ����
	
	//�������ݿ����
	protected static DatabaseManager databaseManager;
	
	//�������
	protected static NetworkService newworkService;
	
	//�绰״̬�ķ���
	protected static TelephoneService telephoneService;
	
	//GPS��λ�÷���
	
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
	 * ��ʼ�����еķ���.
	 * �����ʼ��ֻ��ִ��һ�Σ�����Application����ִ�е�.
	 */
	public static void initServices(Context context){
		parancontext = context;
		databaseManager = new DatabaseManager(context);
		newworkService = new NetworkService();
	}
}
