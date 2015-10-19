package com.hou.dulibu;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.telephony.TelephonyManager;

/*
 * Created by: Uong Thanh Tung
 * connection status, battery, ...
 */

public class DeviceStatus {
	public int getApiLevel() {
		int apiLevel = android.os.Build.VERSION.SDK_INT;
		return apiLevel;
	}

	public String getDeviceCodeName() {
		String deviceCodeName = android.os.Build.DEVICE;
		return deviceCodeName;
	}

	public String getDeviceName() {
		String deviceName = android.os.Build.MODEL;
		return deviceName;
	}

	public String getDeviceProduct() {
		String deviceProduct = android.os.Build.PRODUCT;
		return deviceProduct;
	}

	public String getSimNumber(Context c) {
		TelephonyManager telemamanger = (TelephonyManager) c.getSystemService(Context.TELEPHONY_SERVICE);
		String getSimNumber = telemamanger.getLine1Number();
		return getSimNumber;
	}

	public String getSimSerial(Context c) {
		TelephonyManager telemamanger = (TelephonyManager) c.getSystemService(Context.TELEPHONY_SERVICE);
		String getSimSerialNumber = telemamanger.getSimSerialNumber();
		return getSimSerialNumber;
	}

	public String getEMEI(Context c) {
		TelephonyManager telemamanger = (TelephonyManager) c.getSystemService(Context.TELEPHONY_SERVICE);
		String getImei = telemamanger.getDeviceId();
		return getImei;
	}

	public int getBatteryLevel(Context c) {
		Intent batteryIntent = c.getApplicationContext().registerReceiver(null,
				new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
		int batLevel = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
		return batLevel;
	}

	public String getCurrentConnection(Context c) {
		String Stt = "";
		ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (wifiNetwork != null && wifiNetwork.isConnected()) {
			Stt += "Wifi";

		}

		NetworkInfo mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (mobileNetwork != null && mobileNetwork.isConnected()) {
			Stt += "3G";
		}

		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		if (activeNetwork == null) {
			Stt += "No Internet Access";
		}

		return Stt;
	}
}