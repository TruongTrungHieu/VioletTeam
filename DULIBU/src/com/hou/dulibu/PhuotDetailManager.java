package com.hou.dulibu;

import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.hou.adapters.LichtrinhViewPagerAdapter;
import com.hou.adapters.PhuotViewPagerAdapter;
import com.hou.sliding_tab.LichTrinhSlidingTabLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class PhuotDetailManager extends ActionBarActivity {
	
	Toolbar toolbar;
	ViewPager pager;
	PhuotViewPagerAdapter adapter;
	LichTrinhSlidingTabLayout tabs;
	CharSequence Titles[] = { "Overview", "Comment" };
	int Numboftabs = 2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.phuot_detail_manager);
		
		/*Intent intent = getIntent();
		String maDiemP = intent.getStringExtra("maDiemPhuot");
		String tenDiemP = intent.getStringExtra("tenDiemPhuot");
		String ghiChu = intent.getStringExtra("ghiChu");
		String trangThaiChuan = intent.getStringExtra("trangThaiChuan");
		
		Bundle b = new Bundle();
		b.putString("maDiemPhuot", maDiemP);
		b.putString("tenDiemPhuot", tenDiemP);
		b.putString("ghiChu", ghiChu);
		b.putString("trangThaiChuan", trangThaiChuan);
		
		PhuotDetailOverview fo = new PhuotDetailOverview();
		fo.setArguments(b);*/
		
		toolbar = (Toolbar) findViewById(R.id.Phuottool_bar);
		setSupportActionBar(toolbar);

		if (getSupportActionBar() != null) {
			//getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0aae44")));
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}
		adapter = new PhuotViewPagerAdapter(getSupportFragmentManager(), Titles, Numboftabs);

		// Assigning ViewPager View and setting the adapter
		pager = (ViewPager) findViewById(R.id.Phuotpager);
		pager.setAdapter(adapter);

		// Assiging the Sliding Tab Layout View
		tabs = (LichTrinhSlidingTabLayout) findViewById(R.id.Phuottabs);
		tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true,
										// This makes the tabs Space Evenly in
										// Available width

		// Setting Custom Color for the Scroll bar indicator of the Tab View
		tabs.setCustomTabColorizer(new LichTrinhSlidingTabLayout.TabColorizer() {
			@Override
			public int getIndicatorColor(int position) {
				return getResources().getColor(R.color.StatusBarColor);
			}
		});

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
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
