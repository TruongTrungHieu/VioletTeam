package com.hou.app;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import com.hou.upload.MD5;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.widget.DatePicker;

public class Global {

	public static String DULIBU = "DULIBU";

	/*
	 * URI
	 */
	// public static String BASE_URI = "http://api.dulibu.com";
	public static String BASE_URI = "http://128.199.112.15";
	public static String URI_DANGNHAP_PATH = "user/login";
	public static String URI_FORGET_PASS = "user/forgot";
	public static String URI_CHANGE_PASS_PATH = "user/password";
	public static String URI_DANGKY_PATH = "user/register";
	public static String URI_DANGXUAT_PATH = "user/logout";
	public static String URI_LISTCITY_PATH = "location";
	public static String URI_TRIP_TRIP = "trip";
	public static String URI_TRIP_GET_TRIP = "trip/view";
	public static String URI_CREATENEWTRIP_PATH = "trip/create";
	public static String URI_GETPLACE_PATH = "location/travel";
	public static String URI_REGISTERTOTRIP_PATH = "trip/register";
	public static String URI_UPDATETRIPLOCATIONS_PATH = "trip/locations";
	public static String URI_DOWNLOAD_IMAGE = BASE_URI + "/static/travel/5610/";
	public static String URI_GETCHIPHI_PATH = "trip/expense";
	public static String URI_POSTCHIPHI_PATH = "trip/expense";
	public static String URI_GETLISTMEMBER_PATH = "trip/members";
	public static String URI_DELETECHIPHI_PATH = "trip/expense/delete";
	public static String URI_POSTEVENT_PATH = "trip/event";
	public static String URI_GETEVENT_PATH = "trip/event";
	public static String TRIP_INVITE = "trip/invite";
	public static String URI_UPDATEINFO_PATH = "user/update";

	public static String URI_UPDATEAVATAR_PATH = BASE_URI + "/user/avatar";
	public static String DIEMPHUOT_P = "20";
	public static String TRIP_TRIP_ID = "_id_trip";
	public static String TRIP_MONEY = "_trip_money";
	public static String TRIP_REGISTER = "trip/register";
	public static String TRIP_LEAVE = "trip/leave";
	public static String TRIP_ROLE = "trip/role";

	/*
	 * ShareReferences
	 */
	public static String XML_FILE_NAME = "DULIBU_XML";

	// User
	public static String USER_MAUSER = "mauser";
	public static String ID_WHENE_CREATE_TRIP = "id_trip";
	public static String USER_USERNAME = "username";
	public static String USER_FULLNAME = "fullname";
	public static String USER_EMAIL = "email";
	public static String USER_NGAYSINH = "ngaysinh";
	public static String USER_SDT = "sdt";
	public static String USER_GIOITINH = "gioitinh";
	public static String USER_SDT_LIENHE = "sdt_lienhe";
	public static String USER_AVATAR = "avatar";
	public static String USER_GHICHU = "ghichu";

	// ROLE

	public static String USER_ROLE_ADMIN = "2";
	public static String USER_ROLE_USER = "1";
	public static String USER_ROLE_MEMBER = "3";
	public static String USER_ROLE_DAN_DOAN = "4";
	public static String USER_ROLE_THU_QUY = "5";
	public static String USER_ROLE_CHAY_BIEN = "6";
	public static String USER_ROLE_CAM = "0";
	public static String USER_CREATEBY_TRIP = "_id_create";
	public static String USER_ROLE_CHANGE = "0";

	// Multi language
	public static String MULTI_LANGUAGE = "multi_language";
	public static String MULTI_LANGUAGE_VI = "vi";
	public static String MULTI_LANGUAGE_EN = "en";
	public static String MULTI_LANGUAGE_DEFAULT = "vi";

	// Access Token
	public static String USER_ACCESS_TOKEN = "access_token";
	public static String ACCESS_TOKEN_DEFAULT = "0";
	// Load number page
	public static String PAGE_NUMBER = "0";
	// Target
	public static String TARGET_TRIP = "TRIP";
	public static String TARGET_USER = "USER";
	// Start tracking first time
	public static boolean FIRST_TIME_TRACKING = false;
	
	public static String PAGE_PHUOT = "page_phươt";
	public static int PAGE_PHUOT_DEFAULT = 1;
	
	// TRANG THAI AN TOAN
	public static String STATUS_SAFE = "status";
	
	/*
	 * End ShareReferences Key and Default value
	 */

	public static String getURI(String image) {
		File mediaStorageDir;
		/*
		 * if (Build.VERSION.SDK_INT > 8) { mediaStorageDir = Environment
		 * .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES); }
		 * else {
		 */
		mediaStorageDir = new File(Environment.getExternalStorageDirectory(),
				"DULIBU");
		// }

		return "file://" + mediaStorageDir.getPath() + "/" + image;
	}

	public static void savePreference(Context mContext, String key, String value) {
		SharedPreferences mSharedPrefences = mContext.getSharedPreferences(
				XML_FILE_NAME, Context.MODE_PRIVATE);

		Editor mEditor = mSharedPrefences.edit();
		mEditor.putString(key, value);
		mEditor.commit();
	}

	public static void saveIntPreference(Context mContext, String key, int value) {
		SharedPreferences mSharedPrefences = mContext.getSharedPreferences(
				XML_FILE_NAME, Context.MODE_PRIVATE);

		Editor mEditor = mSharedPrefences.edit();
		mEditor.putInt(key, value);
		mEditor.commit();
	}

	public static String getPreference(Context mContext, String key,
			String defValue) {
		SharedPreferences mSharedPrefences = mContext.getSharedPreferences(
				XML_FILE_NAME, Context.MODE_PRIVATE);
		return mSharedPrefences.getString(key, defValue);
	}

	public static int getIntPreference(Context mContext, String key,
			int defValue) {
		SharedPreferences mSharedPrefences = mContext.getSharedPreferences(
				XML_FILE_NAME, Context.MODE_PRIVATE);
		return mSharedPrefences.getInt(key, defValue);
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

	// Validator email
	public static boolean isValidEmail(String email) {
		return Patterns.EMAIL_ADDRESS.matcher(email).matches();
	}

	@SuppressWarnings("null")
	public static String convertStringToDate(String dateString) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd",
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

	/*
	 * NEARBY PLACE GOOGLE MAP
	 */
	public static String NEARBY_GAS = "gas_station";
	public static String NEARBY_HOSPITAL = "hospital";
	public static String NEARBY_RESTAURANT = "restaurant";
	public static String NEARBY_POLICE = "police";
	public static String NEARBY_ATM = "atm";

	public static String URL_NEARBY(double lat, double lon, int radius,
			String type) {
		String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="
				+ lat
				+ ","
				+ lon
				+ "&radius="
				+ radius
				+ "&type="
				+ type
				+ "&key=AIzaSyCV_sND3UkBW8i3KzPWRJ7C452g2Ao4seg";
		return url;
	}

	public static int NEARBY_RADIUS = 10000;

	/*
	 * END - NEARBY PLACE GOOGLE MAP
	 */

	public static java.util.Date getDateFromDatePicket(DatePicker datePicker) {
		int day = datePicker.getDayOfMonth();
		int month = datePicker.getMonth();
		int year = datePicker.getYear();

		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day);

		return calendar.getTime();
	}

	public static void StartServiceGetLocation(Activity atv, Intent intent) {
		if (!isServiceRunning(atv, Activity.ACTIVITY_SERVICE)) {
			atv.startService(intent);
			FIRST_TIME_TRACKING = true;
		}
	}

	public static void StopServiceGetLocation(Activity atv, Intent intent) {
		if (isServiceRunning(atv, Activity.ACTIVITY_SERVICE)) {
			atv.stopService(intent);
			FIRST_TIME_TRACKING = false;
		}
	}

	public static boolean isServiceRunning(Activity atv, String name) {
		ActivityManager manager = (ActivityManager) atv.getSystemService(name);
		for (RunningServiceInfo service : manager
				.getRunningServices(Integer.MAX_VALUE)) {
			if ("com.hou.gps.GetLocationService".equals(service.service
					.getClassName())) {
				return true;
			}
		}
		return false;
	}

	public static void writeFile(String data, String index) {
		File root = null;
		try {
			// check for SDcard
			root = Environment.getExternalStorageDirectory();
			// check sdcard permission
			if (root.canWrite()) {
				File fileDir = new File(root.getAbsolutePath() + "/DULIBU");
				fileDir.mkdirs();

				File file = new File(fileDir, index);
				if (file.exists()) {
					file.delete();
				}
				FileWriter filewriter = new FileWriter(file);
				BufferedWriter out = new BufferedWriter(filewriter);
				out.write(data);
				out.close();
			}
		} catch (IOException e) {
			Log.e("ERROR:---",
					"Could not write file to SDCard" + e.getMessage());
		}
	}

	public static void writeFile(Object data, String index) {
		File root = null;

		try {
			// check for SDcard
			root = Environment.getExternalStorageDirectory();
			// check sdcard permission
			if (root.canWrite()) {
				File fileDir = new File(root.getAbsolutePath() + "/DULIBU");
				fileDir.mkdirs();

				File file = new File(fileDir, index);
				if (file.exists()) {
					file.delete();
				}

				FileOutputStream fileOutputStream = new FileOutputStream(file);
				ObjectOutputStream outputStream = new ObjectOutputStream(
						fileOutputStream);
				outputStream.writeObject(data);
				outputStream.close();
				fileOutputStream.close();
			}
		} catch (IOException e) {
			Log.e("ERROR:---",
					"Could not write file to SDCard" + e.getMessage());
		}

	}

	public static Object readFileObject(String index) {
		File root = null;
		Object result = null;
		root = Environment.getExternalStorageDirectory();
		if (root.canRead()) {

			try {
				File fileDir = new File(root.getAbsolutePath() + "/DULIBU");

				File file = new File(fileDir, index);
				FileInputStream fileInputStream = new FileInputStream(file);
				ObjectInputStream inputStream = new ObjectInputStream(
						fileInputStream);
				result = inputStream.readObject();
				inputStream.close();
				fileInputStream.close();
			} catch (IOException e) {
				Log.e("ERROR:---",
						"Could not write file to SDCard" + e.getMessage());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return result;
	}

	//
	// public static Object readFileObject(String fileName){
	// Object result = null;
	// File myFile = new
	// File(Environment.getExternalStorageDirectory()+"/DULIBU/"+fileName);
	//
	// try {
	// FileInputStream fIn = new FileInputStream(myFile);
	// ObjectInputStream inputStream = new ObjectInputStream(fIn);
	// result = inputStream.readObject();
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return result;
	// }

	@SuppressWarnings("resource")
	public static String readFile(String fileName) {
		File myFile = new File(Environment.getExternalStorageDirectory()
				+ "/DULIBU/" + fileName);
		String aDataRow = "";
		String aBuffer = "";
		try {
			FileInputStream fIn = new FileInputStream(myFile);
			BufferedReader myReader = new BufferedReader(new InputStreamReader(
					fIn));
			while ((aDataRow = myReader.readLine()) != null) {
				aBuffer += aDataRow;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return aBuffer;
	}

	private static Socket socket;
	public static String TIMESTAMP = new java.util.Date().getTime() + "";

	public static Socket getSocketServer(final Activity activity) {
		if (socket == null) {
			try {
				socket = IO.socket(BASE_URI);

				socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

					@Override
					public void call(Object... arg0) {

						// TODO Auto-generated method stub
						JSONObject data = new JSONObject();

						try {
							data.put("timestamp", Global.TIMESTAMP).put(
									"access_token",
									Global.getPreference(activity,
											Global.USER_ACCESS_TOKEN, "token"));
							socket.emit(".join", data);
							Log.d("read file",
									readFile(new MD5().getMD5("18/11/1994")
											.toString()));
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (NoSuchAlgorithmException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});

			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (!socket.connected()) {
			socket.connect();
		}

		return socket;

	}

}
