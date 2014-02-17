package com.aenimastudio.nis.activities;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.aenimastudio.nis.R;
import com.aenimastudio.nis.constants.AppConstants;
import com.aenimastudio.nis.constants.BrowserUrlUtils;

public class BrowserImagePDFFilterActivity extends AbstractBrowserActivity {
	private static final String LOG_TAG = BrowserImagePDFFilterActivity.class.getName();

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
				}else if(BrowserUrlUtils.isPDF(url)){
					redirectToPDFBrowser(url);
					return true;
				}else if(isLoginPage(url)){
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
		String webPage = getResources().getString(R.string.login_get_url);
		if (bundle != null) {
			webPage = (String) bundle.get(AppConstants.WEB_URL_KEY);
		}
		webView.loadUrl(webPage);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

}
