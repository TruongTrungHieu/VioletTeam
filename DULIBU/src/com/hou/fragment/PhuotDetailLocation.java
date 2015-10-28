package com.hou.fragment;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hou.adapters.MyInfoWindowAdapter;
import com.hou.dulibu.R;
import com.hou.dulibu.R.layout;
import com.hou.model.PhuotDetailComment;

import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class PhuotDetailLocation extends Fragment {
	private GoogleMap mMap;
	private MapView mMapView;
	private LatLng latlg;
	Double lat, lng;
	String diemphuot = "";

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
			@Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.phuot_detail_comment, container, false);
		mMapView = (MapView) v.findViewById(R.id.mapPhuotLocation);
		mMapView.onCreate(savedInstanceState);
		mMapView.onResume();// needed to get the map to display immediately

		Context context = getActivity().getApplicationContext();
		String latDiemPhuot = com.hou.app.Global.getPreference(context, "lat_diemphuot", "89");
		String lonDiemPhuot = com.hou.app.Global.getPreference(context, "lon_diemphuot", "98");
		String tenDiemPhuot = com.hou.app.Global.getPreference(context, "tenDiemPhuot", "Viet");
		String image = com.hou.app.Global.getPreference(context, "imagePhuot", "Viet");
		lat = Double.parseDouble(latDiemPhuot);
		lng = Double.parseDouble(lonDiemPhuot);
		diemphuot = com.hou.app.Global.getPreference(getActivity().getApplicationContext(), "tenDiemPhuot",
				"tenDiemPhuot");
		latlg = new LatLng(lat, lng);
		Log.e("aaaaa", com.hou.app.Global.getPreference(getActivity().getApplicationContext(), "lo_diemphuot", "Viet"));
		try {
			MapsInitializer.initialize(getActivity().getApplicationContext());
		} catch (Exception e) {
			e.printStackTrace();
		}
		mMap = mMapView.getMap();
		/*
		 * mMap.addMarker(new MarkerOptions() .position(latlg)
		 * .title(diemphuot)); CameraUpdate cameraUpdate =
		 * CameraUpdateFactory.newLatLngZoom( latlg, 11f);
		 * mMap.moveCamera(cameraUpdate); mMap.setMyLocationEnabled(true);
		 */
		mMap.addMarker(new MarkerOptions().position(latlg).title(tenDiemPhuot)
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_touch)).snippet(image));
		mMap.getUiSettings().setMyLocationButtonEnabled(true);
		mMap.setMyLocationEnabled(true);
		mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 7));
		mMap.setInfoWindowAdapter(new MyInfoWindowAdapter(inflater, R.layout.info_window_custom));
		return v;
	}
}