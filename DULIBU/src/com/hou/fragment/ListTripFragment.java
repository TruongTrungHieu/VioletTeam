package com.hou.fragment;

import java.util.ArrayList;

import com.hou.adapters.LichtrinhAdapter;
import com.hou.dulibu.CreateTripManagerActivity;
import com.hou.dulibu.DeviceStatus;
import com.hou.dulibu.R;
import com.hou.dulibu.TripDetailManagerActivity;
import com.hou.dulibu.TripDetailManagerForUser;
import com.hou.model.Lichtrinh;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class ListTripFragment extends android.support.v4.app.Fragment {
	ProgressDialog pDialog;
	ArrayList<Lichtrinh> lichtrinh;
	ListView lvListTrip;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.listtrip_manager, container,
				false);
		final ListView lvListTrip = (ListView) view
				.findViewById(R.id.lvTripList);
		DeviceStatus ds = new DeviceStatus();

		Lichtrinh trip1 = new Lichtrinh("trip1", "Hà Nội - Hà Giang", "Hà Nội",
				"Hà Giang", "a", "1", "1", "1", "1", "1", "1", "1", 1, 1, "1",
				"image");
		lichtrinh = new ArrayList<Lichtrinh>();
		lichtrinh.add(trip1);
		LichtrinhAdapter adapter = new LichtrinhAdapter(getActivity(),
				R.layout.list_trip_item, lichtrinh);
		lvListTrip.setAdapter(adapter);
		lvListTrip
				.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub

						Toast.makeText(getActivity(),
								"" + lichtrinh.get(position).getMaLichtrinh(),
								Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(getActivity(),
								TripDetailManagerForUser.class);
						startActivity(intent);
					}
				});

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
		inflater.inflate(R.menu.fragment_list_trip, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();
		switch (id) {
		case R.id.addTripActionBar:
			Intent intent = new Intent(getActivity(), CreateTripManagerActivity.class);
			startActivity(intent);
		default:
			return super.onOptionsItemSelected(item);

		}
	}
}
