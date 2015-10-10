package com.hou.fragment;

import java.util.ArrayList;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hou.dulibu.R;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MapFragment extends android.support.v4.app.Fragment implements OnMapReadyCallback,
		LocationListener {
	private ProgressDialog pDialog;
	GoogleMap mMap;
	ImageView imgMapPlace, imgMapWarnning, imgMapTeam, imgMapHospital,
			imgMapGas;
	int status = 0; // 0b
	Boolean stSlide = true;

	ArrayList<ImageView> lstImg;

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
		View v = inflater.inflate(R.layout.maps_manager, container, false);

		lstImg = new ArrayList<ImageView>();
		lstImg.add(imgMapPlace);
		lstImg.add(imgMapWarnning);
		lstImg.add(imgMapTeam);
		lstImg.add(imgMapHospital);
		lstImg.add(imgMapGas);

		com.google.android.gms.maps.MapFragment mMapFragment;
		mMapFragment = com.google.android.gms.maps.MapFragment.newInstance();
		 FragmentTransaction fragmentTransaction =
		         getActivity().getFragmentManager().beginTransaction();
		 fragmentTransaction.add(R.id.map, mMapFragment);
		 fragmentTransaction.commit();
//		SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
//                .findFragmentById(R.id.mMap);
//		mapFragment.getMapAsync(this);
//		mMap = mapFragment.getMap();
//		mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//		mMap.getUiSettings().setMyLocationButtonEnabled(true);
//		mMap.setMyLocationEnabled(true);
//		mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(21.028860,
//				105.852330), 14));
//		// GPS when leave
//		LocationManager locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
//		Criteria criteria = new Criteria();
//		String bestProvider = locationManager.getBestProvider(criteria, true);
//		Location location = locationManager.getLastKnownLocation(bestProvider);
//		if (location != null) {
//			onLocationChanged(location);
//		}
//		locationManager.requestLocationUpdates(bestProvider, 20000, 0, this);

		imgMapPlace = (ImageView) v.findViewById(R.id.imgMapPlace);
		imgMapWarnning = (ImageView) v.findViewById(R.id.imgMapWarnning);
		imgMapTeam = (ImageView) v.findViewById(R.id.imgMapTeam);
		imgMapHospital = (ImageView) v.findViewById(R.id.imgMapHospital);
		imgMapGas = (ImageView) v.findViewById(R.id.imgMapGas);
		imgMapPlace.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				status ^= 16; // 1 << 4
//				mMap.clear();
//				onMapReady(mMap);
				if (((status >> 4) & 1) == 0) {
					imgMapPlace.setImageResource(R.drawable.map_place);
				}else {
					imgMapPlace.setImageResource(R.drawable.map_place1);
				}

			}
		});
		imgMapWarnning.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				status ^= 1;
//				mMap.clear();
//				onMapReady(mMap);
				if (((status >> 0)& 1) == 0) {
					imgMapWarnning.setImageResource(R.drawable.map_warnning);
				}else {
					imgMapWarnning.setImageResource(R.drawable.map_warnning1);
				}
			}
		});
		imgMapTeam.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				status ^= 4;
//				mMap.clear();
//				onMapReady(mMap);
				if (((status >> 2)& 1) == 0) {
					imgMapTeam.setImageResource(R.drawable.map_team);
				}else {
					imgMapTeam.setImageResource(R.drawable.map_team1);
				}

			}
		});
		imgMapHospital.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				status ^= 2;
//				mMap.clear();
//				onMapReady(mMap);
				if (((status >> 1)& 1) == 0) {
					imgMapHospital.setImageResource(R.drawable.map_hospital);
				}else {
					imgMapHospital.setImageResource(R.drawable.map_hospital1);
				}

			}
		});
		imgMapGas.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				status ^= 8;
//				mMap.clear();
//				onMapReady(mMap);
				if (((status >> 3)& 1) == 0) {
					imgMapGas.setImageResource(R.drawable.map_gas);
				}else {
					imgMapGas.setImageResource(R.drawable.map_gas1);
				}
			}
		});
		FixWidthBottom(imgMapPlace, imgMapWarnning, imgMapTeam, imgMapHospital, imgMapGas);
		
		return v;
		
	}
	
public void CheckStatusBottom() {
		
		ArrayList<ImageView> lstImg = new ArrayList<ImageView>();
		
		lstImg.add(imgMapWarnning);
		lstImg.add(imgMapHospital);
		lstImg.add(imgMapTeam);
		lstImg.add(imgMapGas);
		lstImg.add(imgMapPlace);
		
		
		
		for (int i = lstImg.size()-1; i>=0; i--) {
			lstImg.get(i).setAlpha((((status >> i) & 1) == 0 ? 1f : 0.5f) );
		}
		
	}

	// icon nào đang được chọn
	public void ImgIsSelected() {
		if (((status >> 4) & 1) == 1) {
			// place

		}
		if (((status >> 2) & 1) == 1) {
			//warnning
		}
		if (((status >> 1) & 1) == 1) {
			//team
		}
		if (((status >> 3) & 1) == 1) {
			//hospital
		}
		if (((status >> 0) & 1) == 1) {
			//gas
		}
	}

	// căn chỉnh khi thay đổi kích thước màn hình
	public void FixWidthBottom(ImageView place, ImageView warnning, ImageView team,
			ImageView hospital, ImageView gas) {
		Display display = getActivity().getWindowManager().getDefaultDisplay();
		ArrayList<ImageView> arrIv = new ArrayList<ImageView>();
		arrIv.add(place);
		arrIv.add(warnning);
		arrIv.add(team);
		arrIv.add(hospital);
		arrIv.add(gas);
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay()
				.getMetrics(displaymetrics);
		int height = displaymetrics.heightPixels;
		int width = displaymetrics.widthPixels;

		if (true) {
			int t = (width - arrIv.size()*60) / (6);
			int b = t;

			for (ImageView imageView : arrIv) {
				RelativeLayout.LayoutParams arrImageBottom = new RelativeLayout.LayoutParams(
						imageView.getLayoutParams());
				arrImageBottom.setMargins(t, 0, 0, 0);
				imageView.setLayoutParams(arrImageBottom);
				t = t + 60 + b;
			}
		}
	}
	private void removeFragmentMaps(){
		FragmentManager fm = getActivity().getFragmentManager();
	    android.app.Fragment fragment = (fm.findFragmentById(R.id.map));
	    FragmentTransaction ft = fm.beginTransaction();
	    ft.remove(fragment);
	    ft.commit();
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
		// inflater.inflate(R.menu.fragment_profile, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();
		return super.onOptionsItemSelected(item);

	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		double latitude = location.getLatitude();
		double longitude = location.getLongitude();
		LatLng latLng = new LatLng(latitude, longitude);
		mMap.addMarker(new MarkerOptions().position(latLng));
		mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMapReady(GoogleMap mMap) {
		// TODO Auto-generated method stub
		Toast.makeText(getActivity(), "Đây là map", Toast.LENGTH_LONG).show();
		ImgIsSelected();
		mMap.addMarker(new MarkerOptions()
        .position(new LatLng(0, 0))
        .title("Marker"));
	}
}
