package com.aenimastudio.sis.activities;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.aenimastudio.sis.R;
import com.aenimastudio.sis.constants.AppConstants;
import com.aenimastudio.sis.content.NetworkStatus;
import com.aenimastudio.sis.content.NetworkStatusListener;

public class SimpleBrowserActivity extends MinBrowserActivity {

	protected void init() {
		super.init();
		webView.getSettings().setUseWideViewPort(true);
		webView.getSettings().setLoadWithOverviewMode(true);
	}

	@Override
	protected void loadWebPage() {
		Bundle bundle = getIntent().getExtras();
		if (bundle == null) {
			throw new IllegalArgumentException("This Activity should be intented with a bundle object");
		}
		String sharedUrl = bundle.getString(AppConstants.SHARED_URL_KEY);
		if (sharedUrl == null || sharedUrl.isEmpty()) {
			throw new IllegalArgumentException("This Activity should be intented with a valid url");
		}
		if (sharedUrl.endsWith(".png") || sharedUrl.endsWith(".jpg") || sharedUrl.endsWith(".jpeg")) {
			String html = "<body ><img style='width:100%;' id=\"resizeImage\" src=\"${picture}\" alt=\"an image\" /></body>";
			html = html.replace("${picture}", sharedUrl);
			webView.loadData(html, "text/html; charset=UTF-8", null);
			return;
		}
		webView.loadUrl(sharedUrl);
	}

	@Override
	protected void configureMenuBar() {

		ImageView imgIcon = (ImageView) findViewById(R.id.commonMenuIcon);
		imgIcon.setImageResource(R.drawable.icon_ins_menu);

		ImageView logoutIcon = (ImageView) findViewById(R.id.commonMenuRightButton);
		logoutIcon.setVisibility(View.GONE);

		ImageView imgPrev = (ImageView) findViewById(R.id.commonMenuLeftButton);
		imgPrev.setImageResource(R.drawable.btn_ins_prev);
		imgPrev.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});

		final LinearLayout warningLayout = (LinearLayout) findViewById(R.id.commonMenuTopWarning);
		networkStatusListener = new NetworkStatusListener() {
			@Override
			public void connectionChecked(NetworkStatus status) {
				if (status == NetworkStatus.OFFLINE) {
					warningLayout.setVisibility(View.VISIBLE);
				} else if (status == NetworkStatus.ONLINE) {
					warningLayout.setVisibility(View.GONE);
					loadWebPage();
				}
			}
		};
		addNetworkStatusListener(networkStatusListener);

	}
}