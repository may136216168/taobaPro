package com.may.taoba;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.may.taoba.module.mybase.HtmlActivity;
import com.may.taoba.tools.StringTool;

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
	
	private String id;
	
	public HeaderWebActivity() {
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getId()
	{
		return id;
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
		boolean flag;
		//主要是处理是否需要退出程序程序的键盘处理事件
		if(keyCode == event.KEYCODE_BACK && event.getRepeatCount() ==0){
			//如果是最后一个可以显示的页面，那么我们退出程序
			//如果不是最后一个页面，那么我们跳转到上一个页面中去.
			if(getStack().backToStackBottom()){
				Activity activity = getParent();
				//退出之前需要一个警告
				AlertDialog.Builder builder = new AlertDialog.Builder(activity).setTitle("退出程序")
				.setMessage("是否退出淘吧程序");
				
				builder.setPositiveButton("确定", new OnClickOkListener());
				
				builder.setNegativeButton("取消", new OnClickCancelListener());
			}
			flag = true;
		}else{
			flag = super.onKeyDown(keyCode, event);
		}
		
		return flag;
	}
	
	/**
	 * 触发一个js，让webview中的页面执行js的代码
	 */
	private void triggerJs(int i,String s){
		if(StringTool.isEmpty(s)){
			s = "null";
		}
		switch (i) {
		case 1:
			//表示页面从另一个页面返回
			String as[] = new String[1];
			as[0] = s;
			this.callJSFunction("_on_page_back", as);
			break;
		case 2:
			//表示页面中断之后，重新进入或者恢复
			String as2[] = new String[1];
			as2[0] = s;
			this.callJSFunction("_on_page_resume", as2);
			break;
		}
	}
	
	/**
	 * 设置标题
	 * @param title 标题字符串
	 */
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
	
	/**
	 * 点击确定按钮就会退出程序
	 * @author ZhangJian
	 *
	 */
	private class OnClickOkListener implements DialogInterface.OnClickListener{

		@Override
		public void onClick(DialogInterface dialog, int which) {
			finish();
		}
		
	}
	
	private class OnClickCancelListener implements DialogInterface.OnClickListener{

		@Override
		public void onClick(DialogInterface dialog, int which) {
			//do nothing
		}
		
	}
}
