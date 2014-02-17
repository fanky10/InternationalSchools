package com.aenimastudio.nis.activities;

import org.apache.http.util.EncodingUtils;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.aenimastudio.nis.R;
import com.aenimastudio.nis.constants.AppConstants;

/**
 * 
 * @author fanky10
 * filters if the current user should be redirected to: 
 * login activity with corresponding error message, 
 * or home depending on redirection from server. 
 *
 */
public class BrowserLoginFilterActivity extends AbstractBrowserActivity {
	private static final String LOG_TAG = BrowserLoginFilterActivity.class.getName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected WebViewClient getWebViewClient() {
		return new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				if (isLoginPage(url)) {
					errorLoginView();
				}
				useSimpleBrowser(url);
				return true;
			}

			@Override
			public void onPageStarted(WebView webView, String url, Bitmap favicon) {
				//check what is going on
			}

			@Override
			public void onPageFinished(WebView webView, String url) {

			}

		};
	}

	private void errorLoginView() {
		String loginErrorMessage = getResources().getString(R.string.login_failed_message);
		showLoginView(loginErrorMessage);
	}

	private void useSimpleBrowser(String url) {
		Bundle bundle = new Bundle();
		bundle.putString(AppConstants.WEB_URL_KEY, url);
		Intent intent = new Intent(getApplicationContext(), BrowserImagePDFFilterActivity.class);
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
