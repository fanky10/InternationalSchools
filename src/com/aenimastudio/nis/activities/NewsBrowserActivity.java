package com.aenimastudio.nis.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.aenimastudio.nis.R;
import com.aenimastudio.nis.constants.AppConstants;
import com.aenimastudio.nis.constants.BrowserUrlUtils;

public class NewsBrowserActivity extends AbstractBrowserActivity {
	private static final String LOG_TAG = NewsBrowserActivity.class.getName();

	@Override
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
					redirectToPDFBrowser(url);
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

	private void redirectToPDFBrowser(String url) {
		throw new UnsupportedOperationException("not supported yet");
	}

	@Override
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
		Intent intent = new Intent(getApplicationContext(), FoodMenuBrowserActivity.class);
		startActivity(intent);
	}

}
