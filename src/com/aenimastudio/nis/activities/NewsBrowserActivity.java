package com.aenimastudio.nis.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.aenimastudio.nis.R;
import com.aenimastudio.nis.constants.AppConstants;
import com.aenimastudio.nis.constants.BrowserUrlUtils;

public class NewsBrowserActivity extends BaseActivity {
	private static final String LOG_TAG = NewsBrowserActivity.class.getName();
	
	protected WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news_layout);
		configureMenuBar();
		init();
	}

	private void init() {
		webView = (WebView) findViewById(R.id.newsWebView);
		webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setBuiltInZoomControls(true);
		webView.getSettings().setSupportZoom(true);
		webView.getSettings().setUseWideViewPort(true);
		webView.setWebViewClient(getWebViewClient());
		showWebpage();
	}

	protected WebViewClient getWebViewClient() {
		return new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				Log.d(LOG_TAG, "url given! " + url);
				if (BrowserUrlUtils.isAnImage(url)) {
					Log.d(LOG_TAG, "url is an image!!");
					redirectToImageBrowser(url);
					return true;
				} else if (BrowserUrlUtils.isPDF(url)) {
					showPDFView(url);
					return true;
				} else if (isLoginPage(url)) {
					showLoginView(null);
					return true;
				}
				view.loadUrl(url);
				return false;
			}
		};
	}

	private void redirectToImageBrowser(String url) {
		throw new UnsupportedOperationException("not supported yet");
	}


	protected void showWebpage() {
		Bundle bundle = getIntent().getExtras();
		if (bundle == null) {
			throw new IllegalArgumentException("This Activity should be intented with a bundle object");
		}

		Integer userId = bundle.getInt(AppConstants.SHARED_USER_ID_KEY);
		if (userId == null || userId < 1) {
			throw new IllegalArgumentException("This Activity should be intented with a valid userId");
		}
		
		String webPage = new StringBuilder().append(getWebAppUrl()).append(getResources().getString(R.string.news_page)).append("?")
				.append(getResources().getString(R.string.news_param_user_id)).append("=").append(userId).toString();
		webView.loadUrl(webPage);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}
	
	protected void configureMenuBar() {
		ImageView imgFood = (ImageView) findViewById(R.id.commonMenuFood);
		imgFood.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showFoodMenu();
			}
		});

		ImageView imgLogout = (ImageView) findViewById(R.id.commonMenuLogout);
		imgLogout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				logout();
			}

		});
	}

	private void logout() {
		Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
		startActivity(intent);
	}

	private void showFoodMenu() {
		StringBuilder sbUrl = new StringBuilder();
		sbUrl.append(getResources().getString(R.string.web_url));
		sbUrl.append(getResources().getString(R.string.web_context));
		sbUrl.append(getResources().getString(R.string.food_menu_path));
		showPDFView(sbUrl.toString());
	}

}
