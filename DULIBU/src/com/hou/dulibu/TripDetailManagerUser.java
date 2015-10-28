package com.hou.dulibu;

import com.hou.adapters.TripForUserViewPagerAdapter;
import com.hou.sliding_tab.TripForUserSlidingTabLayout;

import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class TripDetailManagerUser extends ActionBarActivity {
	private Menu currentMenu;
	private TabHost tabHost;
	private TabSpec infoSpec, tripSpec;
	
	Toolbar toolbar;
	ViewPager pager;
	TripForUserViewPagerAdapter adapter;
	TripForUserSlidingTabLayout tabs;
	CharSequence Titles[] = { "Info", "Trip" };
	int Numboftabs = 2;
	
	public TripDetailManagerUser() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trip_detail_manager_for_user);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.trip_detail_manager_user, menu);
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
