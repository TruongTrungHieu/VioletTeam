package com.hou.dulibu;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class RegisterManagerActivity extends ActionBarActivity {
	RadioGroup radiosex;
	RadioButton rbButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_manager);
		
		if(getSupportActionBar() != null){
			getSupportActionBar().setDisplayShowCustomEnabled(true);
			getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0aae44")));
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}
		radiosex = (RadioGroup) findViewById(R.id.radiosex);
		int rbSelected = radiosex.getCheckedRadioButtonId();
		rbButton = (RadioButton) findViewById(rbSelected);
		
	}
	
	public void CheckboxSelected(){
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register_manager, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == android.R.id.home) {
			onBackPressed();
		}
		return super.onOptionsItemSelected(item);
	}
}
