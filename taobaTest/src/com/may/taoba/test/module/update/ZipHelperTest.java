package com.may.taoba.test.module.update;

import com.may.taoba.module.update.ZipHelper;

import android.test.AndroidTestCase;

public class ZipHelperTest extends AndroidTestCase {

	public void testUnZipFolder() throws Exception{
		//ѹ�������治�ܰ������ĵ��ļ��У���Ȼ����û�а취ͨ����
		//ֻ�ܶ�/mnt/sdcard���ļ�������Ȩ��
		String pathFile = "/sdcard/android_test_zip.zip";
		String unPathFile = "/sdcard";
		ZipHelper.unZipFolder(pathFile, unPathFile);
	}
}
