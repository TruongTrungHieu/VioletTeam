package com.hou.dulibu;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;

public class SplashScreenActivity extends ActionBarActivity {

	private final int SPLASH_DISPLAY_LENGTH = 1000;
	private Context mContext;
	
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
