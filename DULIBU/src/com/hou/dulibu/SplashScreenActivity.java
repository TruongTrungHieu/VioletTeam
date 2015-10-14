package com.hou.dulibu;

import org.json.JSONException;
import org.json.JSONObject;

import com.hou.app.Global;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
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
	private Context mContext;
	private TextView tvSlogan;
	private int Pagenumber = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		mContext = this;
		
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent intent = new Intent(SplashScreenActivity.this, LoginManagerActivity.class);
				startActivity(intent);
				
			}
		}, SPLASH_DISPLAY_LENGTH);
		Typeface tf = Typeface.createFromAsset(this.getAssets(), "fonts/Roboto-Thin.ttf");
		tvSlogan = (TextView) findViewById(R.id.tvSlogan);
		tvSlogan.setTypeface(tf);
	}
	
	private void getListCity(){
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("Pagenumber", Pagenumber);
		
		client.post(Global.BASE_URI + "/" + Global.URI_DANGNHAP_PATH, params,
				new AsyncHttpResponseHandler() {
					public void onSuccess(String response) {
						Log.e("getListCity", response);
						// ham lay danh sach thanh pho (la 1 file JSON)
						Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
					}

					@Override
					public void onFailure(int statusCode, Throwable error,
							String content) {
						
					}
				});
	}
	
	private String listCity(String response){

		try {
			JSONObject cityLocationJson = new JSONObject(response);

			String _id = cityLocationJson.getString("_id");
			String ghichu = cityLocationJson.getString("ghichu");
			String lat = cityLocationJson.getDouble("lat")+"";
			String lon = cityLocationJson.getDouble("lon")+"";
			String tenTinh = cityLocationJson.getString("tenTinh");
			String image = cityLocationJson.getString("image");
			

			

			return "true";
		} catch (JSONException e) {
			e.printStackTrace();
			return "false";
		}

	
		
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
}
