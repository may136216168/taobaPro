package com.may.taoba.module.jsinterface;

import com.may.taoba.module.ServicesManager;

public class ConnectivityInterface extends ScirptInterface{

	public ConnectivityInterface(){
	}
	
	/**
	 * ������������״̬����������ĺ����л�����������״̬.
	 * ���������ͨ�ˣ���ô����true���������û����ͨ��ô����false.
	 * �ֻ������֣�һ��wifi,һ�����ֻ�Gprs
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