package com.hou.GCM;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.hou.dulibu.LoginManagerActivity;
import com.hou.dulibu.MainManagerActivity;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class GCMMessageHandler extends IntentService {

	String mes;
	private Handler handler;

	public GCMMessageHandler() {
		super("GCMMessageHandler");
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		handler = new Handler();
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Bundle extras = intent.getExtras();

		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
		// The getMessageType() intent parameter must be the intent you received
		// in your BroadcastReceiver.
		String messageType = gcm.getMessageType(intent);

		mes = extras.getString("title");
		// showToast();
		SoundNoti();
		Log.i("GCM", "Received : (" + messageType + ")  " + extras.getString("title"));

		GCMBroadcastReceiver.completeWakefulIntent(intent);

	}

	public void showToast() {
		handler.post(new Runnable() {
			public void run() {
				Toast.makeText(getApplicationContext(), mes, Toast.LENGTH_LONG).show();
			}
		});

	}

	private void SoundNoti() {
		try {
			NotificationManager NM = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
			// Notification notify=new
			// Notification(android.R.drawable.stat_notify_more,"Test",System.currentTimeMillis());
			Notification notify = new Notification(android.R.drawable.stat_notify_more, "Test",
					System.currentTimeMillis());
			notify.flags = Notification.FLAG_AUTO_CANCEL;

			String Title = "DulibuTeam";
			String Text = "Keep movin forward";

			Intent intent = new Intent(getApplicationContext(), LoginManagerActivity.class);
			intent.putExtra("NotiTitle", Title);
			intent.putExtra("NotiText", Text);
			PendingIntent pending = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
			notify.setLatestEventInfo(getApplicationContext(), Title, Text, pending);
			NM.notify(0, notify);

			Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
			Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
			r.play();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}