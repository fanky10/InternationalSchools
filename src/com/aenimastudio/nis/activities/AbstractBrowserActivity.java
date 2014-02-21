package com.aenimastudio.nis.activities;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.aenimastudio.nis.R;

public abstract class AbstractBrowserActivity extends BaseActivity {
	protected WebView webView;
	private int webViewErrorCode = 0;
	private boolean pageFinishedLoading = false;

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
		webView.setWebViewClient(getWebViewClient());
		loadWebPage();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}

	protected void reloadPage() {
		if (webViewErrorCode > 0 || !pageFinishedLoading) {
			loadWebPage();
		}
	}

	private WebViewClient getWebViewClient() {
		return new WebViewClient() {
			@Override
			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
				webViewErrorCode = errorCode;
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				pageFinishedLoading = true;
				webViewErrorCode = 0;
			}
		};
	}

	protected abstract void loadWebPage();

	protected abstract void configureMenuBar();
}
