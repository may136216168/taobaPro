package com.may.taoba.module.jsinterface;

import com.may.taoba.module.mybase.HtmlActivity;

/**
 * 一个包含js的基类.
 * @author ZhangJian
 *
 */
public class ScirptInterface {

	private HtmlActivity context;
	
	public ScirptInterface(){
	}

	public ScirptInterface(HtmlActivity webActivity){
		this.context = webActivity;
	}

	public HtmlActivity getContext() {
		return context;
	}

	public void setContext(HtmlActivity context) {
		this.context = context;
	}
	
	
}
