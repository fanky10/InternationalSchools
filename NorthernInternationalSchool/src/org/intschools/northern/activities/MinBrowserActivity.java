package org.intschools.northern.activities;

import org.intschools.northern.R;

import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

public abstract class MinBrowserActivity extends AbstractBrowserActivity {

	@Override
	public void setContentView(int view) {
		super.setContentView(R.layout.min_browser_layout);
	}

	protected void init() {
		configureMenuBar();
		webView = (WebView) findViewById(R.id.webView);
		webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setBuiltInZoomControls(true);
		webView.getSettings().setSupportZoom(true);
		webView.setWebViewClient(getWebViewClient());
		webView.setWebChromeClient(new WebChromeClient());
	}
}
