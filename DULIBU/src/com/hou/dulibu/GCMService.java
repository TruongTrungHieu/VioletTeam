package com.hou.dulibu;

import java.io.IOException;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import android.os.AsyncTask;
import android.app.Activity;
import android.util.Log;

/*
 * Created by: Uong Thanh Tung
 */

public class GCMService extends Activity{
	GoogleCloudMessaging gcm;
	String PROJECT_NUMBER = "728072246164";
	String regid;

	public void getRegId() {
		new AsyncTask<Void, Void, String>() {
			@Override
			protected String doInBackground(Void... params) {
				String msg = "";
				try {
					if (gcm == null) {
						gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
					}
					regid = gcm.register(PROJECT_NUMBER);
					msg = "Device registered, registration ID=" + regid;
					Log.i("GCM", msg);

				} catch (IOException ex) {
					msg = "Error :" + ex.getMessage();

				}
				return msg;
			}

			/*@Override
			protected void onPostExecute(String msg) {
				etRegId.setText(msg + "\n");
			}*/
		}.execute(null, null, null);
	}
	/*public void getDataFromNoti(){
		
		//Trích xuất dữ liệu của Notification
		tv1.setText(myData.getStringExtra("NotiTitle"));
		tv2.setText(myData.getStringExtra("NotiText"));
	}*/
}
