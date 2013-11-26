package com.may.taoba.module.jsinterface;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.webkit.WebView;

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
				if(object instanceof ScirptInterface){
					((ScirptInterface)object).setContext(htmlActivity);
				}
				
				webView.addJavascriptInterface(object, "keyString");
			}
		}
	}
}
