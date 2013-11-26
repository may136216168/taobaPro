package com.may.taoba.module.update;

import android.app.Application;
import android.os.Handler;
import android.os.Message;

import com.may.taoba.module.Config;
import com.may.taoba.module.TaoBaApplication;
import com.may.taoba.module.net.FileDownload;

/**
 * 1、检查客户端是否需要更新
 * 2、检查界面文件是否需要更新
 * @author ZhangJian
 *
 */
public class UpdateService {
	public UpdateService() {
	}
	
	/**
	 * 检查客户端是否需要更新
	 * @param参数是进度条的Handler
	 */
	public static void checkClientApkUpdate(Handler handler){
		//首先判断是否需要更新
		if(VersionManager.checkClientUpdate()){
			TaoBaApplication application = TaoBaApplication.getInstance();
			FileDownload fileDownload = new FileDownload(application);
			//文件在远程服务器上的地址
			String s1 = Config.REMOTE_UPDATE_PATH +VersionManager.getRemoteVersion().getCurrentStableClientVersionInfo();
			
			//表示将网上的apk下载到本地的存放位置
			String localFilePathStr = TaoBaApplication.getInstance().getAppFilePath()+"/install_tmp.apk";
			Handler1 handler1 = new Handler1(handler, localFilePathStr);
			fileDownload.download2(s1, "install_tmp.apk", handler);
		}
		//从服务器上下载一个apk文件
	}
	
	private static class Handler1 extends Handler{
		final String filePathStr;
		final Handler progressHandler;
		
		public Handler1(Handler handler,String filepath) {
			this.progressHandler = handler;
			this.filePathStr = filepath;
		}
		
		@Override
		public void handleMessage(Message msg) {
			if(progressHandler != null){
				Message message = Message.obtain();
				message.what = 4;//表示发送进度条前进的消息
				message.arg1 = msg.arg1;
				
				//在这里，更新进度条.
				progressHandler.sendMessage(message);
			}
			
			if(msg.what == 2){
				ApkInstaller.install(filePathStr);
			}
		}
	}
}
