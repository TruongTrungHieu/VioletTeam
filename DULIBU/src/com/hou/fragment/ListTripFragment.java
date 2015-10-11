package com.hou.fragment;

import java.util.ArrayList;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.hou.adapters.LichtrinhAdapter;
import com.hou.dulibu.CreateTripManagerActivity;
//import com.hou.dulibu.CreateTripManagerActivity;
import com.hou.dulibu.DeviceStatus;
import com.hou.dulibu.R;
import com.hou.dulibu.TripDetailManagerActivity;
import com.hou.dulibu.TripDetailManagerForUser;
import com.hou.dulibu.UserSecureConfirmManager;
import com.hou.model.Lichtrinh;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
		View view = inflater.inflate(R.layout.list_trip_manager, container,
				false);
		final ListView lvListTrip = (ListView) view
				.findViewById(R.id.lvTripList);
		DeviceStatus ds = new DeviceStatus();

		Lichtrinh trip1 = new Lichtrinh("trip1", "DulibuTeam's Trip", "Hà Nội",
				"Hà Giang", "01/05/2015", "03/05/2015", "1", "1", "1", "1", "1", "1", 1, 1, "1",
				"image");
		Lichtrinh trip2 = new Lichtrinh("trip2", "VioletTeam's Trip", "Hà Nội",
				"Hà Giang", "28/09/2015", "29/09/2015", "1", "1", "1", "1", "1", "1", 1, 1, "1",
				"image");
		lichtrinh = new ArrayList<Lichtrinh>();
		lichtrinh.add(trip1);
		lichtrinh.add(trip2);
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
								TripDetailManagerActivity.class);
						startActivity(intent);
					}
				});

		// showSlideImage(SlideImageArr);

		// initGridView(view);

		return view;

	}
	private void SearchTrip(){
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View alertLayout = inflater.inflate(R.layout.search_trip_dialog, null);
		AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
		alert.setView(alertLayout);
		alert.setCancelable(false);
		alert.setTitle("Tìm kiếm");
		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						return;
						
					}
				});

		alert.setPositiveButton("Tìm kiếm", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				return;
			}
		});
		AlertDialog dialog = alert.create();
		dialog.show();
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
			break;
		case R.id.searchTrip:
			SearchTrip();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
