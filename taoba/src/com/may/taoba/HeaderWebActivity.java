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
 * �������Ҫ����HtmlActivity�Ļ����ϼ���һ�����ذ�ť��һ������
 * @author ZhangJian
 *
 */
public class HeaderWebActivity extends HtmlActivity {
	//��һ����Աbutton,��������
	Button backButton;
	
	//��һ���ı�����ʾView�ı���
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
		boolean flag;
		//��Ҫ�Ǵ����Ƿ���Ҫ�˳��������ļ��̴����¼�
		if(keyCode == event.KEYCODE_BACK && event.getRepeatCount() ==0){
			//��������һ��������ʾ��ҳ�棬��ô�����˳�����
			//����������һ��ҳ�棬��ô������ת����һ��ҳ����ȥ.
			if(getStack().backToStackBottom()){
				Activity activity = getParent();
				//�˳�֮ǰ��Ҫһ������
				AlertDialog.Builder builder = new AlertDialog.Builder(activity).setTitle("�˳�����")
				.setMessage("�Ƿ��˳��԰ɳ���");
				
				builder.setPositiveButton("ȷ��", new OnClickOkListener());
				
				builder.setNegativeButton("ȡ��", new OnClickCancelListener());
			}
			flag = true;
		}else{
			flag = super.onKeyDown(keyCode, event);
		}
		
		return flag;
	}
	
	/**
	 * ����һ��js����webview�е�ҳ��ִ��js�Ĵ���
	 */
	private void triggerJs(int i,String s){
		if(StringTool.isEmpty(s)){
			s = "null";
		}
		switch (i) {
		case 1:
			//��ʾҳ�����һ��ҳ�淵��
			String as[] = new String[1];
			as[0] = s;
			this.callJSFunction("_on_page_back", as);
			break;
		case 2:
			//��ʾҳ���ж�֮�����½�����߻ָ�
			String as2[] = new String[1];
			as2[0] = s;
			this.callJSFunction("_on_page_resume", as2);
			break;
		}
	}
	
	/**
	 * ���ñ���
	 * @param title �����ַ���
	 */
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
	
	/**
	 * ���ȷ����ť�ͻ��˳�����
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
