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
 * ����ܶ�Activity������.
 * 1��ʵ��һЩ�����Ĺ���.
 * 2��Ӧ����һ����ջ���һ���˵������Activity
 * 3��Ӧ������ʾ��ʼ����ջ��������ѹ���ջ�Ĺ���.
 * 4��Ӧ������ʾĳһ��View������Activity���Ĺ���.
 * 5�����д�webҳ��Ĺ���.
 * @author ZhangJian
 *
 */
public class ActivityContainer extends ActivityGroup {
	
	//���嶯����
	Animation leftIn;
	Animation leftOut;
	Animation rightIn;
	Animation rightOut;
	
	//��ջ
	ActivityStack stack;
	
	//һ����������������activity������ʵ�ֶ���
	private ViewFlipper container;
	
	public ActivityContainer() {
	}
	
	public void openStackAndInit(ActivityStack activityStack,String s1,String s2){
		setStack(activityStack);
		
		this.getLocalActivityManager().removeAllActivities();
		
		if(activityStack.size() == 0 && StringTool.isEmpty(s1) ==false){
			//ͨ��url��һ������
			openUrl(s1,s2,true);
		}else{
			openStack(activityStack);
		}
	}
	
	/**
	 * 
	 * @param s
	 * @param s2
	 * @param flag ��ʾ�Ƿ��һ�μ���url
	 */
	private void openUrl(String s,String s2,boolean flag) {
		//��һ��web����
		Bundle bundle = new Bundle();
		bundle.putString("url", s);
		bundle.putString("ui_title", s2);
		openWebActivity(bundle,flag);
	}

	private void openWebActivity(Bundle bundle,boolean flags) {
		//�´�һ��������ʾwebҳ��activity,Ȼ��showView
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
	 * ��һ���Ѿ��еĶ�ջ�����view.
	 * @param activityStack
	 */
	private void openStack(ActivityStack activityStack) {
		setStack(activityStack);
		HeaderWebActivity header = activityStack.getTop();
		if(header != null){
			container.setInAnimation(null);
			container.setOutAnimation(null);
			container.removeAllViews();
			//��ʾ���ǵĴ���
			View view = header.getWindow().getDecorView();
			showView(view, 2, "");
		}
	}

	private void setStack(ActivityStack activityStack) {
		stack = activityStack;
	}

	/**
	 * ������ȥ��һЩ��ʼ���Ĺ���.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//��ʼ������,id��ʾ������xml�ļ�
		leftIn = AnimationUtils.loadAnimation(this, R.anim.left_in);
		leftOut = AnimationUtils.loadAnimation(this, R.anim.left_out);
		rightIn = AnimationUtils.loadAnimation(this, R.anim.right_in);
		rightOut = AnimationUtils.loadAnimation(this, R.anim.right_out);
		//��ʼ���������󣬲�����ӵ�ActivityContainer
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
		//view��ʾ�ڴ����������Ͻǵ�λ��
		FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1,-1);
		view.setLayoutParams(layoutParams);
		container.showNext();
//		((HtmlActivity)view.getContext()).;
		HtmlActivity ha = (HtmlActivity)view.getContext();
		//������Ҫ����һ��html��js����
//		ha.triggerSearch(query, appSearchData)
	}
	
	/**
	 * ���ص������Ĵ��ڣ�����������Ĵ��ڣ���ô����false.
	 * @return
	 */
	public boolean backToStackBottom(){
		boolean flag;
		if(stack.size() <= 1){
			flag = false;
		}else{
			//��������һ������
			container.removeAllViews();
			
			HeaderWebActivity hwa = stack.pop();
			View view = stack.pop2Bottom().getWindow().getDecorView();
			showView(view, 1, null);
			flag = true;
		}
		return flag;
	}
}
