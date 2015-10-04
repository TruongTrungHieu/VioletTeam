package com.hou.adapters;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.hou.dulibu.R;
import com.hou.model.Sukien;

public class SuKienAdapter extends ArrayAdapter<Sukien> implements
		OnMapReadyCallback {

	Activity context = null;
	ArrayList<Sukien> myArray = null;
	int layoutId;
	MapView gMapView;
	GoogleMap gMap = null;

	public SuKienAdapter(Activity context, int layoutId, ArrayList<Sukien> arr) {
		super(context, layoutId, arr);
		this.context = context;
		this.layoutId = layoutId;
		this.myArray = arr;
	}

	@SuppressLint("ViewHolder")
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = context.getLayoutInflater();
		convertView = inflater.inflate(layoutId, parent, false);

		if (myArray.size() > 0 && position >= 0) {

			final TextView tvTenSuKien = (TextView) convertView
					.findViewById(R.id.tvTenSuKien);
			final TextView tvThoiGian = (TextView) convertView
					.findViewById(R.id.tvThoiGian);

			gMapView = (MapView) convertView.findViewById(R.id.map);
			// gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			// gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new
			// LatLng(49.39,-124.83), 20));
			gMapView.onCreate(null);
			gMapView.onResume();
			gMapView.getMapAsync(this);

			/*
			 * LatLng latLng=new LatLng(-14.235004,-51.925280);
			 * map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));
			 */
			final Sukien sk = myArray.get(position);

			tvTenSuKien.setText(sk.getTenSukien());

			tvThoiGian.setText(sk.getThoigian());
		}

		return convertView;

	}

	@Override
	public void onMapReady(GoogleMap map) {
		gMap = map;
		/*
		 * gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		 * gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new
		 * LatLng(-14.235004,-51.925280), 20));
		 */
		 LatLng sydney = new LatLng(-34, 151);
		// // gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		// LatLng latLng = new LatLng(-14.235004, -51.925280);
		// // gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));
		// //gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
		// gMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

		gMap.clear();

		CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
				sydney, 10f);
		gMap.moveCamera(cameraUpdate);

	}
}
