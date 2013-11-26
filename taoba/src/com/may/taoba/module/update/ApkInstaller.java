package com.may.taoba.module.update;

import java.io.File;

import android.content.Intent;
import android.net.Uri;

import com.may.taoba.module.TaoBaApplication;

public class ApkInstaller {
	
	/**
	 * Apk安装
	 * @param fileName apk的文件名
	 */
	public static void install(String fileName){
		install(new File(fileName));
	}
	
	/**
	 * Apk安装
	 * @param file 文件夹对象
	 */
	public static void install(File file){
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.addFlags(0x10000000);
		String s = "application/vnd.android.package-archive";
		Uri uri = Uri.fromFile(file);
		intent.setDataAndType(uri, s);
		TaoBaApplication.getInstance().startActivity(intent);
	}
}
