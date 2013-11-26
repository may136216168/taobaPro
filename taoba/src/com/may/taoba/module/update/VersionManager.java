package com.may.taoba.module.update;

/**
 * 版本管理器.
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
	 * 得到服务器的版本信息.
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
	 * 获取本地的版本信息.
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
	 * 得到完整的版本号信息,由client+"."+web-file
	 * @return
	 */
	public static String getFullVersion(){
		LocalVersion localVersion = getLocalVersion();
		StringBuilder stringBuilder = new StringBuilder(localVersion.clientVersion);
		stringBuilder.append(localVersion.webFileVersion);
		return stringBuilder.toString();
	}
	
	/**
	 * 比较两个版本是否一致
	 * @param version1
	 * @param version2
	 * @return
	 */
	private static boolean compareVersion(String version1,String version2){
		return (version1.compareTo(version2) == 0);
	}
	
	/**
	 * 检查本地版本与远程版本，是否一样.
	 * @return
	 */
	public static boolean checkClientUpdate(){
		boolean flag = false;
		//读出远程客户端的版本信息
		remoteVersion = getRemoteVersion();
		if(remoteVersion == null){
			return flag;
		}
		//未有完成
		String localVersion = "1.0";
		String remoteVersionClientString = remoteVersion.getCurrentStableClientVersionInfo();
		flag = compareVersion(localVersion, remoteVersionClientString);
		return flag;
	}
}
