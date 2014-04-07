package org.intschools.northern.utils;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

public class AndroidServicesUtil {
	public static void makePhoneCall(Context context, String phoneNumber) {
		Intent intent = new Intent(Intent.ACTION_CALL);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setData(Uri.parse("tel:" + phoneNumber));
		context.startActivity(intent);
	}

	@SuppressLint("NewApi")
	public static AlertDialog.Builder getAlertDialogBuilder(Context context) {
		final AlertDialog.Builder builder;
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
			builder = new AlertDialog.Builder(context);
		} else {
			// it's checked with the if clause (:
			builder = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT);
		}
		return builder;
	}
}
