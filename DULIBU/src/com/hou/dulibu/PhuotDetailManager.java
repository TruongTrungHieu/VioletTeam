package com.hou.dulibu;

import io.socket.client.Manager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.hou.adapters.PhuotViewPagerAdapter;

import com.hou.fragment.ListPhuotFragment;
import com.hou.fragment.PhuotDetailOverview;
import com.hou.sliding_tab.PhuotSlidingTabLayout;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class PhuotDetailManager extends ActionBarActivity {

	Toolbar toolbar;
	ViewPager pager;
	PhuotViewPagerAdapter adapter;
	PhuotSlidingTabLayout tabs;
	CharSequence Titles[] = { "Overview", "Location" };
	int Numboftabs = 2;
	String maDiemPhuot, tenDiemPhuot, ghiChu, image, lat_diemphuot, lon_diemphuot;
	int trangThaiChuan;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.phuot_detail_manager);

		Intent callerIntent = getIntent();
		if (callerIntent != null) {
			String a = callerIntent.getStringExtra("test");
		}
		toolbar = (Toolbar) findViewById(R.id.Phuottool_bar);
		setSupportActionBar(toolbar);

		if (getSupportActionBar() != null) {
			// getSupportActionBar().setBackgroundDrawable(new
			// ColorDrawable(Color.parseColor("#0aae44")));
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}
		adapter = new PhuotViewPagerAdapter(getSupportFragmentManager(), Titles, Numboftabs, getBaseContext());

		// Assigning ViewPager View and setting the adapter
		pager = (ViewPager) findViewById(R.id.Phuotpager);
		pager.setAdapter(adapter);

		// Assiging the Sliding Tab Layout View
		tabs = (PhuotSlidingTabLayout) findViewById(R.id.Phuottabs);
		tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true,
										// This makes the tabs Space Evenly in
										// Available width

		// Setting Custom Color for the Scroll bar indicator of the Tab View
		tabs.setCustomTabColorizer(new PhuotSlidingTabLayout.TabColorizer() {
			@Override
			public int getIndicatorColor(int position) {
				return getResources().getColor(R.color.StatusBarColor);
			}
		});
		tabs.setCustomTabView(R.layout.custom_tab, 0);
		// Setting the ViewPager For the SlidingTabsLayout
		tabs.setViewPager(pager);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.phuot_detail_manager, menu);
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
