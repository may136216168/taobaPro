package com.may.taoba.module;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * 电话服务类，主要判断电话是否联网
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
	 * WiFi状态
	 * @return
	 */
	public android.net.NetworkInfo.State getPhoneWifiState(){
		return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
	}
	
	/**
	 * 手机Gprs
	 * @return
	 */
	public android.net.NetworkInfo.State getPhoneState(){
		return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
	}
}
