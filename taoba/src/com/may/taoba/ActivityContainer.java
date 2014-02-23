package com.may.taoba;

import com.may.taoba.module.mybase.HtmlActivity;

import android.app.ActivityGroup;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ViewFlipper;

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
