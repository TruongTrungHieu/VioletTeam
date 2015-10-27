package com.hou.fragment;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hou.app.Global;
import com.hou.dulibu.R;
import com.hou.dulibu.R.layout;
import com.hou.model.Nearby;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class TripDetailTripActivity extends Fragment implements
		OnMapReadyCallback, LocationListener {
	private ProgressDialog pDialog;
	ImageButton ivShareLocation;
	ImageView imgMapPlace, imgMapWarnning, imgMapTeam, imgMapHospital,
			imgMapGas;
	int status = 0; // 0b
	Boolean stSlide = true;
	private Location location;

	ArrayList<ImageView> lstImg;
	private ArrayList<Nearby> listHospital, listGas;
	// private ArrayList<Nearby> listWarning;

	private GoogleMap googleMap;
	private MapView mMapView;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.trip_detail_trip, container, false);
		listGas = new ArrayList<Nearby>();
		listHospital = new ArrayList<Nearby>();
		lstImg = new ArrayList<ImageView>();
		lstImg.add(imgMapPlace);
		lstImg.add(imgMapWarnning);
		lstImg.add(imgMapTeam);
		lstImg.add(imgMapHospital);
		lstImg.add(imgMapGas);

		ivShareLocation = (ImageButton) v.findViewById(R.id.ivShareLocation);
		imgMapPlace = (ImageView) v.findViewById(R.id.imgMapPlace);
		imgMapWarnning = (ImageView) v.findViewById(R.id.imgMapWarnning);
		imgMapTeam = (ImageView) v.findViewById(R.id.imgMapTeam);
		imgMapHospital = (ImageView) v.findViewById(R.id.imgMapHospital);
		imgMapGas = (ImageView) v.findViewById(R.id.imgMapGas);

		mMapView = (MapView) v.findViewById(R.id.mapView);
		mMapView.onCreate(savedInstanceState);
		mMapView.onResume();
		
		try {
			MapsInitializer.initialize(getActivity().getApplicationContext());
		} catch (Exception e) {
			e.printStackTrace();
		}
		googleMap = mMapView.getMap();
		googleMap.setMyLocationEnabled(true);

		// GPS when leave
		LocationManager locationManager = (LocationManager) getActivity()
				.getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		String bestProvider = locationManager.getBestProvider(criteria, true);
		location = locationManager.getLastKnownLocation(bestProvider);
		if (location != null) {
			onLocationChanged(location);
		}
		locationManager.requestLocationUpdates(bestProvider, 20000, 0, this);

		ivShareLocation.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			}
		});
		
		
		imgMapPlace.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				status ^= 16; // 1 << 4
				// mMap.clear();
				// onMapReady(mMap);
				if (((status >> 4) & 1) == 0) {
					imgMapPlace.setImageResource(R.drawable.map_place);
				} else {
					imgMapPlace.setImageResource(R.drawable.map_place1);
				}
				updateMarked();
			}
		});
		imgMapWarnning.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				status ^= 1;
				// mMap.clear();
				// onMapReady(mMap);
				if (((status >> 0) & 1) == 0) {
					imgMapWarnning.setImageResource(R.drawable.map_warnning);
				} else {
					imgMapWarnning.setImageResource(R.drawable.map_warnning1);
				}
				updateMarked();
			}
		});
		imgMapTeam.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				status ^= 4;
				// mMap.clear();
				// onMapReady(mMap);
				if (((status >> 2) & 1) == 0) {
					imgMapTeam.setImageResource(R.drawable.map_team);
				} else {
					imgMapTeam.setImageResource(R.drawable.map_team1);
				}
				updateMarked();
			}
		});
		imgMapHospital.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				status ^= 2;
				// mMap.clear();
				// onMapReady(mMap);
				if (((status >> 1) & 1) == 0) {
					imgMapHospital.setImageResource(R.drawable.map_hospital);
				} else {
					imgMapHospital.setImageResource(R.drawable.map_hospital1);
					getNearbyFromGoogle(Global.NEARBY_HOSPITAL);
				}
				updateMarked();
			}
		});
		imgMapGas.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				status ^= 8;
				// mMap.clear();
				// onMapReady(mMap);
				if (((status >> 3) & 1) == 0) {
					imgMapGas.setImageResource(R.drawable.map_gas);
				} else {
					imgMapGas.setImageResource(R.drawable.map_gas1);
					getNearbyFromGoogle(Global.NEARBY_GAS);
				}
				updateMarked();
			}
		});
		FixWidthBottom(imgMapPlace, imgMapWarnning, imgMapTeam, imgMapHospital,
				imgMapGas);

		return v;

	}

	public void CheckStatusBottom() {

		ArrayList<ImageView> lstImg = new ArrayList<ImageView>();

		lstImg.add(imgMapWarnning);
		lstImg.add(imgMapHospital);
		lstImg.add(imgMapTeam);
		lstImg.add(imgMapGas);
		lstImg.add(imgMapPlace);

		for (int i = lstImg.size() - 1; i >= 0; i--) {
			lstImg.get(i).setAlpha((((status >> i) & 1) == 0 ? 1f : 0.5f));
		}

	}

	// cÄƒn chá»‰nh khi thay Ä‘á»•i kÃ­ch thÆ°á»›c mÃ n hÃ¬nh
	public void FixWidthBottom(ImageView place, ImageView warnning,
			ImageView team, ImageView hospital, ImageView gas) {
		ArrayList<ImageView> arrIv = new ArrayList<ImageView>();
		arrIv.add(place);
		arrIv.add(warnning);
		arrIv.add(team);
		arrIv.add(hospital);
		arrIv.add(gas);
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay()
				.getMetrics(displaymetrics);
		int width = displaymetrics.widthPixels;

		if (true) {
			int t = (width - arrIv.size() * 60) / (6);
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
	private void updateMarked() {
		googleMap.clear();
		
		if (((status >> 4) & 1) == 1) {
			// place

		}
		if (((status >> 2) & 1) == 1) {
			// team
		}
		if (((status >> 1) & 1) == 1) {
			// hospital
			markerShow(listHospital);
		}
		if (((status >> 3) & 1) == 1) {
			// gas
			markerShow(listGas);
		}
		if (((status >> 0) & 1) == 1) {
			//warning
			
		}
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (pDialog != null) {
			pDialog.dismiss();
		}
		mMapView.onDestroy();
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
	public void onResume() {
		super.onResume();
		mMapView.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
		mMapView.onPause();
	}
	@Override
	public void onLowMemory() {
		super.onLowMemory();
		mMapView.onLowMemory();
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
				location.getLatitude(), location.getLongitude()), 13));
	}
	private void getNearbyFromGoogle(final String type) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		String url = Global.URL_NEARBY(location.getLatitude(),
				location.getLongitude(), Global.NEARBY_RADIUS, type);
		client.get(url, params, new AsyncHttpResponseHandler() {
			public void onSuccess(String response) {
				Log.e("getNearbyFromGoogle", response);
				saveNearby(type, response);
			}

			@Override
			public void onFailure(int statusCode, Throwable error,
					String content) {
				Toast.makeText(getActivity(),
						getString(R.string.register_error), Toast.LENGTH_LONG)
						.show();
			}
		});
	}
	private void saveNearby(String type, String response) {
		ArrayList<Nearby> list = new ArrayList<Nearby>();
		try {
			JSONObject nearby = new JSONObject(response);
			JSONArray results = nearby.getJSONArray("results");
			for (int i = 0; i < results.length(); ++i) {
				JSONObject result = results.getJSONObject(i);
				JSONObject geometry = result.getJSONObject("geometry");
				JSONObject location = geometry.getJSONObject("location");
				String lat = location.getString("lat");
				String lon = location.getString("lng");
				String name = result.getString("name");
				String vicinity = result.getString("vicinity");
				String icon = type;

				Nearby n = new Nearby(name, vicinity, Double.parseDouble(lat),
						Double.parseDouble(lon), icon);

				int id = getActivity().getResources().getIdentifier(
						n.getIcon(), "drawable",
						getActivity().getPackageName());
				googleMap.addMarker(new MarkerOptions()
						.position(new LatLng(n.getLat(), n.getLon()))
						.title(n.getTen()).snippet(n.getDiachi())
						.icon(BitmapDescriptorFactory.fromResource(id)));
				
				list.add(n);
			}

			if (type.equals(Global.NEARBY_GAS) && list.size() > 0) {
				listGas.clear();
				listGas.addAll(list);
			} else if (type.equals(Global.NEARBY_HOSPITAL) && list.size() > 0) {
				listHospital.clear();
				listHospital.addAll(list);
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void markerShow(ArrayList<Nearby> listNearby) {
		for (Nearby nearby : listNearby) {
			int id = getActivity().getResources().getIdentifier(
					nearby.getIcon(), "drawable",
					getActivity().getPackageName());
			googleMap.addMarker(new MarkerOptions()
					.position(new LatLng(nearby.getLat(), nearby.getLon()))
					.title(nearby.getTen()).snippet(nearby.getDiachi())
					.icon(BitmapDescriptorFactory.fromResource(id)));
		}
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
	public void onMapReady(GoogleMap arg0) {
		// TODO Auto-generated method stub
		updateMarked();
	}
}