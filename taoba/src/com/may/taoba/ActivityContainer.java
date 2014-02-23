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
