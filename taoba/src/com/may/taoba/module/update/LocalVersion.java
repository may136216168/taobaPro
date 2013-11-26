package com.may.taoba.module.update;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * ���ذ汾��.
 * @author ZhangJian
 *
 */
public class LocalVersion {

	/**
	 * ���ļ��л�ñ��ذ汾��Ϣ.
	 * @param path �ļ�·��
	 * @return
	 */
	public boolean loadFromXmlFile(String fileName){
		boolean flag = false;
		try{
			FileInputStream fileInputStream = new FileInputStream(fileName);
			SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
			XmlHandler xmlHandler = new XmlHandler();
			saxParser.parse(fileInputStream, xmlHandler);
			fileInputStream.close();//�ر��ļ���
			if(badXml == true){
				flag = false;
			}else{
				flag = true;
			}
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * ��ʾ��ȡ��XML�Ƿ�
	 */
	public boolean badXml;
	private final static String VERSION = "version";
	private final static String CLIENT = "client";
	private final static String WEB_FILE = "web-file";
	public String clientVersion;
	public String webFileVersion;
	
	class XmlHandler extends DefaultHandler{
		final LocalVersion this$0;
		public XmlHandler(){
			super();
			this$0 = LocalVersion.this;
			badXml = false;
		}
		@Override
		public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
			//��ȡxml�ĵ�������
			if(attributes != null){
				if(VERSION.equalsIgnoreCase(localName)){
					clientVersion = attributes.getValue(CLIENT);
					webFileVersion = attributes.getValue(WEB_FILE);
					if(clientVersion == null || webFileVersion == null){
						badXml = true;
					}
				}
			}else{
				badXml = true;
			}
		}
	}
}
