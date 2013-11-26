package com.may.taoba.test.module.update;

import java.io.File;

import com.may.taoba.module.update.ApkInstaller;

import android.test.AndroidTestCase;

public class ApkInstallerTest extends AndroidTestCase {

	public void testInstall() throws Exception{
		String apkFileName = "/sdcard/zs_6867023.apk";
		ApkInstaller.install(apkFileName);
	}
	
	public void testInstall1() throws Exception{
		String apkFileName = "/sdcard/zs_6867023.apk";
		File file = new File(apkFileName);
		ApkInstaller.install(file);
	}
}
