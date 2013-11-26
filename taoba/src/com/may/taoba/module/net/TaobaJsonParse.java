package com.may.taoba.module.net;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class TaobaJsonParse extends JsonParse {

	public TaobaJsonParse(String[] as) {
		super(as);
	}

	@Override
	public void parseJson(JSONObject jsonObj, Map map) throws JSONException {
		String[] as = getKeys();
		int length = as.length;
		
		try{
			for (int i = 0; i < length; i++) {
				String s = as[i];
				Object obj = jsonObj.get(s);
				map.put(s, obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
