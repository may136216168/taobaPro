package com.may.taoba.test.module.files;

import java.io.File;

import com.may.taoba.module.files.FileHelper;

import android.test.AndroidTestCase;
import android.util.Log;

public class FileHelperTest extends AndroidTestCase {

	public void testDelete() throws Exception{
		String pathName = "/sdcard/3/";
		boolean reuslt = FileHelper.delete(pathName);
		assertEquals(true, reuslt);
	}
	
	public void testDeleteFile() throws Exception{
		String pathName = "/sdcard/3";
		boolean result = FileHelper.deleteFile(pathName);
		assertEquals(true, result);
	}
	
	public void testDeleteDirectory() throws Exception{
		String pathName = "/sdcard/3/";
		boolean result = FileHelper.deleteDirectory(pathName);
	}
	
	public void testGetMIMEType() throws Exception{
		String fileName = "/sdcard/3/1.mp4";
		String result1 = FileHelper.getMIMEType(new File(fileName));
		Log.e("getMIMEType:>>.", result1);
		assertEquals("video/*", result1);
		
		String fileName2 = "/sdcard/3/2.mp3";
		String result2 = FileHelper.getMIMEType(new File(fileName2));
		Log.e("getMIMEType:>>.", result2);
		assertEquals("audio/*", result2);
		
		String fileName3 = "/sdcard/3/3.mid";
		String result3 = FileHelper.getMIMEType(new File(fileName3));
		Log.e("getMIMEType:>>.", result3);
		assertEquals("audio/*", result3);
		
		String fileName4 = "/sdcard/3/4.txt";
		String result4 = FileHelper.getMIMEType(new File(fileName4));
		Log.e("getMIMEType:>>.", result4);
		assertEquals("*/*", result4);
		
		String fileName5 = "/sdcard/3/5.png";
		String result5 = FileHelper.getMIMEType(new File(fileName5));
		Log.e("getMIMEType:>>.", result5);
		assertEquals("image/*", result5);
		
		String fileName6 = "/sdcard/3/zs_6867023.apk";
		String result6 = FileHelper.getMIMEType(new File(fileName6));
		Log.e("getMIMEType:>>.", result6);
		assertEquals("application/vnd.android.package-archive", result6);
	}
	
	public void testGetMIMEType1(){
		String fileName = "/sdcard/3/1.mp4";
		String result1 = FileHelper.getMIMEType(fileName);
		Log.e("getMIMEType:>>.", result1);
		assertEquals("video/*", result1);
		
		String fileName2 = "/sdcard/3/2.mp3";
		String result2 = FileHelper.getMIMEType(new File(fileName2));
		Log.e("getMIMEType:>>.", result2);
		assertEquals("audio/*", result2);
		
		String fileName3 = "/sdcard/3/3.mid";
		String result3 = FileHelper.getMIMEType(fileName3);
		Log.e("getMIMEType:>>.", result3);
		assertEquals("audio/*", result3);
		
		String fileName4 = "/sdcard/3/4.txt";
		String result4 = FileHelper.getMIMEType(fileName4);
		Log.e("getMIMEType:>>.", result4);
		assertEquals("*/*", result4);
		
		String fileName5 = "/sdcard/3/5.png";
		String result5 = FileHelper.getMIMEType(fileName5);
		Log.e("getMIMEType:>>.", result5);
		assertEquals("image/*", result5);
		
		String fileName6 = "/sdcard/3/zs_6867023.apk";
		String result6 = FileHelper.getMIMEType(fileName6);
		Log.e("getMIMEType:>>.", result6);
		assertEquals("application/vnd.android.package-archive", result6);
	}
}
