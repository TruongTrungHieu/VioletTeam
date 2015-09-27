package com.hou.fragment;

import com.hou.dulibu.R;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class ProfileFragment extends Fragment {
	private ProgressDialog pDialog;
	private Menu currentMenu;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater
				.inflate(R.layout.profile_manager, container, false);

		// showSlideImage(SlideImageArr);

		// initGridView(view);
		// showSlideImage(SlideImageArr);
		// initGridView(view);
		return view;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (pDialog != null) {
			pDialog.dismiss();
		}
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		if (pDialog != null) {
			pDialog.dismiss();
		}
	}
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		inflater.inflate(R.menu.fragment_profile, menu);
		currentMenu = menu;
		currentMenu.getItem(0).setVisible(true);
		currentMenu.getItem(1).setVisible(false);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();
		switch (id) {
		case R.id.editting_actionbar:
			currentMenu.getItem(0).setVisible(false);
			currentMenu.getItem(1).setVisible(true);
			Toast.makeText(getActivity(), "Hello", Toast.LENGTH_LONG).show();
		case R.id.done_setting_actionbar:
			currentMenu.getItem(0).setVisible(true);
			currentMenu.getItem(1).setVisible(false);
			Toast.makeText(getActivity(), "Hell", Toast.LENGTH_LONG).show();
		default:
			return super.onOptionsItemSelected(item);

		}
	}
}
