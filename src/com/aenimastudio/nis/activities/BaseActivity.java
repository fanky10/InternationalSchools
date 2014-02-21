package com.aenimastudio.nis.activities;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.aenimastudio.nis.R;
import com.aenimastudio.nis.constants.AppConstants;
import com.aenimastudio.nis.content.NetworkReceiver;
import com.aenimastudio.nis.handlers.NetworkStatusListener;

public abstract class BaseActivity extends FragmentActivity {
	private String webAppUrl;
	// The BroadcastReceiver that tracks network connectivity changes.
	private NetworkReceiver receiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//builds complete url 
		StringBuilder sb = new StringBuilder();
		sb.append(getResources().getString(R.string.web_url));
		sb.append(getResources().getString(R.string.web_context));
		sb.append(getResources().getString(R.string.web_app_context));
		webAppUrl = sb.toString();

		// Registers BroadcastReceiver to track network connection changes.
		IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
		receiver = new NetworkReceiver();
		this.registerReceiver(receiver, filter);
	}
	
	protected void addNetworkStatusListener(NetworkStatusListener networkStatusListener) {
		receiver.addNetworkStatusListener(networkStatusListener);
	}
	protected void removeNetworkStatusListener(NetworkStatusListener networkStatusListener) {
		receiver.removeNetworkStatusListener(networkStatusListener);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// Unregisters BroadcastReceiver when app is destroyed.
		if (receiver != null) {
			this.unregisterReceiver(receiver);
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

	protected void makePhoneCall(String phoneNumber) {
		Intent intent = new Intent(Intent.ACTION_CALL);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setData(Uri.parse("tel:" + phoneNumber));
		getApplicationContext().startActivity(intent);
	}

	protected boolean isLoginPage(String url) {
		String loginPage = getResources().getString(R.string.login_page);
		return url.contains(loginPage);
	}

	protected boolean isLogout(String url) {
		String loginPage = getResources().getString(R.string.logout_page);
		return url.contains(loginPage);
	}

	protected void showLoginView(String errorMessage) {
		Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
		startActivity(intent);
	}

	protected void showPDFView(String url) {
		Bundle bundle = new Bundle();
		bundle.putString(AppConstants.SHARED_PDF_URL_KEY, url);
		Intent intent = new Intent(getApplicationContext(), PDFBrowserActivity.class);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	public String getWebAppUrl() {
		return webAppUrl;
	}

	public void setWebAppUrl(String webAppUrl) {
		this.webAppUrl = webAppUrl;
	}

}
