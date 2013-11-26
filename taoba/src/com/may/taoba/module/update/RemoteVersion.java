package com.may.taoba.module.update;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * 远程版本信息类.
 * @author ZhangJian
 *
 */
public class RemoteVersion {

	
	private List<ClientVersionInfo> clientVersionList;
	public String currentStableClientVersion;
	
	/**
	 * 客户端版本信息.
	 * 远程XML的Client节点的实体.
	 * @author ZhangJian
	 *
	 */
	public class ClientVersionInfo{
		public String clientVersion;
		public int versionCode;
		public String fileName;
		public String currentWebVersion;
		public String currentWebFile;
		
		final RemoteVersion this$0;
		public ClientVersionInfo(){
			this$0 = RemoteVersion.this;
		}
	}
	
	public RemoteVersion(){
		clientVersionList = new ArrayList<RemoteVersion.ClientVersionInfo>();
		
	}
	
	/**
	 * 加载服务上的版本信息.
	 * @param url
	 * @return
	 */
	public boolean loadFromUrl(String url){
		boolean flag = false;
		HttpURLConnection httpURLConnection = null;
		try {
			//打开链接
			httpURLConnection = (HttpURLConnection)(new URL(url)).openConnection();
			httpURLConnection.setConnectTimeout(6000);
			if(httpURLConnection.getResponseCode() != 200){
				flag = false;
			}
			//服务器的连接已经建立成功了
			InputStream inputStream = httpURLConnection.getInputStream();
			SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
			XmlHandler xmlHandler = new XmlHandler();
			saxParser.parse(inputStream, xmlHandler);
			
			inputStream.close();
			flag = true;

		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(httpURLConnection!=null){
				httpURLConnection.disconnect();
			}
		}
		
		return flag;
	}
	
	/**
	 * 取得当前web页面的版本.
	 * @return
	 */
	public String getCurrentWebVersion(){
		ClientVersionInfo clientVersionInfo = getCurrentClientVersionInfo();
		if(clientVersionInfo != null){
			return clientVersionInfo.currentWebVersion;
		}else{
			return "";
		}
		
	}
	
	/**
	 * 获得当前客户端的版本信息.
	 * @return
	 */
	public ClientVersionInfo getCurrentClientVersionInfo(){
		//配置文件里去读取我们的版本信息
		String s1 = "3.2";  //未有完成
		return getClientVersionInfo(s1);
	}
	
	/**
	 * 取得稳定的客户端版本信息.
	 * @return
	 */
	public String getCurrentStableClientVersionInfo(){
		return RemoteVersion.this.currentStableClientVersion;
	}
	
	/**
	 * 取得当前客户端的版本信息.
	 * @return
	 */
	public ClientVersionInfo getClientVersionInfo(String clientVersion){
		ClientVersionInfo clientVersionInfo = null;
		if(clientVersion == null || clientVersion.length() <= 0){
			return clientVersionInfo;
		}
		Iterator<ClientVersionInfo> iterator = clientVersionList.iterator();
		String s1;
		do{
			if(!iterator.hasNext()){
				break;
			}
			clientVersionInfo = iterator.next();
			s1 = clientVersionInfo.clientVersion;
		}while(clientVersion.equalsIgnoreCase(s1) ==false);
		return clientVersionInfo;
	}
	
	private final static String VERSION = "version";
	private final static String VERSION_CODE = "version-code";
	private final static String CLIENT_VERSION = "client-version";
	private final static String FILE = "file";
	private final static String CURRENT_WEB_VERSION = "current-web-version";
	private final static String CURRENT_WEB_FILE = "current-web-file";
	private final static String CLIENT = "client";
	private final static String CURRENT_STABLE_CLIENT = "current-stable-client";
	class XmlHandler extends DefaultHandler{
		final RemoteVersion this$0;
		
		public XmlHandler(){
			super();
			this$0 = RemoteVersion.this;
		}
		
		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			//将从服务器上读取的xml文件进行解析
			ClientVersionInfo clientVersionInfo = null;
			try {
				if(VERSION.equalsIgnoreCase(localName) == false){
					if(CLIENT.equalsIgnoreCase(localName) == false){
						//如果读取的不是当前的Version的节点，就什么也不做.
						return;
					}else{
						RemoteVersion remoteVersion = RemoteVersion.this;
						clientVersionInfo = remoteVersion.new ClientVersionInfo();
						
						//根据xml文件填充
						clientVersionInfo.versionCode = Integer.valueOf(attributes.getValue(VERSION_CODE));
						clientVersionInfo.clientVersion = attributes.getValue(CLIENT_VERSION);
						clientVersionInfo.fileName = attributes.getValue(FILE);
						clientVersionInfo.currentWebVersion = attributes.getValue(CURRENT_WEB_VERSION);
						clientVersionInfo.currentWebFile = attributes.getValue(CURRENT_WEB_FILE);
						
						clientVersionList.add(clientVersionInfo);
					}
				}else{
					//当标记是Version的时候
					RemoteVersion remoteVersion = RemoteVersion.this;
					remoteVersion.currentStableClientVersion = attributes.getValue(CURRENT_STABLE_CLIENT);
				}
			} catch (Exception e) {
				e.printStackTrace();
				if(clientVersionInfo == null){
					RemoteVersion remoteVersion = RemoteVersion.this;
					clientVersionInfo = remoteVersion.new ClientVersionInfo();
				}
				//表示错误
				clientVersionInfo.versionCode = 0;
			}
		}
		
		@Override
		public void startDocument() throws SAXException {
			clientVersionList.clear();
		}
		
	}
	
}
