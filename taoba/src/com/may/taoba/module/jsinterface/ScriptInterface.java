package com.may.taoba.module.jsinterface;

import com.may.taoba.module.mybase.HtmlActivity;

/**
 * һ������js�Ļ���.
 * @author ZhangJian
 *
 */
public class ScriptInterface {

	protected HtmlActivity context;
	
	public ScriptInterface(){
	}

	public ScriptInterface(HtmlActivity webActivity){
		this.context = webActivity;
	}

	public HtmlActivity getContext() {
		return context;
	}

	public void setContext(HtmlActivity context) {
		this.context = context;
	}
	
	public void runInHandlerThread(Runnable runnable){
		boolean flag = getContext().getHandler().post(runnable);
	}
	
}
