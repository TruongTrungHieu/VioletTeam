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
import com.hou.app.Global;
import com.hou.dulibu.ChangeMap;
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
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class PhuotDetailLocation extends Fragment {
	private GoogleMap mMap;
	private MapView mMapView;
	private LatLng latlg;
	Double lat, lng;
	String diemphuot = "";
	private ImageButton iv;
	private Context c;
	private int map_type;
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
		
		map_type = Global.getIntPreference(context, "mapType", 0);
		if(map_type != 0){
			mMap.setMapType(map_type);
		}
		else{
			mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		}
		
		mMap.getUiSettings().setMyLocationButtonEnabled(true);
		mMap.setMyLocationEnabled(true);
		mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 7));
		mMap.setInfoWindowAdapter(new MyInfoWindowAdapter(inflater, R.layout.info_window_custom));
		
		c = getActivity().getApplicationContext();
		iv = (ImageButton) v.findViewById(R.id.phuotMapSetting);
		iv.setVisibility(View.VISIBLE);
		iv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialogChangeMapType(mMap, c, map_type);
			}
		});
		return v;
	}
	private void dialogChangeMapType(GoogleMap gm, Context c, int maptype) {
		android.support.v4.app.FragmentManager fm = getFragmentManager();
		ChangeMap cm = new ChangeMap(gm, c, maptype);
		// cm.setStyle(R.style.dialogFragment, R.style.dialogFragment);
		cm.show(fm, "Change map");
	}
}