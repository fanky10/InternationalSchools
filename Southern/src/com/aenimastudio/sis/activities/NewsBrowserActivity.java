package com.aenimastudio.sis.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.aenimastudio.sis.R;
import com.aenimastudio.sis.constants.AppConstants;
import com.aenimastudio.sis.constants.BrowserUrlUtils;
import com.aenimastudio.sis.content.NetworkStatus;
import com.aenimastudio.sis.content.NetworkStatusListener;
import com.aenimastudio.sis.utils.AndroidServicesUtil;

public class NewsBrowserActivity extends AbstractBrowserActivity {

	protected boolean overrideUrlLoading(WebView view, String url) {
		webView.onPause();
		if (BrowserUrlUtils.isPDF(url)) {
			showPDFView(url);
			return true;
		}
		redirectToExternalBrowser(url);
		return true;
	}
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		webView.onPause();
	}

	public void onRestoreInstanceState(Bundle savedInstanceState) {
		// Always call the superclass so it can restore the view hierarchy
		super.onRestoreInstanceState(savedInstanceState);
		webView.onResume();
	}

	private void redirectToExternalBrowser(String url) {
		Bundle bundle = new Bundle();
		bundle.putString(AppConstants.SHARED_URL_KEY, url);
		Intent intent = new Intent(getApplicationContext(), SimpleBrowserActivity.class);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	protected void loadWebPage() {
		Bundle bundle = getIntent().getExtras();
		if (bundle == null) {
			throw new IllegalArgumentException("This Activity should be intented with a bundle object");
		}

		Integer userId = bundle.getInt(AppConstants.SHARED_USER_ID_KEY);
		if (userId == null || userId < 1) {
			throw new IllegalArgumentException("This Activity should be intented with a valid userId");
		}

		String webPage = new StringBuilder().append(getWebAppUrl())
				.append(getResources().getString(R.string.news_page)).append("?")
				.append(getResources().getString(R.string.news_param_user_id)).append("=").append(userId).toString();

		webView.loadUrl(webPage);
	}

	protected void showLogoutModal() {
		webView.onPause();
		AndroidServicesUtil.getAlertDialogBuilder(this).setIcon(android.R.drawable.ic_dialog_alert)
				.setTitle(R.string.text_confirm_logout).setMessage(R.string.text_ask_logout)
				.setPositiveButton(R.string.dialog_accept, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						logout();
					}

				}).setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						webView.onResume();
					}

				}).show();
	}

	private void logout() {
		Intent intent = getLogoutIntent();
		finish();
		startActivity(intent);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode != AppConstants.REQUEST_CODE_MODAL_LOGOUT) {
			return;// I dont care.
		}
		if (resultCode == RESULT_OK) {
			logout();
		}
		if (resultCode == RESULT_CANCELED) {
			//do nothing it was the logout modal
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		removeNetworkStatusListener(networkStatusListener);
	}

	@Override
	protected void configureMenuBar() {
		ImageView imgFood = (ImageView) findViewById(R.id.commonMenuLeftButton);
		imgFood.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showFoodMenu();
			}
		});

		ImageView imgLogout = (ImageView) findViewById(R.id.commonMenuRightButton);
		imgLogout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showLogoutModal();
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
					reloadPage();
				}
			}
		};
		addNetworkStatusListener(networkStatusListener);
	}

	private void showFoodMenu() {
		webView.onPause();
		Intent intent = new Intent(getApplicationContext(), FoodMenuBrowserActivity.class);
		startActivity(intent);
	}

}
