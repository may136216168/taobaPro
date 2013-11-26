package com.may.taoba.module.update;

import java.io.File;

import android.content.Intent;
import android.net.Uri;

import com.may.taoba.module.TaoBaApplication;

public class ApkInstaller {
	
	/**
	 * Apk��װ
	 * @param fileName apk���ļ���
	 */
	public static void install(String fileName){
		install(new File(fileName));
	}
	
	/**
	 * Apk��װ
	 * @param file �ļ��ж���
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
