package com.may.taoba;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.may.taoba.module.update.UpdateService;
import com.may.taoba.module.update.VersionManager;

public class LaunchMainActivity extends Activity {

	private ImageView imageView;
	private TextView info;
	private TextView rate;
	private ProgressBar progressBar;
	private Handler progressHandler;
	public LaunchMainActivity() {
		progressHandler = new ProgressHandler();
	}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //�����Զ��������
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.launch_main);
        
        //�����Զ���ı���
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title);
        
        //������Ϣ�ı��������
        info = (TextView)findViewById(R.id.tips);
        info.setText("��Ba");
        
        progressBar = (ProgressBar)findViewById(R.id.progress_bar);
        rate = (TextView)findViewById(R.id.rate);
        imageView = (ImageView)findViewById(R.id.download);
        
        //���ͻ��˵İ汾�Ƿ���Ҫ����
//        if(VersionManager.checkClientUpdate()){
        if(true){
        	//����и��£���ֱ�Ӹ���
        	//��ʾ�û��Ƿ���Ҫ����
        	Builder builder = new Builder(this);
        	String s = getResources().getString(R.string.have_new_apk_title);
        	OnOkListener okListener = new OnOkListener();
        	OnCencelListener cencelListener = new OnCencelListener();
        	Builder builder1 = builder.setTitle(s).setMessage(R.string.new_apk_msg)
        	.setPositiveButton(R.string.dlg_ok, okListener);
        	Builder builder2 = builder1.setNegativeButton(R.string.dlg_cencel, cencelListener);
        	builder2.create().show();
        }else{
        	//���û�и��£���ȥ������������
        	doOtherJobs();
        }
    }
    
    class OnOkListener implements android.content.DialogInterface.OnClickListener{

		@Override
		public void onClick(DialogInterface dialog, int which) {
			
			//���غ͸��²���
			UpdateService.checkClientApkUpdate(progressHandler);
			
		}
    }
    
    class OnCencelListener implements android.content.DialogInterface.OnClickListener{

		@Override
		public void onClick(DialogInterface dialog, int which) {
			//ȡ�������صĲ���
			doOtherJobs();
		}
    	
    }

    /**
     * ��Ҫ�Ĺ�����ִ��һ�����������̣߳����߳�������һЩ��ʼ���Ĺ�����Ȼ����ת��Ŀ¼ҳ��.
     */
    private void doOtherJobs() {
    	startMainActivity();
	}

    /**
     * ��ת����Ŀ¼
     */
    private void startMainActivity(){
    	Intent intent = new Intent(this, MainActivity.class);
    	startActivity(intent);
    	finish();
    }
	private boolean checkClientUpdate() {
		return false;
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.launch_main, menu);
        return true;
    }
	
	private class ProgressHandler extends Handler{
		
		final LaunchMainActivity this$0;
		public ProgressHandler() {
			super();
			this$0 = LaunchMainActivity.this;
		}
		
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				//��ʾ���ڼ���Ƿ������°汾
				info.setText("���ڼ��汾...");
				break;
			case 5:
				//��ʾӦ�ó������ڸ���
				progressBar.setVisibility(View.VISIBLE);
				info.setText("���ڸ���...");
				int step = msg.arg1;
				progressBar.setProgress(step);
				
				rate.setText(step+"%");
				if(step == 100){
					Toast.makeText(this$0, "�������", Toast.LENGTH_SHORT).show();
				}
				break;
			}
		}
	}
}
