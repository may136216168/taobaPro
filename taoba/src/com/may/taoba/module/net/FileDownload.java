package com.may.taoba.module.net;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import com.may.taoba.module.files.FileHelper;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

/**
 * 文件下载.
 * @author ZhangJian.
 *
 */
public class FileDownload {

	Context context;
	public FileDownload() {
	}
	
	public FileDownload(Context context){
		this.context = context;
	}
	
	/**
	 * 
	 * @param s
	 * @param s2
	 * @param handler
	 */
	private void download(String url,String fileName,Handler handler){
		int length,j,k;
		FileOutputStream fileOutputStream;
		byte[] buffer;
		try {
			URLConnection urlConnection = (new URL(url)).openConnection();
			urlConnection.connect();
			
			length = urlConnection.getContentLength();
			InputStream inputStream = urlConnection.getInputStream();
			int l;
			sendProgressMessage(handler, 0, 0);
			if(context == null){
				fileOutputStream = new FileOutputStream(fileName);
			}else{
				fileOutputStream = context.openFileOutput(fileName, 3);
			}
			
			j=0;//存储现在读了多少数据
			while(true){
				buffer = new byte[1024];
				k = inputStream.read(buffer);
				
				if(k >= 0){
					fileOutputStream.write(buffer,0,k);
					j+=k;
					int present = (j*100)/length;
					sendProgressMessage(handler, present, 1);
				}else{
					inputStream.close();
					fileOutputStream.close();
					int present = (j*100)/length;
					sendProgressMessage(handler, present, 2);
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 向UI线程发送一个消息,这个消息是关于进度条的
	 * @param handler
	 * @param i 发送哪一种类型的消息
	 * @param j
	 */
	private void sendProgressMessage(Handler handler,int i,int j){
		if(handler != null){
			Message msg = Message.obtain();
			msg.what = i;
			msg.arg1 = j;
			handler.sendMessage(msg);
		}
	}
	
	public void download2(String remoteFile,String localFile,final Handler handler){
		boolean flag;
		if(new File(localFile).exists()){
			flag = FileHelper.delete(localFile);
		}
		FileRunable mRunable = new FileRunable(remoteFile, localFile, handler);
		new Thread(mRunable).start();
	}
	
	private class FileRunable implements Runnable{

		final FileDownload this$0;
		final String localFile;
		final Handler progressHandler;
		final String remoteFile;
		
		public FileRunable(String remoteFile,String localFile,Handler progressHandler) {
			this$0 = FileDownload.this;
			this.remoteFile = remoteFile;
			this.localFile = localFile;
			this.progressHandler = progressHandler;
		}
		
		@Override
		public void run() {
			FileDownload fileDownload = FileDownload.this;
			fileDownload.download(remoteFile, localFile, progressHandler);
		}
		
	}
}
