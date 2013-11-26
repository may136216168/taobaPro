package com.may.taoba.test.module.update;

import com.may.taoba.module.update.VersionManager;

import android.test.AndroidTestCase;

public class VersionManagerTest extends AndroidTestCase {

	public void testGetRemoteVersion() throws Exception{
		assertNotNull(VersionManager.getRemoteVersion());
	}
	
	public void testGetLocalVersion() throws Exception{
		assertNotNull(VersionManager.getLocalVersion());
	}
	
	public void testGetFullVersion(){
		//���ƻ����ļ����ȶ�.
		assertEquals("1.0,1", VersionManager.getFullVersion());
	}
}
