package com.may.taoba.module.jsinterface;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.webkit.WebView;

import com.may.taoba.module.Config;
import com.may.taoba.module.mybase.HtmlActivity;

/**
 * �����е�HTML������Ե��õ�JS�����������Ա�ʹ��.
 * ��javascript��HtmlActivity����,ͨ��js����java����
 * @author ZhangJian
 *
 */
public class ScriptHelper {
	//������е�js��ϣ��.
	private Map interfaceMap;
	
	public ScriptHelper() {
		//��ȥ���interface Map
		interfaceMap = new HashMap();
		//���ϵ���interfaceMap�м�ScriptInterface�ӿ�
		ConnectivityInterface connectivityInterface = new ConnectivityInterface();
		interfaceMap.put("android_connect", connectivityInterface);
		
		StoreInterface storeInterface = new StoreInterface();
		interfaceMap.put("android_store", storeInterface);
		
		NetWorkInterface netWorkInterface = new NetWorkInterface();
		interfaceMap.put("android_net", netWorkInterface);
		
		//����Log
		LogInterface logInterface = new LogInterface();
		interfaceMap.put("android_log", logInterface);
		try{
		//���ｫBridgeע�ᣬ�Ѿ�config.xml�Ѿ���Config.java����������
		Iterator iterator = Config.JAVASCRIPT_BRIDGES.entrySet().iterator();
		while (iterator.hasNext()) {
			java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
			String s1 = (String)entry.getKey();
			String s2 = (String)entry.getValue();
			Class class1 = Class.forName("com.may.taoba.javascript.Bridge");
			Object obj1 = class1.newInstance();
			interfaceMap.put(s1, obj1);
			
			System.out.println("s="+s1+" obj1"+obj1);
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void bindJavascriptInterface(HtmlActivity htmlActivity){
		if(interfaceMap.size() > 0){
			//���webView
			WebView webView = htmlActivity.getWebView();
			
			String keyString;
			Object object;
			Iterator iterator = interfaceMap.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry entry = (Map.Entry)iterator.next();
				keyString = (String)entry.getKey();
				object = entry.getValue();
				if(object instanceof ScriptInterface){
					((ScriptInterface)object).setContext(htmlActivity);
				}
				
				webView.addJavascriptInterface(object, "keyString");
			}
		}
	}
}
