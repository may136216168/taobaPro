package com.may.taoba.module.files;

import java.io.File;

public class FileHelper {
	
	public FileHelper(){
	}
	
	/**
	 * 删除文件.
	 * @param fileName文件名
	 * @return
	 */
	public static boolean delete(String fileName){
		File file = new File(fileName);
		boolean flag = false;
		if(file.exists() == false){
			flag = false;
		}else{
			if(file.isFile()){
				flag = deleteFile(fileName);
			}else{
				flag = deleteDirectory(fileName);
			}
		}
		return flag;
	}
	
	/**
	 * 删除目录.
	 * @param dir
	 * @return
	 */
	public static boolean deleteDirectory(String dir){
		File file;
		boolean flag = false;
		//dir "/"结尾，需要加一个/
		if(dir.endsWith("/") == false){
			StringBuilder sb = new StringBuilder(dir);
			String separator = File.separator;
			dir = sb.append(separator).toString();
		}
		file = new File(dir);
		if(file.exists() && file.isDirectory()){
			File aFile[] = file.listFiles();
			int length = aFile.length;
			
			for (int i = 0; i < aFile.length; i++) {
				if(aFile[i].isFile()){
					deleteFile(aFile[i].getAbsolutePath());
				}else{
					deleteDirectory(aFile[i].getAbsolutePath());
					flag = file.delete();
				}
			}
		}else{
			
		}
		return flag;
	}
	
	/**
	 * 只删除文件.
	 * @param fileName
	 * @return
	 */
	public static boolean deleteFile(String fileName){
		File file = new File(fileName);
		boolean flag=false;
		if(file.exists() && file.isFile()){
			file.delete();
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 获取文件扩展名.
	 * @param file
	 * @return
	 */
	public static String getMIMEType(File file){
		String fileName = file.getName();
		int index = fileName.lastIndexOf(".")+1;
		String fileName1 = fileName.substring(index, fileName.length());
		String resultStr = "";
		if(fileName1.equals("mp3") || fileName1.equals("mid") || fileName1.equals("wav") || fileName1.equals("audio")){
			resultStr = "audio";
		}else if(fileName1.equals("3gp") || fileName1.equals("mp4") || fileName1.equals("avi")){
			resultStr = "video";
		}else if(fileName1.equals("jpg") || fileName1.equals("gif") || fileName1.equals("png") || fileName1.equals("jpeg")){
			resultStr = "image";
		}else if(fileName1.equals("apk")){
			resultStr = "application/vnd.android.package-archive";
		}else{
			resultStr = "*";
		}
		if(fileName1.equals("apk") == false)
		{
			resultStr = resultStr + "/*";
		}
		return resultStr;
	}
	
	/**
	 * 获取文件扩展名.
	 * @param fileName
	 * @return
	 */
	public static String getMIMEType(String fileName){
		
		return getMIMEType(new File(fileName));
	}
}
