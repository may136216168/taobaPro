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
		// ���� �ǵ�andrdoird���У��ر���ģ�����У�ȥassets�е������п����Ƿ��ʲ�������ô��ô�죬
		// ������������/data/data/love.android.taoba/
		// /sdcard/
		//δ
		String fileName = "";
		boolean flag = localVersion.loadFromXmlFile(fileName);
		assertEquals(true, flag);
	}
}
