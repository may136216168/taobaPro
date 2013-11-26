package com.may.taoba.module;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.content.res.AssetManager;

import com.may.taoba.tools.StringTool;

public class Config {

	static class ConfigXMLHandler extends DefaultHandler{
		@Override
		public void startDocument() throws SAXException {
			super.startDocument();
		}
		
		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			super.characters(ch, start, length);
			if("remote-prefix".equalsIgnoreCase(tagName) == false){
				if("remote-update-path".equalsIgnoreCase(tagName)){
					String s1 = new String(ch,start,length);
					String s2 = Config.REMOTE_UPDATE_PATH;
					Config.REMOTE_UPDATE_PATH = StringTool.getString(s1, s2);
				}else if("remote-version-url".equalsIgnoreCase(tagName)){
					String s1 = new String(ch,start,length);
					String s2 = Config.REMOTE_VERSION_PATH;
					Config.REMOTE_VERSION_PATH = StringTool.getString(s1, s2);
				}
			}else{
				String s1 = new String(ch,start,length);
				String s2 = Config.REMOTE_PREFIX;
				Config.REMOTE_PREFIX = StringTool.getString(s1, s2);
			}
			
		}
		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			super.startElement(uri, localName, qName, attributes);
			tagName = localName;
			if("config".equalsIgnoreCase(localName) == false){
				if("database".equalsIgnoreCase(localName)){
					String s1 = attributes.getValue("name");
					String s2 = Config.DATABASE_NAME;
					Config.DATABASE_NAME = StringTool.getString(s1, s2);
				}else if("assets".equalsIgnoreCase(localName)){
					ASSET_FOLDERS.clear();
				}else if("asset".equalsIgnoreCase(localName)){
					String path = attributes.getValue("path");
//					String type = attributes.getValue("type");
					String copy = attributes.getValue("copy");
					String config = attributes.getValue("config");
					
					if("true".equalsIgnoreCase(copy) && StringTool.isEmpty(path).booleanValue() == false){
						ASSET_FOLDERS.add(path);
					}
					
					if("WEB_FILE_FOLDER".equalsIgnoreCase(config)){
						Config.WEB_FILE_FOLDER = config;
					}
					
					if("UPDATE_FILE_FOLDER".equalsIgnoreCase(config)){
						Config.UPDATE_FILE_FOLDER = config;
					}
				}else if("javascript-bridges".equalsIgnoreCase(localName)){
					JAVASCRIPT_BRIDGES.clear();
				}else if("javascript-bridge".equalsIgnoreCase(localName)){
					String name = attributes.getValue("name");
					String classStr = attributes.getValue("class");
					
					if(StringTool.isEmpty(name) == false && StringTool.isEmpty(classStr) == false){
						Config.JAVASCRIPT_BRIDGES.put(name, classStr);
					}
				}
			}else{
				String s1 = attributes.getValue("client-tag");
				String s2 = Config.CLIENT_TAG;
				Config.CLIENT_TAG = StringTool.getString(s1, s2);
				
				s1 = attributes.getValue("client-version");
				s2 = Config.CLIENT_TAG;
				Config.CLIENT_TAG = StringTool.getString(s1, s2);
				
				s1 = attributes.getValue("page-source");
				s2 = Config.PAGE_SOURCE;
				Config.PAGE_SOURCE = StringTool.getString(s1, s2);
				
				s1 = attributes.getValue("debug");
				Config.DEBUG = Boolean.valueOf("true".equalsIgnoreCase(s1));
			}
		}
		
		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			super.endElement(uri, localName, qName);
			tagName = "";
		}
	}
	public final static String ANDROID_ID = "";
	public static Boolean DEBUG = false;
	public static String CLIENT_TAG = "";
	public static String CLIENT_VERSION = "";
	public static String WEB_FILE_FOLDER = "web";
	public static String UPDATE_FILE_FOLDER = "update";
	
	public static String DATABASE_NAME = "";
	
	public static String PAGE_SOURCE = "";
	
	public static List<String> ASSET_FOLDERS = null;
	public static Map<String,String> JAVASCRIPT_BRIDGES = null;
	
	public static String tagName = "";
	public static String REMOTE_PREFIX;
	public static String REMOTE_UPDATE_PATH;
	public static String REMOTE_VERSION_PATH;
	
	public static String WEB_FILE_PREFIX;
	
	//file://
	
	public Config(){
	}
	
	
	public static void InitConfig(){
		try {
			AssetManager assetManager = TaoBaApplication.getInstance().getAssets();
			InputStream inputStream = assetManager.open("config.xml");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static{
		DATABASE_NAME ="tabadb";
		PAGE_SOURCE = "local";
		CLIENT_TAG = "android_1";
		CLIENT_VERSION = "1.0";
		ASSET_FOLDERS = new ArrayList<String>();
		JAVASCRIPT_BRIDGES = new HashMap<String,String>();
		REMOTE_UPDATE_PATH = REMOTE_PREFIX+"/update/";
		REMOTE_PREFIX = "http://d.tuan800/dl/mobile/wepapp/";
		REMOTE_VERSION_PATH = REMOTE_PREFIX +"/ver.xml";
	}
}
