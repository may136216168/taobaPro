package com.may.taoba.bean;

import java.io.Serializable;

public class City implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//主要是有几个变量，第一个ID，第二个城市名，第三个拼音
	public String id;
	public String name;
	public String pinyin;
	
	public City() {
	}
}
