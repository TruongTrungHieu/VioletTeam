package com.hou.dulibu;

import android.support.v7.app.ActionBarActivity;

import java.util.ArrayList;

import com.hou.adapters.UserConfirmAdapter;
import com.hou.model.UserConfirm;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

public class UserSecureConfirmManager extends ActionBarActivity {
	TextView tvLostUserName;
	ListView lvConfirm;
	ArrayList<UserConfirm> ucArr;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_secure_confirm);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		tvLostUserName = (TextView) findViewById(R.id.LostUsername);
		Typeface tf = Typeface.createFromAsset(this.getAssets(), "fonts/Roboto-Thin.ttf");
		tvLostUserName.setTypeface(tf);
		
		UserConfirm uc1 = new UserConfirm("user1", "Nguyễn Văn C", 1);
		UserConfirm uc2 = new UserConfirm("user2", "Trần Văn K", 0);
		ucArr = new ArrayList<UserConfirm>();
		ucArr.add(uc1);
		ucArr.add(uc2);
		UserConfirmAdapter adapter = new UserConfirmAdapter(this, R.layout.user_confirm_item, ucArr);
		lvConfirm = (ListView) findViewById(R.id.lvListConfirm);
		lvConfirm.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_secure_confirm, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
