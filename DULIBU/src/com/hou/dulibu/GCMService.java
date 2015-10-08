package com.hou.dulibu;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.os.AsyncTask;
import android.content.Context;
import android.util.Log;

/*
 * Created by: Uong Thanh Tung
 */

public class GCMService{
	GoogleCloudMessaging gcm;
	String PROJECT_NUMBER = "728072246164";
	String regid;
	Context context;
	
	public GCMService(Context context) {
		super();
		this.context = context;
	}
	public String getRegId() {
		try {
			String recieve = new getGCMID().execute().get().toString();
			Log.d("Code", recieve);
			return recieve;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return e.getMessage();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return e.getMessage();
			
		}		
	}
	public class getGCMID extends AsyncTask<Void, Void, String>{

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			String msg = "";
			try {
				if (gcm == null) {
					gcm = GoogleCloudMessaging.getInstance(context);
				}
				regid = gcm.register(PROJECT_NUMBER);
				msg = "Device registered, registration ID=" + regid;
				Log.i("GCM", msg);

			} catch (IOException ex) {
				ex.printStackTrace();
				msg = "Error :" + ex.getMessage();
			}
			return msg;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
		}
		
	}
	/*public void getDataFromNoti(){
		
		//Trích xuất dữ liệu của Notification
		tv1.setText(myData.getStringExtra("NotiTitle"));
		tv2.setText(myData.getStringExtra("NotiText"));
	}*/
}
