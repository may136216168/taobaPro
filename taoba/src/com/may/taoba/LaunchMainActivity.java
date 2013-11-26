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
        //设置自定义标题栏
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.launch_main);
        
        //设置自定义的标题
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title);
        
        //设置信息文本框的文字
        info = (TextView)findViewById(R.id.tips);
        info.setText("淘Ba");
        
        progressBar = (ProgressBar)findViewById(R.id.progress_bar);
        rate = (TextView)findViewById(R.id.rate);
        imageView = (ImageView)findViewById(R.id.download);
        
        //检查客户端的版本是否需要更新
//        if(VersionManager.checkClientUpdate()){
        if(true){
        	//如果有更新，就直接更新
        	//提示用户是否需要更新
        	Builder builder = new Builder(this);
        	String s = getResources().getString(R.string.have_new_apk_title);
        	OnOkListener okListener = new OnOkListener();
        	OnCencelListener cencelListener = new OnCencelListener();
        	Builder builder1 = builder.setTitle(s).setMessage(R.string.new_apk_msg)
        	.setPositiveButton(R.string.dlg_ok, okListener);
        	Builder builder2 = builder1.setNegativeButton(R.string.dlg_cencel, cencelListener);
        	builder2.create().show();
        }else{
        	//如果没有更新，就去做其它工作！
        	doOtherJobs();
        }
    }
    
    class OnOkListener implements android.content.DialogInterface.OnClickListener{

		@Override
		public void onClick(DialogInterface dialog, int which) {
			
			//下载和更新操作
			UpdateService.checkClientApkUpdate(progressHandler);
			
		}
    }
    
    class OnCencelListener implements android.content.DialogInterface.OnClickListener{

		@Override
		public void onClick(DialogInterface dialog, int which) {
			//取消不下载的操作
			doOtherJobs();
		}
    	
    }

    /**
     * 主要的工作是执行一个轻量级的线程，在线程里面做一些初始化的工作，然后跳转到目录页面.
     */
    private void doOtherJobs() {
    	startMainActivity();
	}

    /**
     * 跳转了主目录
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
				//表示正在检测是否是最新版本
				info.setText("正在检查版本...");
				break;
			case 5:
				//表示应用程序正在更新
				progressBar.setVisibility(View.VISIBLE);
				info.setText("正在更新...");
				int step = msg.arg1;
				progressBar.setProgress(step);
				
				rate.setText(step+"%");
				if(step == 100){
					Toast.makeText(this$0, "下载完成", Toast.LENGTH_SHORT).show();
				}
				break;
			}
		}
	}
}
