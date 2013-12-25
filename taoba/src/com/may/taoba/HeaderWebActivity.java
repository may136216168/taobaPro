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
 * 这个类主要是在HtmlActivity的基础上加上一个返回按钮和一个标题
 * @author ZhangJian
 *
 */
public class HeaderWebActivity extends HtmlActivity {
	//第一个成员button,用来返回
	Button backButton;
	
	//有一个文本，表示View的标题
	TextView titleTextView;
	
	public HeaderWebActivity() {
	}
	
	
	//布局初始化函数，让布局更好看
	protected void initLayout(){
		//首先通过xml来设置布局
		this.setContentView(R.layout.header_web);
		//找到线性布局和按钮
		LinearLayout linearLayout = (LinearLayout)findViewById(R.id.root);
		backButton = (Button)findViewById(R.id.btn_main_title_back);
		titleTextView = (TextView)findViewById(R.id.txt_main_title);
		
		//设置监听事件等
		backButton.setOnClickListener(new OnButtonClick());
		
		WebView webView = getWebView();
		
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1,-1,-1);
		webView.setLayoutParams(layoutParams);
		
		//刚好能够显示在整个屏幕中
		webView.setInitialScale(100);
		webView.setVerticalScrollBarEnabled(false);//这个表示不显示滚动条
		webView.setOnLongClickListener(new OnLongClick2());
		
		WebSettings webSesstings = webView.getSettings();
		webSesstings.setJavaScriptEnabled(true);
		webSesstings.setJavaScriptCanOpenWindowsAutomatically(true);
		
		linearLayout.addView(webView);
	}
	
	/**
	 * 得到它的顶层窗口
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
			//返回到我们的上一个页面
		}
		
	}
	
	private class OnLongClick2 implements View.OnLongClickListener{

		@Override
		public boolean onLongClick(View v) {
			//去调用一些长按事件，需要js去配合完成一个长按事件
			return false;
		}

	}
}
