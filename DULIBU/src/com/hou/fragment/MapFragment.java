package com.hou.fragment;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import com.hou.model.Nearby;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

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
import android.util.Log;
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

public class MapFragment extends Fragment implements OnMapReadyCallback,
		LocationListener {
	private ProgressDialog pDialog;
	private ImageView imgMapWarnning, imgMapHospital, imgMapGas;
	private int status = 0;
	private Location location;

	private ArrayList<ImageView> lstImg;
	private ArrayList<Nearby> listHospital, listGas;
	// private ArrayList<Nearby> listWarning;

	private GoogleMap googleMap;
	private MapView mMapView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);

		listGas = new ArrayList<Nearby>();
		listHospital = new ArrayList<Nearby>();
		// listWarning = new ArrayList<Nearby>();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.maps_manager, container, false);

		lstImg = new ArrayList<ImageView>();
		lstImg.add(imgMapWarnning);
		lstImg.add(imgMapHospital);
		lstImg.add(imgMapGas);

		mMapView = (MapView) v.findViewById(R.id.mapView);
		mMapView.onCreate(savedInstanceState);
		mMapView.onResume();// needed to get the map to display immediately

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

		imgMapWarnning = (ImageView) v.findViewById(R.id.imgMapWarnning);
		imgMapHospital = (ImageView) v.findViewById(R.id.imgMapHospital);
		imgMapGas = (ImageView) v.findViewById(R.id.imgMapGas);
		imgMapWarnning.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				status ^= 1;
				// mMap.clear();
				// onMapReady(mMap);
				if (((status >> 0) & 1) == 1) {
					imgMapWarnning.setImageResource(R.drawable.map_warnning);
				} else {
					imgMapWarnning.setImageResource(R.drawable.map_warnning1);
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
				if (((status >> 1) & 1) == 1) {
						imgMapHospital.setImageResource(R.drawable.map_hospital1);
						getNearbyFromGoogle(Global.NEARBY_HOSPITAL);
				} else {
					imgMapHospital.setImageResource(R.drawable.map_hospital);
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
				if (((status >> 3) & 1) == 1) {
						imgMapGas.setImageResource(R.drawable.map_gas1);
						getNearbyFromGoogle(Global.NEARBY_GAS);
				} else {
					imgMapGas.setImageResource(R.drawable.map_gas);
				}
				updateMarked();
			}
		});
		FixWidthBottom(imgMapWarnning, imgMapHospital, imgMapGas);
		return v;
	}

	private void updateMarked() {
		googleMap.clear();
		// warning
		if (((status >> 0) & 1) == 1) {

		}
		// gas
		if (((status >> 3) & 1) == 1) {
			markerShow(listGas);
		}
		// hospital
		if (((status >> 1) & 1) == 1) {
			markerShow(listHospital);
		}
	}

	public void CheckStatusBottom() {
		ArrayList<ImageView> lstImg = new ArrayList<ImageView>();

		lstImg.add(imgMapWarnning);
		lstImg.add(imgMapHospital);
		lstImg.add(imgMapGas);

		for (int i = lstImg.size() - 1; i >= 0; i--) {
			lstImg.get(i).setAlpha((((status >> i) & 1) == 0 ? 1f : 0.5f));
		}
	}

	@SuppressWarnings("unused")
	public void FixWidthBottom(ImageView warnning, ImageView hospital,
			ImageView gas) {
		Display display = getActivity().getWindowManager().getDefaultDisplay();
		ArrayList<ImageView> arrIv = new ArrayList<ImageView>();
		arrIv.add(warnning);
		arrIv.add(hospital);
		arrIv.add(gas);
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay()
				.getMetrics(displaymetrics);
		int height = displaymetrics.heightPixels;
		int width = displaymetrics.widthPixels;

		if (true) {
			int t = (width - arrIv.size() * 60) / (arrIv.size() + 1);
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

	@SuppressWarnings("unused")
	private void removeFragmentMaps() {
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
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		// inflater.inflate(R.menu.fragment_profile, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();
		switch (id) {

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
				location.getLatitude(), location.getLongitude()), 13));
	}

	@Override
	public void onMapReady(GoogleMap mMap) {
		// TODO Auto-generated method stub
		updateMarked();
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

}