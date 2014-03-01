package com.aenimastudio.nis.activities;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.aenimastudio.nis.R;
import com.aenimastudio.nis.content.NetworkStatusListener;
import com.aenimastudio.nis.widget.VideoEnabledWebChromeClient;

public abstract class AbstractBrowserActivity extends BaseActivity {
	protected WebView webView;
	private int webViewErrorCode = 0;
	private boolean pageFinishedLoading = false;
	protected NetworkStatusListener networkStatusListener;
	protected VideoEnabledWebChromeClient webChromeClient;

	@Override
	protected final void onCreate(Bundle savedInstanceState) {
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
				if (fullscreen) {
					WindowManager.LayoutParams attrs = getWindow().getAttributes();
					attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
					attrs.flags |= WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
					getWindow().setAttributes(attrs);
					if (android.os.Build.VERSION.SDK_INT >= 14) {
						getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
					}
				} else {
					WindowManager.LayoutParams attrs = getWindow().getAttributes();
					attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
					attrs.flags &= ~WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
					getWindow().setAttributes(attrs);
					if (android.os.Build.VERSION.SDK_INT >= 14) {
						getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
					}
				}

			}
		});
		webView.setWebChromeClient(webChromeClient);
	}

	@Override
	public void onBackPressed() {

		// Notify the VideoEnabledWebChromeClient, and handle it ourselves if it doesn't handle it
		if (!webChromeClient.onBackPressed()) {
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
