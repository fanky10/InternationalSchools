package com.aenimastudio.nis.activities;

import com.aenimastudio.nis.R;
import com.aenimastudio.nis.constants.AppConstants;

import android.os.Bundle;
import android.webkit.WebViewClient;

public class SimpleBrowserActivity extends BrowserActivity{

	@Override
	protected WebViewClient getWebViewClient() {
		return new WebViewClient();
	}

	@Override
	protected void showWebpage() {
		Bundle bundle = getIntent().getExtras();
		String webPage = getResources().getString(R.string.login_get_url);
		if(bundle!=null){
			webPage = (String) bundle.get(AppConstants.WEB_URL_KEY);	
		}
		webView.loadUrl(webPage);
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		//redirect to home...?
	}
	

}
