package com.hou.dulibu;

import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.hou.adapters.PhuotViewPagerAdapter;
import com.hou.fragment.ListPhuotFragment;
import com.hou.sliding_tab.PhuotSlidingTabLayout;

import android.content.Intent;
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
	public String maDiemPhuot,tenDiemPhuot,ghiChu;
	int trangThaiChuan;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.phuot_detail_manager);
/*		Intent callerIntent = getIntent().getExtras();
		Bundle myPackage = callerIntent.getBundleExtra("myBundle");
		maDiemPhuot = myPackage.getString("maDiemPhuot");
		tenDiemPhuot = myPackage.getString("tenDiemPhuot");
		ghiChu = myPackage.getString("ghiChu");
		trangThaiChuan = myPackage.getInt("trangThaiChuan");
		Log.e("Viet5091","Ma diem phuot:"+ maDiemPhuot + ";" + "Trang thai chuan: " +trangThaiChuan);
		
		Bundle bundle = new Bundle();
		bundle.putString("maDiemPhuot", maDiemPhuot);
		bundle.putString("tenDiemPhuot", tenDiemPhuot);
		bundle.putString("ghiChu", ghiChu);
	//	bundle.putInt("trangThaiChuan", trangThaiChuan);
		// set Fragmentclass Arguments
		ListPhuotFragment f = new ListPhuotFragment();
		f.setArguments(bundle);*/
		/*
		 * Intent intent = getIntent(); String maDiemP =
		 * intent.getStringExtra("maDiemPhuot"); String tenDiemP =
		 * intent.getStringExtra("tenDiemPhuot"); String ghiChu =
		 * intent.getStringExtra("ghiChu"); String trangThaiChuan =
		 * intent.getStringExtra("trangThaiChuan");
		 * 
		 * Bundle b = new Bundle(); b.putString("maDiemPhuot", maDiemP);
		 * b.putString("tenDiemPhuot", tenDiemP); b.putString("ghiChu", ghiChu);
		 * b.putString("trangThaiChuan", trangThaiChuan);
		 * 
		 * PhuotDetailOverview fo = new PhuotDetailOverview();
		 * fo.setArguments(b);
		 */

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
