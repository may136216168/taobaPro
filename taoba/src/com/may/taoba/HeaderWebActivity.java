package com.may.taoba;

import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.may.taoba.module.mybase.HtmlActivity;

/**
 * �������Ҫ����HtmlActivity�Ļ����ϼ���һ�����ذ�ť��һ������
 * @author ZhangJian
 *
 */
public class HeaderWebActivity extends HtmlActivity {
	//��һ����Աbutton,��������
	Button backButton;
	
	//��һ���ı�����ʾView�ı���
	TextView titleTextView;
	
	public HeaderWebActivity() {
	}
	
	
	//���ֳ�ʼ���������ò��ָ��ÿ�
	protected void initLayout(){
		//����ͨ��xml�����ò���
		this.setContentView(R.layout.header_web);
		//�ҵ����Բ��ֺͰ�ť
		LinearLayout linearLayout = (LinearLayout)findViewById(R.id.root);
		backButton = (Button)findViewById(R.id.btn_main_title_back);
		titleTextView = (TextView)findViewById(R.id.txt_main_title);
		
		//���ü����¼���
		backButton.setOnClickListener(new OnButtonClick());
		
		WebView webView = getWebView();
		
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1,-1,-1);
		webView.setLayoutParams(layoutParams);
		
		//�պ��ܹ���ʾ��������Ļ��
		webView.setInitialScale(100);
		webView.setVerticalScrollBarEnabled(false);//�����ʾ����ʾ������
		webView.setOnLongClickListener(new OnLongClick2());
		
		WebSettings webSesstings = webView.getSettings();
		webSesstings.setJavaScriptEnabled(true);
		webSesstings.setJavaScriptCanOpenWindowsAutomatically(true);
		
		linearLayout.addView(webView);
	}
	
	/**
	 * �õ����Ķ��㴰��
	 * @return
	 */
	public ActivityContainer getStack(){
		return (ActivityContainer)this.getParent();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		
		return super.onKeyDown(keyCode, event);
	}
	
	public void setWebTitle(String title){
		this.titleTextView.setText(title);
	}
	
	private class OnButtonClick implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			//���ص����ǵ���һ��ҳ��
		}
		
	}
	
	private class OnLongClick2 implements View.OnLongClickListener{

		@Override
		public boolean onLongClick(View v) {
			//ȥ����һЩ�����¼�����Ҫjsȥ������һ�������¼�
			return false;
		}

	}
}
