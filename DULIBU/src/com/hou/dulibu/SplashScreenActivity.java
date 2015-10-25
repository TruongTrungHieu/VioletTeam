package com.hou.dulibu;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hou.app.Global;
import com.hou.asynctask.GetListCityAsyncTask;
import com.hou.database_handler.ExecuteQuery;
import com.hou.model.Tinh_Thanhpho;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.support.v7.app.ActionBarActivity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class SplashScreenActivity extends ActionBarActivity {

	private final int SPLASH_DISPLAY_LENGTH = 1000;
	private TextView tvSlogan;
	private int Pagenumber = 1;
	private int p = 1;
	private String kq;
	private ExecuteQuery exeQ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);

		if (getSupportActionBar() != null) {
			getSupportActionBar().hide();
		}

		exeQ = new ExecuteQuery(getApplicationContext());
		exeQ.createDatabase();
		exeQ.open();

		ArrayList<Tinh_Thanhpho> tinh = exeQ.getAllTinhThanhpho();
		if (exeQ.delete_tbl_tinh_thanhpho()){ 
			for (int i = 1; i < 5; i++) {
				getListCity(i);
			}
		}
		ArrayList<Tinh_Thanhpho> tinh1 = exeQ.getAllTinhThanhpho();
		
		SharedPreferences sharedPreferences = this.getSharedPreferences(
				"gpstracker", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putBoolean("firstTimeGettingPosition", true);
		editor.putFloat("totalDistanceInMeters", 0f);
		editor.commit();

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {

				Intent intent = new Intent(SplashScreenActivity.this,
						LoginManagerActivity.class);
				startActivity(intent);

			}
		}, SPLASH_DISPLAY_LENGTH);
		Typeface tf = Typeface.createFromAsset(this.getAssets(),
				"fonts/Roboto-Thin.ttf");
		tvSlogan = (TextView) findViewById(R.id.tvSlogan);
		tvSlogan.setTypeface(tf);
		GetListCityAsyncTask ga = new GetListCityAsyncTask();
		ga.execute(Global.BASE_URI + "/" + Global.URI_LISTCITY_PATH);
	}

	private void getListCity(int i) {
		AsyncHttpClient client = new AsyncHttpClient();

		client.get(
				Global.BASE_URI + "/" + Global.URI_LISTCITY_PATH + "?p=" + i,
				new AsyncHttpResponseHandler() {
					public void onSuccess(String response) {
						Log.e("getListCity", response);
						kq = response;
						exeQ.insert_tbl_tinh_thanhpho_multi(listCity(kq));
					}

					@Override
					public void onFailure(int statusCode, Throwable error,
							String content) {
						Toast.makeText(getApplicationContext(),
								"khong vao duoc " + statusCode,
								Toast.LENGTH_LONG).show();
					}
				});
	}

	private ArrayList<Tinh_Thanhpho> listCity(String response) {
		ArrayList<Tinh_Thanhpho> lstCity = new ArrayList<>();
		try {
			JSONArray arrObj = new JSONArray(response);
			for (int i = 0; i < arrObj.length(); i++) {
				JSONObject cityLocationJson = arrObj.getJSONObject(i);
				Tinh_Thanhpho city = new Tinh_Thanhpho();

				String _id = cityLocationJson.optString("_id");
				String ghichu = cityLocationJson.optString("note");
				String lat = cityLocationJson.optString("lat") + "";
				String lon = cityLocationJson.optString("lon") + "";
				String tenTinh = cityLocationJson.optString("name");
				String image = cityLocationJson.optString("image");
				city.setMaTinh(_id);
				city.setGhichu(ghichu);
				city.setLat(lat);
				city.setLon(lon);
				city.setImage(image);
				city.setTenTinh(tenTinh);
				Log.e("listCity", tenTinh);
				lstCity.add(city);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return lstCity;
	}

	private boolean executeWhenRegisterSuccess(String reponse) {
		boolean check = true;
		return check;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash_screen, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public boolean isServiceRunning() {
		ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		for (RunningServiceInfo service : manager
				.getRunningServices(Integer.MAX_VALUE)) {
			if ("com.hou.gps.GetLocationService".equals(service.service
					.getClassName())) {
				return true;
			}
		}
		return false;
	}
}
