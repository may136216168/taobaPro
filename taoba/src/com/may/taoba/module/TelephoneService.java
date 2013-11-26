package com.may.taoba.module;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * �绰�����࣬��Ҫ�жϵ绰�Ƿ�����
 * @author ZhangJian
 *
 */
public class TelephoneService {

	ConnectivityManager connectivityManager;
	Context parecontext;
	
	public TelephoneService(Context context){
		this.parecontext = context;
		connectivityManager  = (ConnectivityManager)this.parecontext.getSystemService(Context.CONNECTIVITY_SERVICE);
	}
	
	/**
	 * WiFi״̬
	 * @return
	 */
	public android.net.NetworkInfo.State getPhoneWifiState(){
		return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
	}
	
	/**
	 * �ֻ�Gprs
	 * @return
	 */
	public android.net.NetworkInfo.State getPhoneState(){
		return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
	}
}
