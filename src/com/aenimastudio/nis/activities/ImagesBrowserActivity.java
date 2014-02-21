package com.aenimastudio.nis.activities;

import android.os.Bundle;

import com.aenimastudio.nis.constants.AppConstants;


public class ImagesBrowserActivity extends AbstractBrowserActivity {

	@Override
	protected void loadWebPage() {
		Bundle bundle = getIntent().getExtras();
		if (bundle == null) {
			throw new IllegalArgumentException("This Activity should be intented with a bundle object");
		}
		String imgUrl = bundle.getString(AppConstants.SHARED_IMAGE_URL_KEY);
		if (imgUrl == null || imgUrl.isEmpty()) {
			throw new IllegalArgumentException("This Activity should be intented with a valid image url");
		}
		webView.loadUrl(imgUrl);
	}

	@Override
	protected void configureMenuBar() {
		// no menu bar
		
	}
}