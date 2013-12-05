package com.may.taoba;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.may.taoba.module.mybase.HtmlActivity;

public class HeaderWebActivity extends HtmlActivity {
	//��һ����Աbutton,��������
	Button backButton;
	
	//��һ���ı�����ʾView�ı���
	TextView titleTextView;
	
	public HeaderWebActivity() {
	}
	
	
	//���ֳ�ʼ���������ò��ָ��ÿ�
	private void initLayout(){
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
		webView.setOnLongClickListener(new OnLongClick2());
		
		WebSettings webSesstings = webView.getSettings();
		webSesstings.setJavaScriptEnabled(true);
		webSesstings.setJavaScriptCanOpenWindowsAutomatically(true);
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
