package com.aenimastudio.nis.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.aenimastudio.nis.R;

public abstract class BaseActivity extends FragmentActivity {
	private String webAppUrl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//builds complete url 
		StringBuilder sb = new StringBuilder();
		sb.append(getResources().getString(R.string.service_url));
		sb.append(getResources().getString(R.string.service_context));
		webAppUrl = sb.toString();
	}

	protected void makePhoneCall(String phoneNumber) {
		Intent intent = new Intent(Intent.ACTION_CALL);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setData(Uri.parse("tel:" + phoneNumber));
		getApplicationContext().startActivity(intent);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

	public String getWebAppUrl() {
		return webAppUrl;
	}

	public void setWebAppUrl(String webAppUrl) {
		this.webAppUrl = webAppUrl;
	}

}
