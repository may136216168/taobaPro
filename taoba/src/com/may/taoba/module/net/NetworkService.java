package com.may.taoba.module.net;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class NetworkService {
	private static NetworkService instance = null;
	private DefaultHttpClient httpClient;
	public NetworkService() {
		BasicHttpParams basicHttpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(basicHttpParams, 60000);
		HttpConnectionParams.setSoTimeout(basicHttpParams, 60000);
		
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		PlainSocketFactory plainSocketFactory = PlainSocketFactory.getSocketFactory();
		Scheme scheme = new Scheme("http", plainSocketFactory, 80);
		schemeRegistry.register(scheme);
		
		ThreadSafeClientConnManager threadSafeClientConnManager =
				new ThreadSafeClientConnManager(basicHttpParams, schemeRegistry);
		
		DefaultHttpClient defaultHttpClient = new DefaultHttpClient(threadSafeClientConnManager, basicHttpParams);
		httpClient = defaultHttpClient;
	}
	
	public static NetworkService shareInstance(){
		try {
			if(instance == null){
				instance = new NetworkService();
			}
			return instance;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * �����ǵõ��ַ����Ĳ���.
	 * @param s
	 * @return
	 */
	private String addGetParams(String s){
		//http://www.baidu.com/a?v=1.0&b=jdj
		StringBuilder stringBuilder = new StringBuilder();
		String s1 = "";//no
		s1+="=";
		s1+="";//����ѡ��������ļ���
		s1+="&ver=";
		s1+="";//�����ļ���
		return "";
	}
	
	/**
	 * ����һ�������������Ϣ��������handler�н��д���.
	 * @param i
	 * @param s
	 * @param handler
	 */
	private void sendFailureMessage(int i,String s,Handler handler){
		Message msg = Message.obtain();
		Bundle bundle = new Bundle();
		bundle.putInt("status", 600);
		bundle.putString("error", s);
		msg.setData(bundle);
		handler.sendMessage(msg);
	}
	
	/**
	 * �õ����������淢����������.
	 * @param url
	 * @param handler
	 */
	public void get(String url,final Handler handler){
		
	}
	
	/**
	 * �õ�Http����.
	 * @param s
	 * @return
	 */
	public HttpResponse getResponse(String s) throws Exception{
		HttpResponse httpResponse;
		String urlString = addGetParams(s);
		
		HttpGet httpGet = new HttpGet(urlString);
		HttpResponse httpResponse2 = httpClient.execute(httpGet);
		return httpResponse2;
	}
	
	/**
	 * ������Ϣ�����ǵķ�������ȥ.
	 * @param s
	 * @param map
	 * @param handler
	 */
	public void Post(String s,Map map,final Handler handler){
		String urlStr = addGetParams(s);
		
		//ͨ��һ���߳��������������һ����Ϣ
		MyResponseHandler myResponseHandler = new MyResponseHandler(handler);
		Map map1 = map;
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
			}
		}).start();
	}
	
	/**
	 * �������ݵ�����������ȥ.
	 * @param url
	 * @param map
	 * @return
	 */
	public String PostSync(String url,Map map){
		
		
		return null;
	}
	
	/**
	 * �ȷ���һЩ��Ϣ����������Ȼ�����������JSON��Ϣ����������Map��ʽ.
	 * @param url
	 * @param map
	 * @param jsonParse
	 * @return
	 */
	public Map postSync(String url,Map map,JsonParse jsonParse){
		
		return null;
	}
	
	private class MyResponseHandler implements ResponseHandler{

		final NetworkService this$0;
		final Handler handler;
		
		@Override
		public Object handleResponse(HttpResponse response)
				throws ClientProtocolException, IOException {
			HttpEntity httpEntity = response.getEntity();
			int status = response.getStatusLine().getStatusCode();
			String s1 = httpEntity.toString();
			String s2 = EntityUtils.toString(httpEntity);
			
			Bundle bundle = new Bundle();
			bundle.putInt("status", status);
			bundle.putString("responseInfo", s2);
			
			Message message = Message.obtain();
			message.setData(bundle);
			handler.sendMessage(message);
			return s2;
		}
		
		public MyResponseHandler(Handler handler){
			this$0 = NetworkService.this;
			this.handler = handler;
		}
	}
	
	private class MyThread extends Thread{
		final NetworkService this$0;
		final String abUrl;
		final Handler handler;
		final Map params;
		final ResponseHandler rhHandler;
		
		public MyThread(Map mapParams,String abUrl,Handler handler,
				ResponseHandler responseHandler) {
			this$0 = NetworkService.this;
			this.params = mapParams;
			this.abUrl = abUrl;
			this.handler = handler;
			this.rhHandler = responseHandler;
		}
		@Override
		public void run() {
			ArrayList<BasicNameValuePair> arrayList = new ArrayList<BasicNameValuePair>();
			
			//����һЩURL����
			String s1 = "";
			String s2 = "";
			BasicNameValuePair basicNameValuePair = new BasicNameValuePair(s1,s2);
			boolean flag = arrayList.add(basicNameValuePair);
			
			if(this.params != null){
				for (Iterator iterator = params.keySet().iterator();iterator.hasNext();) {
					String s3 = (String)iterator.next();
					String s4 = (String)params.get(s3);
					BasicNameValuePair basicNameValuePair2 = new BasicNameValuePair(s3, s4);
					boolean flag1 = arrayList.add(basicNameValuePair2);
				}
			}
			try {
				//Ȼ����URL����һЩ��Ϣ
				HttpPost httpPost = new HttpPost(this.abUrl);
				
				UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(arrayList,"UTF-8");
				
				httpPost.setEntity(urlEncodedFormEntity);
				DefaultHttpClient defaultHttpClient = httpClient;
				
				//�������ѯ��������
				Object obj = defaultHttpClient.execute(httpPost,rhHandler);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
}
