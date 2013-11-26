package com.may.taoba.test.module.update;

import com.may.taoba.module.update.ZipHelper;

import android.test.AndroidTestCase;

public class ZipHelperTest extends AndroidTestCase {

	public void testUnZipFolder() throws Exception{
		//压缩包里面不能包含中文的文件夹，不然测试没有办法通过！
		//只能对/mnt/sdcard的文件来夹有权限
		String pathFile = "/sdcard/android_test_zip.zip";
		String unPathFile = "/sdcard";
		ZipHelper.unZipFolder(pathFile, unPathFile);
	}
}
