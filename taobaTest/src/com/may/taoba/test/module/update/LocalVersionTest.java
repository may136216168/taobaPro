package com.may.taoba.test.module.update;

import com.may.taoba.module.update.LocalVersion;

import android.test.AndroidTestCase;

public class LocalVersionTest extends AndroidTestCase {

	public void testLoadFromXmlFileAsBadFileName() throws Exception{
		LocalVersion localVersion = new LocalVersion();
		boolean flag = localVersion.loadFromXmlFile("");
		assertEquals(false, flag);
	}
	
	public void loadFromXmlFileAsGoodFileName() throws Exception{
		LocalVersion localVersion = new LocalVersion();
		// 在我 们的andrdoird当中，特别是模拟器中，去assets中的内容有可能是访问不到，那么怎么办，
		// 将它复制我们/data/data/love.android.taoba/
		// /sdcard/
		//未
		String fileName = "";
		boolean flag = localVersion.loadFromXmlFile(fileName);
		assertEquals(true, flag);
	}
}
