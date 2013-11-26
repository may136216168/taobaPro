package com.may.taoba.module.mybase;

import android.app.Activity;
import android.view.KeyEvent;

/**
 * 这个类用来控制HtmlActivity的显示，关闭，跳转等功能.
 * 需要将他要控制的activity的实例传进来，为了以后操作.
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
