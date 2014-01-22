package com.lenovo.social;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;

public class Helper {
	private boolean canDownload;
	private boolean canDownloadWifiOnly;
	
	public Helper(Activity activity) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        canDownload = preferences.getBoolean("pref_can_download_images", true);
        canDownloadWifiOnly = preferences.getBoolean("pref_can_download_wifi_only", true);
	}
	public boolean canDownload(Activity activity) {
		if (canDownload) {
			if (canDownloadWifiOnly) {
				final ConnectivityManager connectivityManager = (ConnectivityManager) activity.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
				final android.net.NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
				if(wifi.isAvailable()) {
					return true;
				} else {
					return false;
				}
			} else {
				return true;
			}
		} else {
			return false;
		}
	}
	
	public static boolean isPackageInstalled(String packageName, Context context) {
		PackageManager packageManager = context.getPackageManager();
		try {
			packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
			return true;
		} catch (NameNotFoundException e) {
			return false;
		}
	}
}