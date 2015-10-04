package com.hou.dulibu;

import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;

import com.hou.adapters.TripForUserViewPagerAdapter;
import com.hou.adapters.TripForUserViewPagerAdapter;
import com.hou.adapters.TripForUserViewPagerAdapter;
import com.hou.sliding_tab.TripForUserSlidingTabLayout;
import com.hou.sliding_tab.TripForUserSlidingTabLayout;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;

public class TripDetailManagerForUser extends ActionBarActivity {

	private Menu currentMenu;
	private TabHost tabHost;
	private TabSpec infoSpec, tripSpec;

	Toolbar toolbar;
	ViewPager pager;
	TripForUserViewPagerAdapter adapter;
	TripForUserSlidingTabLayout tabs;
	CharSequence Titles[] = { "Info", "Trip" };
	int Numboftabs = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trip_detail_manager_for_user);

		toolbar = (Toolbar) findViewById(R.id.trip_for_user_tool_bar);
		setSupportActionBar(toolbar);
		if (getSupportActionBar() != null) {
			getSupportActionBar().setDisplayShowCustomEnabled(true);
			getSupportActionBar().setBackgroundDrawable(
					new ColorDrawable(Color.parseColor("#0aae44")));
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}

		// Creating The Toolbar and setting it as the Toolbar for the activity

		// Creating The ViewPagerAdapter and Passing Fragment Manager, Titles
		// fot the Tabs and Number Of Tabs.
		adapter = new TripForUserViewPagerAdapter(getSupportFragmentManager(), Titles, Numboftabs, getBaseContext());

		// Assigning ViewPager View and setting the adapter
		pager = (ViewPager) findViewById(R.id.trip_for_user_pager);
		pager.setAdapter(adapter);
		// Assiging the Sliding Tab Layout View
		tabs = (TripForUserSlidingTabLayout) findViewById(R.id.trip_for_user_tabs);
		tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true,
										// This makes the tabs Space Evenly in
										// Available width

		// Setting Custom Color for the Scroll bar indicator of the Tab View
		tabs.setCustomTabColorizer(new TripForUserSlidingTabLayout.TabColorizer() {
			@Override
			public int getIndicatorColor(int position) {
				return getResources().getColor(R.color.StatusBarColor);
			}
		});

		tabs.setCustomTabView(R.layout.custom_tab, 0);//test
		// Setting the ViewPager For the SlidingTabsLayout
		tabs.setViewPager(pager);

		// tabhost cÅ©
		/*
		 * LocalActivityManager lam = new LocalActivityManager(this, false);
		 * 
		 * tabHost = (TabHost) findViewById(android.R.id.tabhost);
		 * lam.dispatchCreate(savedInstanceState); tabHost.setup(lam);
		 * tabHost.getTabWidget().getLayoutParams().height = 80; // Tab for
		 * infoSpec = tabHost.newTabSpec("ThÃ´ng tin"); infoSpec.setIndicator(
		 * "ThÃ´ng tin", getResources().getDrawable(R.drawable.icon_info));
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
		getMenuInflater().inflate(R.menu.trip_detail_manager_for_user_menu, menu);
		
		currentMenu = menu;
		currentMenu.getItem(0).setVisible(true);
		currentMenu.getItem(1).setVisible(false);
		return true;
	}

	private void saveAction(Menu menu){
		currentMenu = menu;
		currentMenu.getItem(0).setVisible(false);
		currentMenu.getItem(1).setVisible(true);
		Toast.makeText(getBaseContext(), "trip Ä‘Æ°á»£c thÃªm vÃ o Æ°a thÃ­ch", Toast.LENGTH_LONG).show();
	}
	private void UndoSaveAction(Menu menu){
		currentMenu = menu;
		currentMenu.getItem(0).setVisible(true);
		currentMenu.getItem(1).setVisible(false);
		Toast.makeText(getBaseContext(), "trip Ä‘Æ°á»£c loáº¡i khá»�i Æ°a thÃ­ch", Toast.LENGTH_LONG).show();
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		/*switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			break;
		}*/
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
