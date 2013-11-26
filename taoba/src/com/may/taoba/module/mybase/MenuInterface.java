package com.may.taoba.module.mybase;

import android.app.Activity;
import android.view.Menu;

public abstract class MenuInterface {

	public MenuInterface() {
	}
	
	//定义三个抽象涵数
	public abstract boolean createMenu(Activity activity,Menu menu);
	public abstract boolean optionsItemSelected(Activity activity,Menu menu);
	public abstract boolean prepareOptionsMenu(Activity activity,Menu menu);
}
