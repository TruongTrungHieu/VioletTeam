package com.hou.dulibu;

import com.hou.adapters.LichtrinhViewPagerAdapter;
import com.hou.sliding_tab.LichTrinhSlidingTabLayout;

import android.app.LocalActivityManager;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

public class TripDetailManagerActivity extends ActionBarActivity {
	private Menu currentMenu;
	private TabHost tabHost;
	private TabSpec infoSpec, memberSpec, messageSpec, tripSpec;

	Toolbar toolbar;
	ViewPager pager;
	LichtrinhViewPagerAdapter adapter;
	LichTrinhSlidingTabLayout tabs;
	CharSequence Titles[] = { "Info", "Members", "Message", "Trip" };
	int Numboftabs = 4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trip_detail_manager);

		toolbar = (Toolbar) findViewById(R.id.tool_bar);
		setSupportActionBar(toolbar);

		if (getSupportActionBar() != null) {
			// getSupportActionBar().setBackgroundDrawable(new
			// ColorDrawable(Color.parseColor("#0aae44")));
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}

		// Creating The Toolbar and setting it as the Toolbar for the activity

		// Creating The ViewPagerAdapter and Passing Fragment Manager, Titles
		// fot the Tabs and Number Of Tabs.
		adapter = new LichtrinhViewPagerAdapter(getSupportFragmentManager(), Titles, Numboftabs, getBaseContext());

		// Assigning ViewPager View and setting the adapter
		pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(adapter);

		// Assiging the Sliding Tab Layout View
		tabs = (LichTrinhSlidingTabLayout) findViewById(R.id.tabs);
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
		tabs.setCustomTabView(R.layout.custom_tab, 0);
		// Setting the ViewPager For the SlidingTabsLayout
		tabs.setViewPager(pager);

		// tabhost cũ
		/*
		 * LocalActivityManager lam = new LocalActivityManager(this, false);
		 * 
		 * tabHost = (TabHost) findViewById(android.R.id.tabhost);
		 * lam.dispatchCreate(savedInstanceState); tabHost.setup(lam);
		 * tabHost.getTabWidget().getLayoutParams().height = 80; // Tab for
		 * infoSpec = tabHost.newTabSpec("Thông tin"); infoSpec.setIndicator(
		 * "Thông tin", getResources().getDrawable(R.drawable.icon_info));
		 * Intent infoIntent = new Intent(this, TripDetailInfoActivity.class);
		 * infoSpec.setContent(infoIntent); tabHost.addTab(infoSpec);
		 * 
		 * // Tab for Member memberSpec = tabHost.newTabSpec("Member"); //
		 * setting Title and Icon for the Tab memberSpec.setIndicator("Member",
		 * getResources().getDrawable(R.drawable.icon_male)); Intent
		 * memberIntent = new Intent(this, TripDetailMemberActivity.class);
		 * memberSpec.setContent(memberIntent); tabHost.addTab(memberSpec);
		 * 
		 * // Tab for Message messageSpec = tabHost.newTabSpec("Member"); //
		 * setting Title and Icon for the Tab messageSpec.setIndicator("Member",
		 * getResources().getDrawable(R.drawable.icon_male)); Intent
		 * messageIntent = new Intent(this, TripDetailMemberActivity.class);
		 * messageSpec.setContent(memberIntent); tabHost.addTab(messageSpec);
		 * 
		 * // Tab for Message tripSpec = tabHost.newTabSpec("Member"); //
		 * setting Title and Icon for the Tab tripSpec.setIndicator("Member",
		 * getResources().getDrawable(R.drawable.icon_male)); Intent tripIntent
		 * = new Intent(this, TripDetailMemberActivity.class);
		 * tripSpec.setContent(memberIntent); tabHost.addTab(tripSpec);
		 */

		/*
		 * for (int i = 0; i < tabHost.getChildCount(); i++) {
		 * tabHost.getTabWidget().getChildAt(i).getLayoutParams().height = 30;
		 * 
		 * }
		 */
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.trip_detail_manager, menu);
		currentMenu = menu;
		currentMenu.getItem(0).setVisible(true);
		currentMenu.getItem(1).setVisible(false);
		return true;
	}

	private void saveAction(Menu menu) {
		currentMenu = menu;
		currentMenu.getItem(0).setVisible(false);
		currentMenu.getItem(1).setVisible(true);
		Toast.makeText(getBaseContext(), "" + R.string.actionSave, Toast.LENGTH_LONG).show();
	}

	private void UndoSaveAction(Menu menu) {
		currentMenu = menu;
		currentMenu.getItem(0).setVisible(true);
		currentMenu.getItem(1).setVisible(false);
		Toast.makeText(getBaseContext(), "" + R.string.actionUndoSave, Toast.LENGTH_LONG).show();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch (id) {
		case R.id.btnSaveTrip:
			saveAction(currentMenu);
			break;
		case R.id.btnUnSaveTrip:
			UndoSaveAction(currentMenu);
			break;
		case android.R.id.home:
			onBackPressed();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
