package com.may.taoba.module;

import com.may.taoba.module.mybase.MenuInterface;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class TaoBaApplication extends Application {

	public static TaoBaApplication application;
	public MenuInterface globalMenuInterface;
	
	public TaoBaApplication(){
		super();
		application = this;
	}
	
	public static TaoBaApplication getInstance(){
		return application;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
	}
	
	public int getVersionCode(){
		int VersionCode = -1;
		try {
			PackageManager packageManager= getPackageManager();
			String name = getPackageName();
			VersionCode = packageManager.getPackageInfo(name, 0).versionCode;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return VersionCode;
	}
	
	/**
	 * 这个globalMenuInterface的赋值在其他地方进行的.
	 * @return
	 */
	public MenuInterface getGlobalMenuInterface(){
		
		return null;
	}
	
	/**
	 * 获取应用程序的绝对路径.
	 * @return 绝对路径.
	 */
	public static String getAppFilePath(){
		return application.getFilesDir().getAbsolutePath();
	}
}
