package com.aenimastudio.nis.activities;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.aenimastudio.nis.R;
import com.aenimastudio.nis.constants.AppConstants;
import com.aenimastudio.nis.content.NetworkReceiver;
import com.aenimastudio.nis.content.NetworkStatusListener;
import com.aenimastudio.nis.utils.AndroidServicesUtil;

public abstract class BaseActivity extends FragmentActivity {
	private String webAppUrl;
	// The BroadcastReceiver that tracks network connectivity changes.
	private NetworkReceiver receiver;
	protected SharedPreferences appSettings;

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

		//preferences configuration
		appSettings = getSharedPreferences(AppConstants.SHARED_SETTINGS_NAME, Context.MODE_PRIVATE);
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

	protected void showLoginView(String errorMessage) {
		Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
		startActivity(intent);
	}

	protected void showPDFView(String url) {
		try {
			Uri path = Uri.parse(url);
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setData(path);
			startActivity(intent);
		} catch (ActivityNotFoundException ex) {
			final String adobeReaderApp = getResources().getString(R.string.pdf_default_app);
			AndroidServicesUtil.getAlertDialogBuilder(this).setIcon(android.R.drawable.ic_dialog_alert)
					.setTitle(R.string.pdf_alert_title).setMessage(R.string.pdf_alert_msg)
					.setPositiveButton(R.string.dialog_accept, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							showMarket(adobeReaderApp);
						}

					}).setNegativeButton(R.string.dialog_cancel, null).show();

		}
	}

	protected void showMarket(String app) {
		String marketUri = "market://details?id=" + app;
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse(marketUri));
		startActivity(intent);
	}

	protected Intent getLoginSuccessIntent(Integer userId) {
		Bundle bundle = new Bundle();
		bundle.putInt(AppConstants.SHARED_USER_ID_KEY, userId);
		Intent intent = new Intent(getApplicationContext(), NewsBrowserActivity.class);
		intent.putExtras(bundle);
		return intent;
	}

	protected Intent getLogoutIntent() {
		//save data and show news!
		SharedPreferences.Editor editor = appSettings.edit();
		editor.remove(AppConstants.SHARED_SETTINGS_NAME);
		editor.remove(AppConstants.SHARED_SETTINGS_PASSWORD);
		editor.remove(AppConstants.SHARED_SETTINGS_USER_ID);
		editor.remove(AppConstants.SHARED_SETTINGS_MOD_TIME);

		// Commit the edits!
		editor.commit();

		return new Intent(getApplicationContext(), LoginActivity.class);
	}

	public String getWebAppUrl() {
		return webAppUrl;
	}

	public void setWebAppUrl(String webAppUrl) {
		this.webAppUrl = webAppUrl;
	}

	public SharedPreferences getAppSettings() {
		return appSettings;
	}

	public void setAppSettings(SharedPreferences appSettings) {
		this.appSettings = appSettings;
	}

}
