package com.aenimastudio.nis.activities;

import org.apache.http.util.EncodingUtils;

import android.os.Bundle;
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
				view.loadUrl(url);
				return true;
			}
		};
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
