package com.may.taoba.module.net;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class JsonParse {

	private String[] args;
	public JsonParse(String[] as){
		this.args = as;
	}
	
	public String[] getKeys(){
		
		return args;
	}
	
	/**
	 * 将s里面的内容转换为Map对象里面的键值对.
	 * @param jsonStr
	 * @return
	 */
	public Map parse(String jsonStr){
		HashMap hashMap = null;
		try {
			hashMap = new HashMap();
			JSONObject jsonObj = new JSONObject(jsonStr);
			parseJson(jsonObj, hashMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hashMap;
	}
	
	public abstract void parseJson(JSONObject jsonObj,Map map) throws JSONException;
	
	
}
