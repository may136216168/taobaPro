package com.may.taoba.module.jsinterface;

import java.util.Map;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.may.taoba.module.ServicesManager;
import com.may.taoba.module.mybase.HtmlActivity;
import com.may.taoba.module.net.NetworkService;
import com.may.taoba.tools.StringTool;

/**
 * 网络连接的接口
 * 1、从服务器获得数据
 * 2、也可以提交数据到服务器
 * html页面与服务器的交换也差不多只有这些功能
 * @author ZhangJian
 *
 */
public class NetWorkInterface extends ScriptInterface{

	private NetworkService networkService;
	private String error400;
	private String error500;
	public NetWorkInterface() {
		networkService = ServicesManager.getNewworkService();
		error400 = "页面无法访问错误";
		error500 = "服务器错误";
	}
	
	/**
	 * 从服务器获得数据.
	 * @param id
	 * @param urlStr
	 * @param datatype
	 */
	public void getData(String id,String urlStr,String datatype){
		//1与接口相关的窗体，HtmlActivity
		HtmlActivity htmlActivity = getContext();
		HandlerGet handlerGet = new HandlerGet(htmlActivity, datatype, id);
		networkService.get(urlStr, handlerGet);
	}
	
	/**
	 * 主要向服务器发送数据
	 * @param id
	 * @param s1
	 * @param s2
	 * @param dataStr
	 */
	public void postData(String id,String s1,String s2,String dataStr){
		HtmlActivity htmlActivity = getContext();
		Map map = StringTool.convertParamsToMap(s2);
		HandlerPost handlerPost = new HandlerPost(htmlActivity, dataStr, id);
		networkService.Post(s1, map, handlerPost);
	}
	
	/**
	 * 与服务器进行get通讯的handler.
	 */
	private class HandlerGet extends Handler{
		//需要外部类的引用
		final NetWorkInterface this$0;
		HtmlActivity htmlActivity;
		String datatypeString;
		String id;
		
		public HandlerGet(HtmlActivity htmlActivity,String datatype,String id) {
			this$0 = NetWorkInterface.this;
			this.datatypeString = datatype;
			this.id = id;
			this.htmlActivity = htmlActivity;
		}
		
		/**
		 * 主要是向服务器发送消息，然后获取消息，最后调用JS
		 */
		@Override
		public void handleMessage(Message msg) {
			String s1 = "";
			String s2 = "";
			int status;
			Bundle bundle = msg.getData();
			//如果从服务器获取数据出现错误，那么有提示html页面的机会
			if(bundle.containsKey("error")){
				s1 = "'"+bundle.getString("error")+"'";
			}else{
				//数据成功
				s1 = bundle.getString("resonseText").replaceAll("\n", "").replaceAll("'", "\\'");
			}
			
			status = bundle.getInt("status");
			
			//判断状态的情况
			if(status >= 400 && status <= 500){
				s1 = error400;
			}
			if(status >= 500 && status < 510){
				s1 = error500;
			}
			s2 = datatypeString;
			if(s2.equalsIgnoreCase("json") == false){
				s1 = "'"+s1+"'";
			}
			String[] as = new String[3];
			as[0] = "'"+id+"'";
			as[1] = status+"";
			as[2] = s2;
			htmlActivity.callJSFunction("_on_ajax_finished", as);
		}
	}
	
	private class HandlerPost extends Handler{
		
		//需要外部类的引用
		final NetWorkInterface this$0;
		HtmlActivity htmlActivity;
		String datatypeString;
		String id;
		
		public HandlerPost(HtmlActivity htmlActivity,String datatype,String id) {
			this$0 = NetWorkInterface.this;
			this.datatypeString = datatype;
			this.id = id;
			this.htmlActivity = htmlActivity;
		}
		
		@Override
		public void handleMessage(Message msg) {
			String s1 = "";
			String s2 = "";
			int status;
			Bundle bundle = msg.getData();
			//如果从服务器获取数据出现错误，那么有提示html页面的机会
			if(bundle.containsKey("error")){
				s1 = "'"+bundle.getString("error")+"'";
			}else{
				//数据成功
				s1 = bundle.getString("resonseText").replaceAll("\n", "").replaceAll("'", "\\'");
			}
			
			status = bundle.getInt("status");
			
			//判断状态的情况
			if(status >= 400 && status <= 500){
				s1 = error400;
			}
			if(status >= 500 && status < 510){
				s1 = error500;
			}
			s2 = datatypeString;
			if(s2.equalsIgnoreCase("json") == false){
				s1 = "'"+s1+"'";
			}
			String[] as = new String[3];
			as[0] = "'"+id+"'";
			as[1] = status+"";
			as[2] = s2;
			htmlActivity.callJSFunction("_on_ajax_finished", as);
		}
	}
}
