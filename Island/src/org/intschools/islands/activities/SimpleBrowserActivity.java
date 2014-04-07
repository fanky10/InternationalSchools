package org.intschools.islands.activities;

import org.intschools.islands.R;
import org.intschools.islands.constants.AppConstants;
import org.intschools.islands.content.NetworkStatus;
import org.intschools.islands.content.NetworkStatusListener;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class SimpleBrowserActivity extends MinBrowserActivity {

	protected void init() {
		super.init();
		webView.getSettings().setUseWideViewPort(true);
		webView.getSettings().setLoadWithOverviewMode(true);
	}

	@Override
	protected void loadWebPage() {
		Intent intent = getIntent();
		String sharedUrl = null;
		if (Intent.ACTION_VIEW.equals(intent.getAction())) {
			Uri uri = intent.getData();
			String intentScheme = getResources().getString(R.string.intent_scheme);
			sharedUrl = uri.toString().substring(intentScheme.length());
		} else {
			Bundle bundle = intent.getExtras();
			if (bundle == null) {
				throw new IllegalArgumentException("This Activity should be intented with a bundle object");
			}
			sharedUrl = bundle.getString(AppConstants.SHARED_URL_KEY);
		}
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