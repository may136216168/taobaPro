package com.may.taoba;

import java.util.ArrayList;
import java.util.List;

import android.text.StaticLayout;

import com.may.taoba.module.mybase.HtmlActivity;

/**
 * 用来存放一些Activity，这些视图可以通过ViewFlipper进行切换、
 * @author ZhangJian
 *
 */
public class ActivityStack {

	//List,堆栈里面存放的是一个可以放webView视图
	//存放HtmlActivity类或者其派生类
	private List stackList;
	
	public ActivityStack() {
		stackList = new ArrayList();
		
	}
	
	//进堆
	public void push(HtmlActivity htmlActivity){
		stackList.add(htmlActivity);
	}
	
	//出栈
	public HeaderWebActivity pop(){
		if(stackList == null && stackList.size() == 0){
			return null;
		}else{
			HeaderWebActivity htmlActivity = getTop();
			int i = stackList.size() - 1;
			Object obj = stackList.remove(i);
			return htmlActivity;
		}
	}
	//得到第一个元素
	public HeaderWebActivity getTop(){
		int i = stackList.size() - 1;
		return (HeaderWebActivity)stackList.get(i);
	}
	
	//弹出函数，就是只剩下堆栈底部的元素
	public HtmlActivity pop2Bottom(){
		if(stackList.size() == 0){
			return null;
		}else if(stackList.size() == 1){
			return (HtmlActivity)stackList.get(0);
		}else{
			for (int i = stackList.size() -1; i > 0; i--) {
				stackList.remove(i);
			}
			return (HtmlActivity)stackList.get(0);
		}
	}
	
	
	//判断堆栈的大小的函数
	public int size(){
		return stackList.size();
	}
}
