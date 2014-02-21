package com.aenimastudio.nis.activities;

import android.os.Bundle;

import com.aenimastudio.nis.R;
import com.aenimastudio.nis.constants.AppConstants;

public class PDFBrowserActivity extends AbstractBrowserActivity {

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

}
