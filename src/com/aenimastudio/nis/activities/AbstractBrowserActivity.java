package com.aenimastudio.nis.activities;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.aenimastudio.nis.R;
import com.aenimastudio.nis.constants.AppConstants;

public abstract class AbstractBrowserActivity extends BaseActivity {
	protected WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.src_browser_layout);
		init();
	}

	private void init() {
		configureMenuBar();
		webView = (WebView) findViewById(R.id.pdfWebView);
		webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setBuiltInZoomControls(true);
		webView.getSettings().setSupportZoom(true);
		webView.getSettings().setUseWideViewPort(true);
		webView.setWebViewClient(new WebViewClient());
		showWebpage();
	}
	
	protected abstract void showWebpage();

	protected void configureMenuBar() {
		//do nothing for now
	}
}
