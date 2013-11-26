package com.may.taoba.test.module.update;

import com.may.taoba.module.update.RemoteVersion;
import com.may.taoba.module.update.RemoteVersion.ClientVersionInfo;

import android.test.AndroidTestCase;
import android.util.Log;

public class RemoteVersionTest extends AndroidTestCase {

	public void testLoadFromUrl() throws Exception{
		String serverURL = "http://192.168.1.199/android_version.xml";
		RemoteVersion remoteVersion = new RemoteVersion();
		boolean flag = remoteVersion.loadFromUrl(serverURL);
		Log.d("testLoadFromUrl", "::::>>>>>>>>>>>");
		assertEquals(true, flag);
	}
	
	public void testGetCurrentWebVersion() throws Exception{
		String serverURL = "http://192.168.1.199/android_version.xml";
		RemoteVersion remoteVersion = new RemoteVersion();
		boolean flag = remoteVersion.loadFromUrl(serverURL);
		assertEquals(true, flag);
		String webVersion = remoteVersion.getCurrentWebVersion();
		assertEquals(webVersion, "2");
	}
	
	public void testGetCurrentClientVersionInfo() throws Exception{
		String serverURL = "http://192.168.1.199/android_version.xml";
		RemoteVersion remoteVersion = new RemoteVersion();
		boolean flag = remoteVersion.loadFromUrl(serverURL);
		Log.e("testGetCurrentClientVersionInfo::>>", flag+"");
		assertEquals(true, flag);
		ClientVersionInfo clientVersionInfo = remoteVersion.getCurrentClientVersionInfo();
		int s1 = clientVersionInfo.versionCode;
		Log.e("testGetCurrentClientVersionInfo::>>", s1+"");
		assertEquals(s1, 14);
		assertEquals(clientVersionInfo.fileName, "tuan800_daquan.3.2.apk");
		assertEquals(clientVersionInfo.currentWebFile, "android_web.3.2.2.aip");
		assertEquals(clientVersionInfo.currentWebVersion, "2");
	}
	
	public void testGetClientVersionInfo() throws Exception{
		String serverURL = "http://192.168.1.199/android_version.xml";
		RemoteVersion remoteVersion = new RemoteVersion();
		boolean flag = remoteVersion.loadFromUrl(serverURL);
		Log.e("testGetCurrentClientVersionInfo::>>", flag+"");
		assertEquals(true, flag);
		
		String clientVersion = "3.0";
		ClientVersionInfo clientVersionInfo = remoteVersion.getClientVersionInfo(clientVersion);
		int s1 = clientVersionInfo.versionCode;
		Log.e("testGetCurrentClientVersionInfo::>>", s1+"");
		assertEquals(s1, 12);
		assertEquals(clientVersionInfo.fileName, "tuan800_daquan.3.0.apk");
		assertEquals(clientVersionInfo.currentWebFile, "android_web.3.0.2.zip");
		assertEquals(clientVersionInfo.currentWebVersion, "2");
	}
	
}
