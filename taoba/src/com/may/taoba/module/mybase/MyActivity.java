package com.may.taoba.module.mybase;

import com.may.taoba.module.TaoBaApplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

/**
 * 1、主要是接受我们的菜单信息.
 * 2、接收键盘信息.
 * @author ZhangJian
 *
 */
public class MyActivity extends Activity {

	
	public MyActivity() {
		super();
	}
	
	//菜单的接口
	MenuInterface menuInterface;
	//控制activity的一个类
	MyActivityController m_myActivityController;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//得到菜单接口类
		menuInterface = TaoBaApplication.getInstance().getGlobalMenuInterface();
		
		//会去设置控制类的值
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		if(menuInterface == null || menuInterface.createMenu(this, menu)){
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		super.onMenuItemSelected(featureId, item);
		return false;
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);;
		return true;
	}
	
	
}