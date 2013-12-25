package com.may.taoba.module.mybase;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.may.taoba.module.Config;
import com.may.taoba.tools.StringTool;

public abstract class HtmlActivity extends MyActivity {
	protected WebView webView;
	//��ʾ��ǰHtmlActivity�򿪵�����
	private String currentUrlStr;
	
	//����
	private String title;
	private String queryJSon;
	
	public HtmlActivity() {
	}
	
	public WebView getWebView() {
		return webView;
	}
	
	protected abstract void initLayout();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//1��һ�����棬��ü�����ҳ��·��
		Bundle bundle = this.getIntent().getExtras();
		if(bundle != null){
			currentUrlStr = bundle.getString("url");
			title = bundle.getString("title");
		}
		//2 WebView�ؼ�������ؼ�����������ҳ�͵���js
		webView = new WebView(this);
		initLayout();
		webView.setWebViewClient(new MyWebViewClient());
		//3 ע��һЩjs
		
		//4 ������һЩϸС������
		if(StringTool.isEmpty(currentUrlStr).booleanValue() == false){
			try {
				loadUrl(currentUrlStr);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		if(StringTool.isEmpty(title).booleanValue() == false){
			//�������ǵ�title
			setTitle(title);
		}
	}
	
	public String getCurrentUrlStr() {
		return currentUrlStr;
	}

	public void setCurrentUrlStr(String currentUrlStr) {
		this.currentUrlStr = currentUrlStr;
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		String[] as = new String[1];
		as[0] = "false";
		callJSFunction("_page_appeared", as);
	}

	/**
	 * �������ӵĺ�����
	 * @param url
	 * @throws MalformedURLException 
	 * @throws JSONException 
	 */
	private void loadUrl(String url) throws MalformedURLException, JSONException{
		//����ȥ����һ���µ�url
		String s1 = Config.WEB_FILE_PREFIX;
		String s2 = s1 + url;
		
		//ȥ�ж����url�Ƿ���Ҫ�ӷ������϶�ȡ���ݣ���ô���������json��ʽ
		String s3 = (new URL(s2)).getQuery();
		JSONObject jsonObj = new JSONObject();
		if(StringTool.isEmpty(s3).booleanValue() ==false){
			//�����������
			String[] as = Pattern.compile("&").split(s3);
			int length = as.length;
			for (int i = 0; i < length; i++) {
				String s4 = as[i];
				int k = s4.indexOf("=");
				int l = s4.indexOf("#");
				if(l != -1){
					int i1 = l + 1;
					String s5 = s4.substring(i1);
					jsonObj.put("__anchor__", s5);
				}
				
				if(k != -1){
					String s6 = s4.substring(0, k);
					String s7;
					if(l != -1){
						int j1 = k+1;
						s7 = s4.substring(j1);
					}else{
						int k1 = k +1;
						s7 = s4.substring(k1,0);
					}
					if (!StringTool.isEmpty(s6).booleanValue()
							&& !StringTool.isEmpty(s7).booleanValue()) {
						String s8 = Uri.decode(s7);
						jsonObj.put(s6, s8);
					}
				}
			}
		}
		queryJSon = jsonObj.toString();
		//ȥ���ڵļ�����һ��ҳ��.
		webView.loadUrl(s2);
	}
	
	/**
	 * ����javascript
	 * @param s ������javascript�Ĵ���
	 */
	public void callJavascript(String s){
		//�ȹ���һ��JS����alert('��ã�android')
		//s1 = javascript:alert('��ã�android')
		String s1 = "javascript:"+s;
		webView.loadUrl(s1);
	}
	
	/**
	 * �����ǵ���JS�ĺ���.
	 * @param s
	 * @param as
	 */
	public void callJSFunction(String s,String[] as){
		//�ȹ���һ��JS���ַ���
		String s1 = "if(typeof "+s+"=='function')"+ s+"("+StringTool.join(Arrays.asList(as), ",")+")";
		callJavascript(s1);
	}
	
	/**
	 * ��Ҫ��ʹHtmlActivity��webView��һ��JS����
	 */
	private void bindJSFunction(){
		
	}
	
	
	final class MyWebViewClient extends WebViewClient{
		
		private HtmlActivity this$0;
		
		public MyWebViewClient() {
			super();
			this$0 = HtmlActivity.this;
		}
		
		@Override
		public void onPageFinished(WebView view, String url) {
//			super.onPageFinished(view, url);
			//����һ��javaScript�ĺ�����֪ͨwebView�е�ҳ�棬���ع���
			this$0.callJSFunction("_page_loaded", new String[]{});
		}
	}
}
