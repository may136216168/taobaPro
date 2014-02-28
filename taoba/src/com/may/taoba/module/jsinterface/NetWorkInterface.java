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
 * �������ӵĽӿ�
 * 1���ӷ������������
 * 2��Ҳ�����ύ���ݵ�������
 * htmlҳ����������Ľ���Ҳ���ֻ����Щ����
 * @author ZhangJian
 *
 */
public class NetWorkInterface extends ScriptInterface{

	private NetworkService networkService;
	private String error400;
	private String error500;
	public NetWorkInterface() {
		networkService = ServicesManager.getNewworkService();
		error400 = "ҳ���޷����ʴ���";
		error500 = "����������";
	}
	
	/**
	 * �ӷ������������.
	 * @param id
	 * @param urlStr
	 * @param datatype
	 */
	public void getData(String id,String urlStr,String datatype){
		//1��ӿ���صĴ��壬HtmlActivity
		HtmlActivity htmlActivity = getContext();
		HandlerGet handlerGet = new HandlerGet(htmlActivity, datatype, id);
		networkService.get(urlStr, handlerGet);
	}
	
	/**
	 * ��Ҫ���������������
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
	 * �����������getͨѶ��handler.
	 */
	private class HandlerGet extends Handler{
		//��Ҫ�ⲿ�������
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
		 * ��Ҫ���������������Ϣ��Ȼ���ȡ��Ϣ��������JS
		 */
		@Override
		public void handleMessage(Message msg) {
			String s1 = "";
			String s2 = "";
			int status;
			Bundle bundle = msg.getData();
			//����ӷ�������ȡ���ݳ��ִ�����ô����ʾhtmlҳ��Ļ���
			if(bundle.containsKey("error")){
				s1 = "'"+bundle.getString("error")+"'";
			}else{
				//���ݳɹ�
				s1 = bundle.getString("resonseText").replaceAll("\n", "").replaceAll("'", "\\'");
			}
			
			status = bundle.getInt("status");
			
			//�ж�״̬�����
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
		
		//��Ҫ�ⲿ�������
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
			//����ӷ�������ȡ���ݳ��ִ�����ô����ʾhtmlҳ��Ļ���
			if(bundle.containsKey("error")){
				s1 = "'"+bundle.getString("error")+"'";
			}else{
				//���ݳɹ�
				s1 = bundle.getString("resonseText").replaceAll("\n", "").replaceAll("'", "\\'");
			}
			
			status = bundle.getInt("status");
			
			//�ж�״̬�����
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
