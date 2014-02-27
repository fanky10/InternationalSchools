package com.aenimastudio.nis.activities;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.aenimastudio.nis.R;
import com.aenimastudio.nis.constants.AppConstants;
import com.aenimastudio.nis.content.NetworkStatus;
import com.aenimastudio.nis.content.NetworkStatusListener;

public class SimpleBrowserActivity extends AbstractBrowserActivity {

	@Override
	protected void loadWebPage() {
		Bundle bundle = getIntent().getExtras();
		if (bundle == null) {
			throw new IllegalArgumentException("This Activity should be intented with a bundle object");
		}
		String imgUrl = bundle.getString(AppConstants.SHARED_URL_KEY);
		if (imgUrl == null || imgUrl.isEmpty()) {
			throw new IllegalArgumentException("This Activity should be intented with a valid image url");
		}
		webView.loadUrl(imgUrl);
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