package com.may.taoba;

import java.util.UUID;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ViewFlipper;

import com.may.taoba.module.mybase.HtmlActivity;
import com.may.taoba.tools.StringTool;

/**
 * 管理很多Activity的容器.
 * 1、实现一些动画的功能.
 * 2、应该有一个堆栈存放一个菜单下面的Activity
 * 3、应该有显示初始化堆栈，弹出，压入堆栈的功能.
 * 4、应该有显示某一个View（或者Activity）的功能.
 * 5、还有打开web页面的功能.
 * @author ZhangJian
 *
 */
public class ActivityContainer extends ActivityGroup {
	
	//定义动画类
	Animation leftIn;
	Animation leftOut;
	Animation rightIn;
	Animation rightOut;
	
	//堆栈
	ActivityStack stack;
	
	//一个容器，用来容纳activity，并且实现动画
	private ViewFlipper container;
	
	public ActivityContainer() {
	}
	
	public void openStackAndInit(ActivityStack activityStack,String s1,String s2){
		setStack(activityStack);
		
		this.getLocalActivityManager().removeAllActivities();
		
		if(activityStack.size() == 0 && StringTool.isEmpty(s1) ==false){
			//通过url打开一个窗口
			openUrl(s1,s2,true);
		}else{
			openStack(activityStack);
		}
	}
	
	/**
	 * 
	 * @param s
	 * @param s2
	 * @param flag 表示是否第一次加载url
	 */
	private void openUrl(String s,String s2,boolean flag) {
		//打开一个web窗口
		Bundle bundle = new Bundle();
		bundle.putString("url", s);
		bundle.putString("ui_title", s2);
		openWebActivity(bundle,flag);
	}

	private void openWebActivity(Bundle bundle,boolean flags) {
		//新打开一个可以显示web页面activity,然后showView
		container.setInAnimation(leftIn);
		container.setOutAnimation(leftOut);
		
		bundle.putBoolean("isFirst", flags);
		Intent intent = new Intent(this,HeaderWebActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
		intent.putExtras(bundle);
		
		String id = UUID.randomUUID().toString();
		Window window = this.getLocalActivityManager().startActivity(id, intent);
		
		View view = window.getDecorView();
		
		if(bundle != null){
			String s1 = bundle.getString("not_in_stack");
			if("true".equalsIgnoreCase(s1)==false){
				HeaderWebActivity headerWebActivity = (HeaderWebActivity)window.getContext();
				headerWebActivity.setId(id);
				stack.push(headerWebActivity);
			}
		}
		showView(view, 0, null);
	}

	/**
	 * 打开一个已经有的堆栈里面的view.
	 * @param activityStack
	 */
	private void openStack(ActivityStack activityStack) {
		setStack(activityStack);
		HeaderWebActivity header = activityStack.getTop();
		if(header != null){
			container.setInAnimation(null);
			container.setOutAnimation(null);
			container.removeAllViews();
			//显示我们的窗体
			View view = header.getWindow().getDecorView();
			showView(view, 2, "");
		}
	}

	private void setStack(ActivityStack activityStack) {
		stack = activityStack;
	}

	/**
	 * 在这里去做一些初始化的工作.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//初始化动画,id表示动画的xml文件
		leftIn = AnimationUtils.loadAnimation(this, R.anim.left_in);
		leftOut = AnimationUtils.loadAnimation(this, R.anim.left_out);
		rightIn = AnimationUtils.loadAnimation(this, R.anim.right_in);
		rightOut = AnimationUtils.loadAnimation(this, R.anim.right_out);
		//初始化容器对象，并且添加到ActivityContainer
		container = new ViewFlipper(this);
		FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1,-1);
		container.setLayoutParams(layoutParams);
		this.setContentView(container);
	}
	
	/**
	 * 
	 * @param view
	 * @param i
	 * @param s
	 */
	private void showView(View view,int i,String s){
		container.addView(view);
		//view显示在窗口上面左上角的位置
		FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1,-1);
		view.setLayoutParams(layoutParams);
		container.showNext();
//		((HtmlActivity)view.getContext()).;
		HtmlActivity ha = (HtmlActivity)view.getContext();
		//这里需要调用一个html的js函数
//		ha.triggerSearch(query, appSearchData)
	}
	
	/**
	 * 返回到最底面的窗口，如果是最底面的窗口，那么返回false.
	 * @return
	 */
	public boolean backToStackBottom(){
		boolean flag;
		if(stack.size() <= 1){
			flag = false;
		}else{
			//到最底面的一个窗口
			container.removeAllViews();
			
			HeaderWebActivity hwa = stack.pop();
			View view = stack.pop2Bottom().getWindow().getDecorView();
			showView(view, 1, null);
			flag = true;
		}
		return flag;
	}
}
