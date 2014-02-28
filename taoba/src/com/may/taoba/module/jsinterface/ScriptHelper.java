package com.may.taoba.module.jsinterface;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.webkit.WebView;

import com.may.taoba.module.Config;
import com.may.taoba.module.mybase.HtmlActivity;

/**
 * 将所有的HTML里面可以调用的JS集合起来，以便使用.
 * 绑定javascript到HtmlActivity里面,通过js调用java程序
 * @author ZhangJian
 *
 */
public class ScriptHelper {
	//存放所有的js哈希表.
	private Map interfaceMap;
	
	public ScriptHelper() {
		//就去填充interface Map
		interfaceMap = new HashMap();
		//不断的向interfaceMap中加ScriptInterface接口
		ConnectivityInterface connectivityInterface = new ConnectivityInterface();
		interfaceMap.put("android_connect", connectivityInterface);
		
		StoreInterface storeInterface = new StoreInterface();
		interfaceMap.put("android_store", storeInterface);
		
		NetWorkInterface netWorkInterface = new NetWorkInterface();
		interfaceMap.put("android_net", netWorkInterface);
		
		//调试Log
		LogInterface logInterface = new LogInterface();
		interfaceMap.put("android_log", logInterface);
		try{
		//这里将Bridge注册，已经config.xml已经被Config.java这个类给解析
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
			//获得webView
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
