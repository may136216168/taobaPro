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
	//表示当前HtmlActivity打开的链接
	private String currentUrlStr;
	
	//标题
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
		//1第一个方面，获得加载网页的路径
		Bundle bundle = this.getIntent().getExtras();
		if(bundle != null){
			currentUrlStr = bundle.getString("url");
			title = bundle.getString("title");
		}
		//2 WebView控件，这个控件用来加载网页和调用js
		webView = new WebView(this);
		initLayout();
		webView.setWebViewClient(new MyWebViewClient());
		//3 注册一些js
		
		//4 其他的一些细小的事情
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
			//设置我们的title
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
	 * 加载链接的函数。
	 * @param url
	 * @throws MalformedURLException 
	 * @throws JSONException 
	 */
	private void loadUrl(String url) throws MalformedURLException, JSONException{
		//首先去构造一个新的url
		String s1 = Config.WEB_FILE_PREFIX;
		String s2 = s1 + url;
		
		//去判断这个url是否需要从服务器上读取数据，那么这个数据是json形式
		String s3 = (new URL(s2)).getQuery();
		JSONObject jsonObj = new JSONObject();
		if(StringTool.isEmpty(s3).booleanValue() ==false){
			//解析请求参数
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
		//去正在的加载这一个页面.
		webView.loadUrl(s2);
	}
	
	/**
	 * 调用javascript
	 * @param s 参数是javascript的代码
	 */
	public void callJavascript(String s){
		//先构造一个JS代码alert('你好，android')
		//s1 = javascript:alert('你好，android')
		String s1 = "javascript:"+s;
		webView.loadUrl(s1);
	}
	
	/**
	 * 这里是调用JS的函数.
	 * @param s
	 * @param as
	 */
	public void callJSFunction(String s,String[] as){
		//先构造一个JS的字符串
		String s1 = "if(typeof "+s+"=='function')"+ s+"("+StringTool.join(Arrays.asList(as), ",")+")";
		callJavascript(s1);
	}
	
	/**
	 * 主要是使HtmlActivity中webView绑定一个JS函数
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
			//调用一个javaScript的函数，通知webView中的页面，加载工作
			this$0.callJSFunction("_page_loaded", new String[]{});
		}
	}
}
