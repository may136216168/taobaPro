package com.may.taoba.module.mybase;

import com.may.taoba.module.TaoBaApplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

/**
 * 1����Ҫ�ǽ������ǵĲ˵���Ϣ.
 * 2�����ռ�����Ϣ.
 * @author ZhangJian
 *
 */
public class MyActivity extends Activity {

	
	public MyActivity() {
		super();
	}
	
	//�˵��Ľӿ�
	MenuInterface menuInterface;
	//����activity��һ����
	MyActivityController m_myActivityController;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//�õ��˵��ӿ���
		menuInterface = TaoBaApplication.getInstance().getGlobalMenuInterface();
		
		//��ȥ���ÿ������ֵ
		
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