package com.may.taoba;

import java.util.ArrayList;
import java.util.List;

import android.text.StaticLayout;

import com.may.taoba.module.mybase.HtmlActivity;

/**
 * �������һЩActivity����Щ��ͼ����ͨ��ViewFlipper�����л���
 * @author ZhangJian
 *
 */
public class ActivityStack {

	//List,��ջ�����ŵ���һ�����Է�webView��ͼ
	//���HtmlActivity�������������
	private List stackList;
	
	public ActivityStack() {
		stackList = new ArrayList();
		
	}
	
	//����
	public void push(HtmlActivity htmlActivity){
		stackList.add(htmlActivity);
	}
	
	//��ջ
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
	//�õ���һ��Ԫ��
	public HeaderWebActivity getTop(){
		int i = stackList.size() - 1;
		return (HeaderWebActivity)stackList.get(i);
	}
	
	//��������������ֻʣ�¶�ջ�ײ���Ԫ��
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
	
	
	//�ж϶�ջ�Ĵ�С�ĺ���
	public int size(){
		return stackList.size();
	}
}
