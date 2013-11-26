package com.may.taoba.module.mybase;

import android.app.Activity;
import android.view.KeyEvent;

/**
 * �������������HtmlActivity����ʾ���رգ���ת�ȹ���.
 * ��Ҫ����Ҫ���Ƶ�activity��ʵ����������Ϊ���Ժ����.
 * @author ZhangJian
 *
 */
public class MyActivityController implements IMyActivityKeyListener {

	private Activity m_activityActivity;
	
	public MyActivityController() {
	}
	
	public MyActivityController(Activity activity){
		this.m_activityActivity = activity;
	}
	
	public Activity geContext(){
		return m_activityActivity;
	}
	
	public void setContext(Activity activity){
		m_activityActivity = activity;
	}
	
	@Override
	public boolean onKeyDown(int i, KeyEvent keyEvent) {
		
		return false;
	}

}
