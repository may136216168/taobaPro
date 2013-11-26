package com.may.taoba.module.update;

import android.app.Application;
import android.os.Handler;
import android.os.Message;

import com.may.taoba.module.Config;
import com.may.taoba.module.TaoBaApplication;
import com.may.taoba.module.net.FileDownload;

/**
 * 1�����ͻ����Ƿ���Ҫ����
 * 2���������ļ��Ƿ���Ҫ����
 * @author ZhangJian
 *
 */
public class UpdateService {
	public UpdateService() {
	}
	
	/**
	 * ���ͻ����Ƿ���Ҫ����
	 * @param�����ǽ�������Handler
	 */
	public static void checkClientApkUpdate(Handler handler){
		//�����ж��Ƿ���Ҫ����
		if(VersionManager.checkClientUpdate()){
			TaoBaApplication application = TaoBaApplication.getInstance();
			FileDownload fileDownload = new FileDownload(application);
			//�ļ���Զ�̷������ϵĵ�ַ
			String s1 = Config.REMOTE_UPDATE_PATH +VersionManager.getRemoteVersion().getCurrentStableClientVersionInfo();
			
			//��ʾ�����ϵ�apk���ص����صĴ��λ��
			String localFilePathStr = TaoBaApplication.getInstance().getAppFilePath()+"/install_tmp.apk";
			Handler1 handler1 = new Handler1(handler, localFilePathStr);
			fileDownload.download2(s1, "install_tmp.apk", handler);
		}
		//�ӷ�����������һ��apk�ļ�
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
				message.what = 4;//��ʾ���ͽ�����ǰ������Ϣ
				message.arg1 = msg.arg1;
				
				//��������½�����.
				progressHandler.sendMessage(message);
			}
			
			if(msg.what == 2){
				ApkInstaller.install(filePathStr);
			}
		}
	}
}
