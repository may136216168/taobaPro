package com.may.taoba;

import java.util.HashMap;
import java.util.Map;

import android.app.ActivityGroup;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ViewFlipper;

/**
 * ActivityGroup
 * 他可以用来管理一组Activity,那么我的显示列表的这一部份窗口就是多个activity组成
 * @author ZhangJian
 *
 */
public class MainActivity extends ActivityGroup {

	private ViewFlipper container;
	private ActivityContainer innerContainer;
	private LocalActivityManager localActivityManager;
	private Map map;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		ininMap();
		container = (ViewFlipper)findViewById(R.id.container);
		//得到管理窗口的LocalActivityManager
		localActivityManager = this.getLocalActivityManager();
		//第一次需要打开的页面
		Intent intent = new Intent (this,ActivityContainer.class);
		
		Window window = localActivityManager.startActivity("main_container", intent);
		innerContainer = (ActivityContainer)window.getContext();
		
		View view = window.getDecorView();
		container.addView(view);
		container.showNext();
		
		//将网嶥给显示出来
		openStackView("today_tuan","category.html","正在团购");
		
		initCheckClick();
	}
	
	private void openStackView(String name,String fileName,String title){
		ActivityStack activityStack = (ActivityStack)map.get(name);
		innerContainer.openStackAndInit(activityStack, fileName, title);
	}
	
	private void ininMap(){
		map = new HashMap();
		ActivityStack activityStack1 = new ActivityStack();
		map.put("today_tuan", activityStack1);
		
		ActivityStack activityStack2 = new ActivityStack();
		map.put("near_tuan", activityStack2);
		
		ActivityStack activityStack3 = new ActivityStack();
		map.put("my_center", activityStack3);
		
		ActivityStack activityStack4 = new ActivityStack();
		map.put("more_info", activityStack4);
	}
	/**
	 * 初始化RadionButton事件
	 */
	private void initCheckClick(){
		MyCheckChangeListener myCheckChangeListener = new MyCheckChangeListener();
		((RadioButton)findViewById(R.id.btn_tab_today_tuan)).setOnCheckedChangeListener(myCheckChangeListener);
		((RadioButton)findViewById(R.id.btn_tab_near_tuan)).setOnCheckedChangeListener(myCheckChangeListener);
		((RadioButton)findViewById(R.id.btn_tab_my_center)).setOnCheckedChangeListener(myCheckChangeListener);
		((RadioButton)findViewById(R.id.btn_tab_more_info)).setOnCheckedChangeListener(myCheckChangeListener);
	}
	
	private class MyCheckChangeListener implements OnCheckedChangeListener{

		final MainActivity this$0;
		public MyCheckChangeListener() {
			super();
			this$0 = MainActivity.this;
		}
		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			switch (buttonView.getId()) {
			case R.id.btn_tab_today_tuan:
				container.showNext();
				break;
			case R.id.btn_tab_more_info:
				container.showNext();
				break;
			case R.id.btn_tab_my_center:
				container.showNext();
				break;
			case R.id.btn_tab_near_tuan:
				container.showNext();
				break;
			}
		}

		
	}
}
