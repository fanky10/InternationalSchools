package com.aenimastudio.nis.activities;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.aenimastudio.nis.R;
import com.aenimastudio.nis.content.NetworkStatusListener;

public abstract class AbstractBrowserActivity extends BaseActivity {
	protected WebView webView;
	private int webViewErrorCode = 0;
	private boolean pageFinishedLoading = false;
	protected NetworkStatusListener networkStatusListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.browser_layout);
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
		webView.setWebChromeClient(new WebChromeClient());
		loadWebPage();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if(networkStatusListener!=null){
			removeNetworkStatusListener(networkStatusListener);
		}
	}

	protected void reloadPage() {
		if (webViewErrorCode > 0 || !pageFinishedLoading) {
			loadWebPage();
		}
	}

	protected WebViewClient getWebViewClient() {
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

	public WebView getWebView() {
		return webView;
	}

	public void setWebView(WebView webView) {
		this.webView = webView;
	}

	public int getWebViewErrorCode() {
		return webViewErrorCode;
	}

	public void setWebViewErrorCode(int webViewErrorCode) {
		this.webViewErrorCode = webViewErrorCode;
	}

	public boolean isPageFinishedLoading() {
		return pageFinishedLoading;
	}

	public void setPageFinishedLoading(boolean pageFinishedLoading) {
		this.pageFinishedLoading = pageFinishedLoading;
	}

	public NetworkStatusListener getNetworkStatusListener() {
		return networkStatusListener;
	}

	public void setNetworkStatusListener(NetworkStatusListener networkStatusListener) {
		this.networkStatusListener = networkStatusListener;
	}
}
