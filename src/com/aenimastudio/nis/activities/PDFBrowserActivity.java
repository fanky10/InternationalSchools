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

public class PDFBrowserActivity extends AbstractBrowserActivity {
	private NetworkStatusListener networkStatusListener;

	@Override
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

	@Override
	protected void configureMenuBar() {
		ImageView imgPrev = (ImageView) findViewById(R.id.commonMenuPrev);
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
				} else {
					warningLayout.setVisibility(View.GONE);
				}
			}
		};
		addNetworkStatusListener(networkStatusListener);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		removeNetworkStatusListener(networkStatusListener);
	}

}
