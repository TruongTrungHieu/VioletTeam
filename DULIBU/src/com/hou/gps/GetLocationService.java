package com.hou.gps;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.hou.app.Global;
import com.hou.upload.MD5;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.json.JSONException;
import org.json.JSONObject;

@SuppressLint("NewApi")
public class GetLocationService extends Service implements
		GoogleApiClient.ConnectionCallbacks,
		GoogleApiClient.OnConnectionFailedListener, LocationListener {

	private static final String TAG = "LocationService";
	Socket socket = null;

	private boolean currentlyProcessingLocation = false;
	private LocationRequest locationRequest;
	private GoogleApiClient googleApiClient;
	private boolean updateLocation = true;
	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// if we are currently trying to get a location and the alarm manager
		// has called this again,
		// no need to start processing a new location.
		if (!currentlyProcessingLocation) {
			currentlyProcessingLocation = true;
			startTracking();
		}

		return START_NOT_STICKY;
	}

	private void startTracking() {
		Log.d(TAG, "startTracking");

		if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(this) == ConnectionResult.SUCCESS) {

			googleApiClient = new GoogleApiClient.Builder(this)
					.addApi(LocationServices.API).addConnectionCallbacks(this)
					.addOnConnectionFailedListener(this).build();

			if (!googleApiClient.isConnected()
					|| !googleApiClient.isConnecting()) {
				googleApiClient.connect();
			}
		} else {
			Log.e(TAG, "unable to connect to google play services.");
		}
	}
	

	protected void sendLocationDataToWebsite(Location location)
			throws MalformedURLException, JSONException, NoSuchAlgorithmException {
		// formatted for mysql datetime format
		float distance = 0f;

		final JSONObject checkPoint = new JSONObject();
		
		checkPoint.put("access_token", Global.getPreference(getBaseContext(),
				Global.USER_ACCESS_TOKEN, "access_token"));
		
		checkPoint.put("target_id", Global.getPreference(getBaseContext(),
				Global.TRIP_TRIP_ID, "trip_id"));
		
		checkPoint.put("target_type", Global.TARGET_TRIP);

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setTimeZone(TimeZone.getDefault());
		Date date = new Date(location.getTime());

		SharedPreferences sharedPreferences = this.getSharedPreferences(
				"gpstracker", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();


		if (Global.FIRST_TIME_TRACKING) {
			Log.d("Lấy lần đầu", "!");
			updateLocation = true;
			Global.FIRST_TIME_TRACKING = false;
		} else {
			Location previousLocation = new Location("");
			previousLocation.setLatitude(sharedPreferences.getFloat(
					"previousLatitude", 0f));
			previousLocation.setLongitude(sharedPreferences.getFloat(
					"previousLongitude", 0f));

			distance = location.distanceTo(previousLocation);
			if (distance > 2 || true) {
				Log.d("lớn hơn 2m", "!");
				editor.putFloat("previousLatitude",
						(float) location.getLatitude());
				editor.putFloat("previousLongitude",
						(float) location.getLongitude());
				editor.commit();
				updateLocation = true;
			} else {
				updateLocation = false;
			}
		}

		checkPoint.put("lat", Double.toString(location.getLatitude()));
		checkPoint.put("lon", Double.toString(location.getLongitude()));

		try {
			checkPoint.put("date",
					URLEncoder.encode(dateFormat.format(date), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
		}

		checkPoint
				.put("distance", String.format("%.1f", distance));

		// Double accuracyInFeet = location.getAccuracy() * 3.28;
		// checkPoint.put("accuracy",
		// Integer.toString(accuracyInFeet.intValue()));
		//
		// Double altitudeInFeet = location.getAltitude() * 3.28;
		// checkPoint.put("extrainfo",
		// Integer.toString(altitudeInFeet.intValue()));
		//
		// checkPoint.put("eventtype", "android");
		//
		// Float direction = location.getBearing();
		// checkPoint.put("direction", Integer.toString(direction.intValue()));
		if (!updateLocation) {
			return;
		}
		
		if (socket != null) {
			if (socket.connected()) {
				socket.emit("tracking", checkPoint);
				Log.d("check point", checkPoint+"");
			}
		} else {
			try {
				socket = IO.socket("http://128.199.112.15/");
				socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

					@Override
					public void call(Object... args) {
						socket.emit("tracking", checkPoint);
					}

				}).on("tracking", new Emitter.Listener() {

					@Override
					public void call(Object... obj) {
						Log.d("SERVER RETURN", ((JSONObject) obj[0]).toString());
					}

				}).on(".error", new Emitter.Listener() {

					@Override
					public void call(Object... obj) {
						Log.d("SOCKET", ((JSONObject) obj[0]).toString());
					}

				}).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {

					@Override
					public void call(Object... args) {
					}

				});
				socket.connect();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onLocationChanged(Location location) {
		if (location != null) {
			Log.e(TAG,
					"position: " + location.getLatitude() + ", "
							+ location.getLongitude() + " accuracy: "
							+ location.getAccuracy());

			try {
//				if (updateLocation) {
					sendLocationDataToWebsite(location);
//				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void stopLocationUpdates() {
		if (googleApiClient != null && googleApiClient.isConnected()) {
			googleApiClient.disconnect();
		}
	}

	/**
	 * Called by Location Services when the request to connect the client
	 * finishes successfully. At this point, you can request the current
	 * location or start periodic updates
	 */
	@Override
	public void onConnected(Bundle bundle) {
		Log.d(TAG, "onConnected");

		locationRequest = LocationRequest.create();
		locationRequest.setInterval(5000); // milliseconds
		locationRequest.setFastestInterval(5000); // the fastest rate in
													// milliseconds at which
													// your app can handle
													// location updates
		locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

		LocationServices.FusedLocationApi.requestLocationUpdates(
				googleApiClient, locationRequest, this);
	}

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		Log.e(TAG, "onConnectionFailed");

		stopLocationUpdates();
		stopSelf();
	}

	@Override
	public void onConnectionSuspended(int i) {
		Log.e(TAG, "GoogleApiClient connection has been suspend");
	}
}
