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


		adapter = new TripForUserViewPagerAdapter(getSupportFragmentManager(), Titles, Numboftabs, getBaseContext());

		pager = (ViewPager) findViewById(R.id.trip_for_user_pager);
		pager.setAdapter(adapter);
		tabs = (TripForUserSlidingTabLayout) findViewById(R.id.trip_for_user_tabs);
		tabs.setDistributeEvenly(true); 
		tabs.setCustomTabColorizer(new TripForUserSlidingTabLayout.TabColorizer() {
			@Override
			public int getIndicatorColor(int position) {
				return getResources().getColor(R.color.StatusBarColor);
			}
		});

		tabs.setCustomTabView(R.layout.custom_tab, 0);
		tabs.setViewPager(pager);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
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
		Toast.makeText(getBaseContext(), getString(R.string.actionSave), Toast.LENGTH_LONG).show();
	}
	private void UndoSaveAction(Menu menu){
		currentMenu = menu;
		currentMenu.getItem(0).setVisible(true);
		currentMenu.getItem(1).setVisible(false);
		Toast.makeText(getBaseContext(), getString(R.string.actionUndoSave), Toast.LENGTH_LONG).show();
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		switch (id) {
		case R.id.btnSaveTripForUser:
			saveAction(currentMenu);
			break;
		case R.id.btnUnSaveTripForUser:
			UndoSaveAction(currentMenu);
			break;
		case android.R.id.home:
			onBackPressed();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
