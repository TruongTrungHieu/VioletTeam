package com.hou.app;

import java.io.File;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import com.hou.model.Diemphuot;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Patterns;

public class Global {

	public static String DULIBU = "DULIBU";

	/*
	 * URI
	 */
	// public static String BASE_URI = "http://api.dulibu.com";
	public static String BASE_URI = "http://128.199.112.15";
	public static String URI_DANGNHAP_PATH = "user/login";
	public static String URI_DANGKY_PATH = "user/register";
	public static String URI_DANGXUAT_PATH = "user/logout";
	public static String URI_LISTCITY_PATH = "location";
	public static String URI_TRIP_TRIP = "trip";
	public static String URI_CREATENEWTRIP_PATH = "trip/create";
	public static String URI_GETPLACE_PATH ="location/travel";
	public static String URI_REGISTERTOTRIP_PATH = "trip/register";
	public static String URI_UPDATETRIPLOCATIONS_PATH = "trip/locations";

	/*
	 * ShareReferences
	 */
	public static String XML_FILE_NAME = "DULIBU_XML";

	// User
	public static String USER_MAUSER = "mauser";
	public static String USER_USERNAME = "username";
	public static String USER_FULLNAME = "fullname";
	public static String USER_EMAIL = "email";
	public static String USER_NGAYSINH = "ngaysinh";
	public static String USER_SDT = "sdt";
	public static String USER_GIOITINH = "gioitinh";
	public static String USER_SDT_LIENHE = "sdt_lienhe";
	public static String USER_AVATAR = "avatar";
	public static String USER_GHICHU = "ghichu";
	public static String USER_ACCESS_TOKEN = "access_token";

	// Multi language
	public static String MULTI_LANGUAGE = "multi_language";
	public static String MULTI_LANGUAGE_VI = "vi";
	public static String MULTI_LANGUAGE_EN = "en";
	public static String MULTI_LANGUAGE_DEFAULT = "vi";

	// Access Token
	public static String ACCESS_TOKEN = "access_token";
	public static String ACCESS_TOKEN_DEFAULT = "0";

	/*
	 * End ShareReferences Key and Default value
	 */

	public static ArrayList<Diemphuot> LIST_DIEMPHUOT = new ArrayList<Diemphuot>();

	public static String getURI(String image) {
		File mediaStorageDir;
		if (Build.VERSION.SDK_INT > 8) {
			mediaStorageDir = Environment
					.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		} else {
			mediaStorageDir = new File(
					Environment.getExternalStorageDirectory(), "Pictures");
		}

		return "file://" + mediaStorageDir.getPath() + "/" + image;
	}

	public static void savePreference(Context mContext, String key, String value) {
		SharedPreferences mSharedPrefences = mContext.getSharedPreferences(
				XML_FILE_NAME, Context.MODE_PRIVATE);
		Editor mEditor = mSharedPrefences.edit();
		mEditor.putString(key, value);
		mEditor.commit();
	}

	public static String getPreference(Context mContext, String key,
			String defValue) {
		SharedPreferences mSharedPrefences = mContext.getSharedPreferences(
				XML_FILE_NAME, Context.MODE_PRIVATE);
		return mSharedPrefences.getString(key, defValue);
	}

	// Change language
	public static void changeLanguage(Context mContext, String multi_language) {
		savePreference(mContext, MULTI_LANGUAGE, multi_language);
		Locale locale = new Locale(multi_language);
		Resources res = mContext.getResources();
		DisplayMetrics dm = res.getDisplayMetrics();
		Configuration conf = res.getConfiguration();
		conf.locale = locale;
		res.updateConfiguration(conf, dm);
	}

	// Setup language
	public static void setupLanguage(Context mContext) {
		String multi_language = getPreference(mContext, MULTI_LANGUAGE,
				MULTI_LANGUAGE_DEFAULT);
		Locale locale = new Locale(multi_language);
		Resources res = mContext.getResources();
		DisplayMetrics dm = res.getDisplayMetrics();
		Configuration conf = res.getConfiguration();
		conf.locale = locale;
		res.updateConfiguration(conf, dm);
	}

	// Create folder DULIBU in device
	public static void createFolderDULIBU() {
		File folder = new File(Environment.getExternalStorageDirectory() + "/"
				+ DULIBU);
		boolean success = true;
		if (!folder.exists()) {
			success = folder.mkdir();
		}
		if (success) {

		} else {

		}
	}

	public static boolean isValidEmail(String email) {
	    return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
	
	@SuppressWarnings("null")
	public static String convertStringToDate(String dateString) {
		SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-DD",
				Locale.ENGLISH);
		Date date = null;
		try {
			date = (Date) format.parse(dateString);
			return date.toString();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date.toString();
	}

}
