package com.may.taoba.test.module.net;

import java.util.Map;

import android.test.AndroidTestCase;
import android.util.Log;

import com.may.taoba.module.net.TaobaJsonParse;

public class TaobaJsonParseTest extends AndroidTestCase {

	public void testParse() throws Exception{
		TaobaJsonParse jsonParse  = new TaobaJsonParse(new String[]{"categories"});
		String jsonStr = "{\"categories\":[{\"total\":4234,\"name\":\"\\u7f8e\\u98df\\u5929\\u4e0b\",\"id\":\"1\"},{\"total\":5176,\"name\":\"\\u4f11\\u95f2\\u5a31\\u4e50\",\"id\":\"3\"},{\"total\":5344,\"name\":\"\\u751f\\u6d3b\\u670d\\u52a1\",\"id\":\"2\"},{\"total\":2356,\"name\":\"\\u9152\\u5e97\\u65c5\\u6e38\",\"id\":\"22\"},{\"total\":15423,\"name\":\"\\u7f51\\u8d2d\\u7cbe\\u54c1\",\"id\":\"4\"},{\"total\":31,\"name\":\"\\u5176\\u4ed6\\u56e2\\u8d2d\",\"id\":\"6\"}]}";
		Map map = jsonParse.parse(jsonStr);
		
		if(map==null){
			Log.e("testParse", "map is null");
		}
		
		Log.e("testParse", map.get("categories").toString());
	}
}
