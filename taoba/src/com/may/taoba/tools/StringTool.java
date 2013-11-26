package com.may.taoba.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * ×Ö·û´®¹¤¾ßÀà
 * @author ZhangJian
 *
 */
public class StringTool {

	public static Boolean isEmpty(String value){
		boolean flag;
		if(value == null || value.length() <= 0){
			flag = true;
		}else{
			flag = false;
		}
		return flag;
	}
	
	public static String getString(String s,String s1){
		String s2 = isEmpty(s1).booleanValue()?s1:s;
		return s2;
	}
	
	public static String inputStream2String(InputStream inputStram) throws IOException{
		InputStreamReader inputStreamReader = new InputStreamReader(inputStram);
		BufferedReader bufferReader = new BufferedReader(inputStreamReader);
		StringBuilder stringBuilder = new StringBuilder();
		do{
			String s = bufferReader.readLine();
			if(s != null){
				stringBuilder.append(s).append("\n");
			}else{
				bufferReader.close();
				return stringBuilder.toString();
			}
		}while(true);
	}
	
	public static Map convertParamsToMap(String s){
		
		return null;
	}
	
	/**
	 * ×Ö·û´®Æ´½Ó.
	 * @param collection
	 * @param s
	 * @return
	 */
	public static String join(Collection collection,String s){
		String s1;
		if(collection.size() == 0){
			s1 = "";
		}else{
			StringBuilder stringBuilder = new StringBuilder();
			for (Iterator obj = collection.iterator();obj.hasNext();) {
				String s2 = (String)obj.next();
				stringBuilder.append(s2).append(s);
			}
			
			if(stringBuilder.length() > 0){
				int i = stringBuilder.length() - 1;
				int j = stringBuilder.length();
				stringBuilder.delete(i, j);
			}
			s1 = stringBuilder.toString();
		}
		return s1;
	}
	
	public static String fromBytes(byte abyte0[])
	{
		StringBuffer stringbuffer = new StringBuffer("");
		int i = 0;
		do
		{
			int j = abyte0.length;
			if (i < j)
			{
				int k = abyte0[i];
				if (k < 0)
					k += 256;
				StringBuffer stringbuffer1;
				if (k < 16)
					stringbuffer1 = stringbuffer.append("0");
				String s = Integer.toHexString(k);
				StringBuffer stringbuffer2 = stringbuffer.append(s);
				i++;
			} else
			{
				return stringbuffer.toString();
			}
		} while (true);
	}
}
