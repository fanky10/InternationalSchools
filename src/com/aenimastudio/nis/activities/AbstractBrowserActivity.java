package com.aenimastudio.nis.activities;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ScrollView;

import com.aenimastudio.nis.R;
import com.aenimastudio.nis.content.NetworkStatusListener;
import com.aenimastudio.nis.widget.VideoEnabledWebChromeClient;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

public abstract class AbstractBrowserActivity extends BaseActivity {
	protected WebView webView;
	private int webViewErrorCode = 0;
	private boolean pageFinishedLoading = false;
	protected NetworkStatusListener networkStatusListener;
	protected VideoEnabledWebChromeClient webChromeClient;
	protected PullToRefreshScrollView pullToRefreshView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.browser_layout);
		init();
		loadWebPage();
	}

	protected void onResume() {
		super.onResume();
		webView.onResume();
	}

	protected void init() {
		configureMenuBar();
		webView = (WebView) findViewById(R.id.webView);
		webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setBuiltInZoomControls(true);
		webView.getSettings().setSupportZoom(true);
		webView.setWebViewClient(getWebViewClient());
		// Initialize the VideoEnabledWebChromeClient and set event handlers
		View nonVideoLayout = findViewById(R.id.nonVideoLayout); // Your own view, read class comments
		ViewGroup videoLayout = (ViewGroup) findViewById(R.id.videoLayout); // Your own view, read class comments
		View loadingView = null;//getLayoutInflater().inflate(R.layout.view_loading_video, null); // Your own view, read class comments
		webChromeClient = new VideoEnabledWebChromeClient(nonVideoLayout, videoLayout, loadingView, webView);
		webChromeClient.setOnToggledFullscreen(new VideoEnabledWebChromeClient.ToggledFullscreenCallback() {
			@Override
			public void toggledFullscreen(boolean fullscreen) {
				// Your code to handle the full-screen change, for example showing and hiding the title bar. Example:
				// I wont change a thing (:

			}
		});
		webView.setWebChromeClient(webChromeClient);

		// Set a listener to be invoked when the list should be refreshed.
		pullToRefreshView = (PullToRefreshScrollView) findViewById(R.id.ptrScrollView);
		pullToRefreshView.setOnRefreshListener(new OnRefreshListener<ScrollView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
				// Do work to refresh the list here.
				//				new ReloadWebViewTask().execute();
				loadWebPage();
			}
		});
	}

	@Override
	public void onBackPressed() {

		// Notify the VideoEnabledWebChromeClient, and handle it ourselves if it doesn't handle it
		if (webChromeClient == null || !webChromeClient.onBackPressed()) {
			webView.destroy();
			finish();
			super.onBackPressed();
		}

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		webView.destroy();
		if (networkStatusListener != null) {
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
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				return overrideUrlLoading(view, url);
			}

			@Override
			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
				webViewErrorCode = errorCode;
				showErrorPage(view);
				refreshComplete();
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				pageFinishedLoading = true;
				webViewErrorCode = 0;
				refreshComplete();
			}
		};
	}

	private void showErrorPage(WebView view) {
		String webViewErrorPageHtml = getResources().getString(R.string.webview_error_page);
		view.stopLoading();
		view.loadData(webViewErrorPageHtml, "text/html; charset=UTF-8", null);
	}

	private void refreshComplete() {
		if (pullToRefreshView != null) {
			pullToRefreshView.onRefreshComplete();
		}
	}

	protected abstract void loadWebPage();

	protected abstract void configureMenuBar();

	protected boolean overrideUrlLoading(WebView view, String url) {
		return false;
	}

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
