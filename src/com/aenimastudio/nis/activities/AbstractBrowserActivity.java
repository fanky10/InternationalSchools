package com.aenimastudio.nis.activities;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.aenimastudio.nis.R;

public abstract class AbstractBrowserActivity extends BaseActivity {
	protected WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.browser_layout);
		init();
	}

	private void init() {
		webView = (WebView) findViewById(R.id.webView);
		webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setBuiltInZoomControls(true);
		webView.getSettings().setSupportZoom(true);
		webView.getSettings().setUseWideViewPort(true);
		webView.setWebViewClient(getWebViewClient());
		showWebpage();
	}

	protected abstract WebViewClient getWebViewClient();

	protected abstract void showWebpage();

	protected boolean isLoginPage(String url) {
		String loginPage = getResources().getString(R.string.login_page);
		return url.contains(loginPage);
	}

	protected boolean isLogout(String url) {
		String loginPage = getResources().getString(R.string.logout_page);
		return url.contains(loginPage);
	}

	protected void showLoginView(String errorMessage) {
		Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
		startActivity(intent);
	}

}
