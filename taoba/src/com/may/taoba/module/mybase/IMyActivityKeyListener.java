package com.may.taoba.module.mybase;

import android.view.KeyEvent;

public interface IMyActivityKeyListener {

	//键盘虚似键的键码，第二是一个包含所有按键事件信息的对象
	public abstract boolean onKeyDown(int i,KeyEvent keyEvent);
}
