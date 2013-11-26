package com.may.taoba.module.jsinterface;

import com.may.taoba.module.ServicesManager;

public class ConnectivityInterface extends ScirptInterface{

	public ConnectivityInterface(){
	}
	
	/**
	 * 获得网络的连接状态，关于网络的函数中获得网络的链接状态.
	 * 如果网络联通了，那么返回true，如果网络没有联通那么返回false.
	 * 手机上两种，一种wifi,一种是手机Gprs
	 * @return
	 */
	public boolean getNetworkConnectivityState(){
		boolean flag;
		boolean flag1;
		boolean flag2;
		
		//wifi
		if(ServicesManager.getTelephoneService().getPhoneWifiState() == android.net.NetworkInfo.State.CONNECTED){
			flag = true;
		}else{
			flag = false;
		}
		if(ServicesManager.getTelephoneService().getPhoneState() == android.net.NetworkInfo.State.CONNECTED){
			flag1 = true;
		}else{
			flag1 = false;
		}
		
		if(flag == false && flag1 == false){
			flag2 = false;
		}else{
			flag2 = true;
		}
		return flag2;
	}
	
	
}