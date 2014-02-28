package com.may.taoba.module.jsinterface;

import com.may.taoba.module.mybase.HtmlActivity;

/**
 * 一个包含js的基类.
 * @author ZhangJian
 *
 */
public class ScriptInterface {

	private HtmlActivity context;
	
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
	
	
}
