package com.aenimastudio.nis.activities;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.aenimastudio.nis.R;
import com.aenimastudio.nis.constants.AppConstants;

public class PDFBrowserActivity extends BaseActivity {
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
		return new WebViewClient();
	}

	protected void showWebpage() {
		Bundle bundle = getIntent().getExtras();
		if (bundle == null) {
			throw new IllegalArgumentException("This Activity should be intented with a bundle object");
		}
		String pdfUrl = bundle.getString(AppConstants.SHARED_PDF_URL_KEY);
		if (pdfUrl == null || pdfUrl.isEmpty()) {
			throw new IllegalArgumentException("This Activity should be intented with a valid pdf url");
		}
		StringBuilder sbUrl = new StringBuilder();
		sbUrl.append(getResources().getString(R.string.gdocs_pdf_reader));
		sbUrl.append(pdfUrl);

		webView.loadUrl(sbUrl.toString());
	}

	protected void configureMenuBar() {
		//do nothing for now
	}

}
