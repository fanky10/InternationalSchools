package com.aenimastudio.nis.activities;

import org.apache.http.util.EncodingUtils;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.aenimastudio.nis.R;
import com.aenimastudio.nis.constants.AppConstants;

public class PostLoginBrowserActivity extends BrowserActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected WebViewClient getWebViewClient() {
		return new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				Log.d(PostLoginBrowserActivity.class.getName(),"url given! "+url);
				if(url.contains("login.php")){
					Log.d(PostLoginBrowserActivity.class.getName(),"url contains login.php!");
					showLoginView();
					return false;
				}else if(isPDF(url) || isAnImage(url)){
					Log.d(PostLoginBrowserActivity.class.getName(),"img or pdf!");
					showCustomView(url);
					return false;
				}
				
				view.loadUrl(url);
				return true;
			}
		};
	}
	
	private boolean isPDF(String url){
		return url.endsWith("pdf");
	}
	private boolean isAnImage(String url){
		return url.endsWith("jpg") || url.endsWith("jpeg") || url.endsWith("png") || url.endsWith("gif");
	}
	
	private void showLoginView(){
		Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
		startActivity(intent);
	}
	
	private void showCustomView(String url){
		Bundle bundle = new Bundle();
		bundle.putString(AppConstants.WEB_URL_KEY, url);
		Intent intent = new Intent(getApplicationContext(), SimpleBrowserActivity.class);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	@Override
	protected void showWebpage() {
		String postUrl = getResources().getString(R.string.login_post_url);
		String fieldUsername = getResources().getString(R.string.login_field_username);
		String fieldPassword = getResources().getString(R.string.login_field_password);
		String fieldSubmit = getResources().getString(R.string.login_field_submit);
		
		Bundle bundle = getIntent().getExtras();
		String username = bundle.getString(AppConstants.LOGIN_USERNAME_KEY);
		String password = bundle.getString(AppConstants.LOGIN_PASSWORD_KEY);
		
		StringBuilder sbPostData = new StringBuilder();
		sbPostData.append(fieldUsername).append("=").append(username);
		sbPostData.append("&").append(fieldPassword).append("=").append(password);
		sbPostData.append("&").append(fieldSubmit);

		// this line logs you in and you stay logged in
		// I suppose it works this way because in this case WebView handles cookies itself
		webView.postUrl(postUrl, EncodingUtils.getBytes(sbPostData.toString(), "utf-8"));
	}

}
