package com.aenimastudio.nis.activities;

import android.webkit.WebViewClient;

import com.aenimastudio.nis.R;

public class FoodMenuBrowserActivity extends AbstractBrowserActivity {

	@Override
	protected WebViewClient getWebViewClient() {
		return new WebViewClient();
	}

	@Override
	protected void showWebpage() {
		StringBuilder sbUrl = new StringBuilder();
		sbUrl.append(getResources().getString(R.string.gdocs_pdf_reader));
		sbUrl.append(getResources().getString(R.string.web_url));
		sbUrl.append(getResources().getString(R.string.web_context));
		sbUrl.append(getResources().getString(R.string.food_menu_path));

		webView.loadUrl(sbUrl.toString());
	}

	@Override
	protected void configureMenuBar() {
		//do nothing for now
	}

}
