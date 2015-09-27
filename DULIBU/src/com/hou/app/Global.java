package com.hou.app;

import java.io.File;
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

public class Global {

	public static String DULIBU = "DULIBU";
	
	/*
	 * ShareReferences
	 */
	public static String XML_FILE_NAME = "DULIBU_XML";
	
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
	public static String getURI(String image){
		File mediaStorageDir;
		if (Build.VERSION.SDK_INT > 8) {
			mediaStorageDir = Environment
					.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		} else {
			mediaStorageDir = new File(
					Environment.getExternalStorageDirectory(), "Pictures");
		}
		
		return "file://"+ mediaStorageDir.getPath() + "/" + image;
	}
	
	public static void savePreference(Context mContext, String key, String value){
		SharedPreferences mSharedPrefences = mContext.getSharedPreferences(XML_FILE_NAME, Context.MODE_PRIVATE);
		Editor mEditor = mSharedPrefences.edit();
		mEditor.putString(key, value);
		mEditor.commit();
	}
	
	public static String getPreference(Context mContext, String key, String defValue){
		SharedPreferences mSharedPrefences = mContext.getSharedPreferences(XML_FILE_NAME, Context.MODE_PRIVATE);
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
		String multi_language = getPreference(mContext, MULTI_LANGUAGE, MULTI_LANGUAGE_DEFAULT);
		Locale locale = new Locale(multi_language);
		Resources res = mContext.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = locale;
        res.updateConfiguration(conf, dm);
	}
	
	// Create folder DULIBU in device
	public static void createFolderDULIBU() {
		File folder = new File(Environment.getExternalStorageDirectory() + "/" + DULIBU);
		boolean success  = true;
		if (!folder.exists()) {
			success = folder.mkdir();
		}
		if (success) {
			
		} else {
			
		}
	}
}