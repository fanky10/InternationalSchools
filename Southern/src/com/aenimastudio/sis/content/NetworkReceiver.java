package com.aenimastudio.sis.content;

import java.util.ArrayList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class NetworkReceiver extends BroadcastReceiver {
	private static final String TAG = NetworkReceiver.class.getName();
	private List<NetworkStatusListener> networkStatusListenerList = new ArrayList<NetworkStatusListener>();

	@Override
	public void onReceive(Context context, Intent intent) {

		ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = conn.getActiveNetworkInfo();
		NetworkStatus networkStatus = NetworkStatus.OFFLINE;
		if (networkInfo != null && networkInfo.isConnected()) {
			networkStatus = NetworkStatus.ONLINE;
		}
		Log.d(TAG, "Network status: " + networkStatus);
		for (NetworkStatusListener networkStatusListener : networkStatusListenerList) {
			networkStatusListener.connectionChecked(networkStatus);
		}
	}

	public void addNetworkStatusListener(NetworkStatusListener networkStatusListener) {
		this.networkStatusListenerList.add(networkStatusListener);
	}

	public void removeNetworkStatusListener(NetworkStatusListener networkStatusListener) {
		this.networkStatusListenerList.remove(networkStatusListener);
	}

}
