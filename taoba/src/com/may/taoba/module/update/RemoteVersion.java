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
 * Զ�̰汾��Ϣ��.
 * @author ZhangJian
 *
 */
public class RemoteVersion {

	
	private List<ClientVersionInfo> clientVersionList;
	public String currentStableClientVersion;
	
	/**
	 * �ͻ��˰汾��Ϣ.
	 * Զ��XML��Client�ڵ��ʵ��.
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
	 * ���ط����ϵİ汾��Ϣ.
	 * @param url
	 * @return
	 */
	public boolean loadFromUrl(String url){
		boolean flag = false;
		HttpURLConnection httpURLConnection = null;
		try {
			//������
			httpURLConnection = (HttpURLConnection)(new URL(url)).openConnection();
			httpURLConnection.setConnectTimeout(6000);
			if(httpURLConnection.getResponseCode() != 200){
				flag = false;
			}
			//�������������Ѿ������ɹ���
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
	 * ȡ�õ�ǰwebҳ��İ汾.
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
	 * ��õ�ǰ�ͻ��˵İ汾��Ϣ.
	 * @return
	 */
	public ClientVersionInfo getCurrentClientVersionInfo(){
		//�����ļ���ȥ��ȡ���ǵİ汾��Ϣ
		String s1 = "3.2";  //δ�����
		return getClientVersionInfo(s1);
	}
	
	/**
	 * ȡ���ȶ��Ŀͻ��˰汾��Ϣ.
	 * @return
	 */
	public String getCurrentStableClientVersionInfo(){
		return RemoteVersion.this.currentStableClientVersion;
	}
	
	/**
	 * ȡ�õ�ǰ�ͻ��˵İ汾��Ϣ.
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
			//���ӷ������϶�ȡ��xml�ļ����н���
			ClientVersionInfo clientVersionInfo = null;
			try {
				if(VERSION.equalsIgnoreCase(localName) == false){
					if(CLIENT.equalsIgnoreCase(localName) == false){
						//�����ȡ�Ĳ��ǵ�ǰ��Version�Ľڵ㣬��ʲôҲ����.
						return;
					}else{
						RemoteVersion remoteVersion = RemoteVersion.this;
						clientVersionInfo = remoteVersion.new ClientVersionInfo();
						
						//����xml�ļ����
						clientVersionInfo.versionCode = Integer.valueOf(attributes.getValue(VERSION_CODE));
						clientVersionInfo.clientVersion = attributes.getValue(CLIENT_VERSION);
						clientVersionInfo.fileName = attributes.getValue(FILE);
						clientVersionInfo.currentWebVersion = attributes.getValue(CURRENT_WEB_VERSION);
						clientVersionInfo.currentWebFile = attributes.getValue(CURRENT_WEB_FILE);
						
						clientVersionList.add(clientVersionInfo);
					}
				}else{
					//�������Version��ʱ��
					RemoteVersion remoteVersion = RemoteVersion.this;
					remoteVersion.currentStableClientVersion = attributes.getValue(CURRENT_STABLE_CLIENT);
				}
			} catch (Exception e) {
				e.printStackTrace();
				if(clientVersionInfo == null){
					RemoteVersion remoteVersion = RemoteVersion.this;
					clientVersionInfo = remoteVersion.new ClientVersionInfo();
				}
				//��ʾ����
				clientVersionInfo.versionCode = 0;
			}
		}
		
		@Override
		public void startDocument() throws SAXException {
			clientVersionList.clear();
		}
		
	}
	
}
