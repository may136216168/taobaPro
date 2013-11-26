package com.may.taoba.module.update;

/**
 * �汾������.
 * @author ZhangJian
 *
 */
public class VersionManager {
	protected static LocalVersion localVersion;
	protected static RemoteVersion remoteVersion;
	private final static String VERSION_URL = "http://192.168.1.199/android_version.xml";
	
	public VersionManager(){
	}
	
	/**
	 * �õ��������İ汾��Ϣ.
	 * @return
	 */
	public static RemoteVersion getRemoteVersion(){
		if(remoteVersion == null){
			RemoteVersion remoteVersion1 = new RemoteVersion();
			if(remoteVersion1.loadFromUrl(VERSION_URL)){
				remoteVersion = remoteVersion1;
			}
		}
		return remoteVersion;
	}
	
	private final static String LOCAL_VERSION_XML = "file:///android_asset/version.xml";
	/**
	 * ��ȡ���صİ汾��Ϣ.
	 * @return
	 */
	public static LocalVersion getLocalVersion(){
		if(localVersion == null){
			LocalVersion localVersion1 = new LocalVersion();
			localVersion1.loadFromXmlFile(LOCAL_VERSION_XML);
			localVersion = localVersion1;
		}
		return localVersion;
	}
	
	/**
	 * �õ������İ汾����Ϣ,��client+"."+web-file
	 * @return
	 */
	public static String getFullVersion(){
		LocalVersion localVersion = getLocalVersion();
		StringBuilder stringBuilder = new StringBuilder(localVersion.clientVersion);
		stringBuilder.append(localVersion.webFileVersion);
		return stringBuilder.toString();
	}
	
	/**
	 * �Ƚ������汾�Ƿ�һ��
	 * @param version1
	 * @param version2
	 * @return
	 */
	private static boolean compareVersion(String version1,String version2){
		return (version1.compareTo(version2) == 0);
	}
	
	/**
	 * ��鱾�ذ汾��Զ�̰汾���Ƿ�һ��.
	 * @return
	 */
	public static boolean checkClientUpdate(){
		boolean flag = false;
		//����Զ�̿ͻ��˵İ汾��Ϣ
		remoteVersion = getRemoteVersion();
		if(remoteVersion == null){
			return flag;
		}
		//δ�����
		String localVersion = "1.0";
		String remoteVersionClientString = remoteVersion.getCurrentStableClientVersionInfo();
		flag = compareVersion(localVersion, remoteVersionClientString);
		return flag;
	}
}
