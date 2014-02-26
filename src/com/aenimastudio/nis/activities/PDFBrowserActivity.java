package com.aenimastudio.nis.activities;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.aenimastudio.nis.R;
import com.aenimastudio.nis.content.NetworkStatus;
import com.aenimastudio.nis.content.NetworkStatusListener;

public class PDFBrowserActivity extends AbstractBrowserActivity {

	@Override
	protected void loadWebPage() {
		StringBuilder sbUrl = new StringBuilder();
		sbUrl.append(getResources().getString(R.string.web_url));
		sbUrl.append(getResources().getString(R.string.web_context));
		sbUrl.append(getResources().getString(R.string.web_app_context));
		sbUrl.append(getResources().getString(R.string.food_menu_page));
		webView.loadUrl(sbUrl.toString());
	}

	@Override
	protected void configureMenuBar() {
		ImageView imgIcon = (ImageView) findViewById(R.id.commonMenuIcon);
		imgIcon.setImageResource(R.drawable.icon_food);

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
