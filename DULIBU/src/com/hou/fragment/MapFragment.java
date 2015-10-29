package com.hou.fragment;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hou.app.Global;
import com.hou.dulibu.ChangeMap;
import com.hou.dulibu.R;
import com.hou.model.Nearby;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Interpolator;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
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
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MapFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks,
		GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {
	private ProgressDialog pDialog;
	private ImageView imgMapWarnning, imgMapHospital, imgMapGas;
	private int status = 0;
	private LocationRequest locationRequest;
	private GoogleApiClient googleApiClient;
	private Location mLastLocation;
	String url = "";

	private ArrayList<ImageView> lstImg;
	private ArrayList<Nearby> listHospital, listGas;
	// private ArrayList<Nearby> listWarning;

	private GoogleMap googleMap;
	private MapView mMapView;
	double lat;
	double lon;
	private ImageButton iv;

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
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
		startTracking();

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
					imgMapWarnning.setImageResource(R.drawable.map_warnning1);
				} else {
					imgMapWarnning.setImageResource(R.drawable.map_warnning);
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
		iv = (ImageButton) v.findViewById(R.id.mapFragmentMapSetting);
		iv.setVisibility(View.VISIBLE);
		iv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialogChangeMapType(googleMap);
			}
		});
		return v;
	}

	private void dialogChangeMapType(GoogleMap gm) {
		android.support.v4.app.FragmentManager fm = getFragmentManager();
		ChangeMap cm = new ChangeMap(gm);
		// cm.setStyle(R.style.dialogFragment, R.style.dialogFragment);
		cm.show(fm, "Change map");
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
	public void FixWidthBottom(ImageView warnning, ImageView hospital, ImageView gas) {
		Display display = getActivity().getWindowManager().getDefaultDisplay();
		ArrayList<ImageView> arrIv = new ArrayList<ImageView>();
		arrIv.add(warnning);
		arrIv.add(hospital);
		arrIv.add(gas);
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
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
		if (location != null) {

			lat = location.getLatitude();
			lon = location.getLongitude();
		}
	}

	private void getNearbyFromGoogle(final String type) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		url = Global.URL_NEARBY(lat, lon, Global.NEARBY_RADIUS, type);
		client.get(url, params, new AsyncHttpResponseHandler() {
			public void onSuccess(String response) {
				Log.e("getNearbyFromGoogle", response);
				saveNearby(type, response);
			}

			@Override
			public void onFailure(int statusCode, Throwable error, String content) {
				Toast.makeText(getActivity(), getString(R.string.register_error), Toast.LENGTH_LONG).show();
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

				Nearby n = new Nearby(name, vicinity, Double.parseDouble(lat), Double.parseDouble(lon), icon);

				int id = getActivity().getResources().getIdentifier(n.getIcon(), "drawable",
						getActivity().getPackageName());
				googleMap.addMarker(new MarkerOptions().position(new LatLng(n.getLat(), n.getLon())).title(n.getTen())
						.snippet(n.getDiachi()).icon(BitmapDescriptorFactory.fromResource(id)));

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

	private void startTracking() {

		if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity()) == ConnectionResult.SUCCESS) {

			googleApiClient = new GoogleApiClient.Builder(getActivity()).addApi(LocationServices.API)
					.addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();

			if (!googleApiClient.isConnected() || !googleApiClient.isConnecting()) {
				googleApiClient.connect();
			}
		} else {
		}
	}

	private void markerShow(ArrayList<Nearby> listNearby) {
		for (Nearby nearby : listNearby) {
			int id = getActivity().getResources().getIdentifier(nearby.getIcon(), "drawable",
					getActivity().getPackageName());
			googleMap.addMarker(new MarkerOptions().position(new LatLng(nearby.getLat(), nearby.getLon()))
					.title(nearby.getTen()).snippet(nearby.getDiachi()).icon(BitmapDescriptorFactory.fromResource(id)));

		}

	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		stopLocationUpdates();
	}

	private void stopLocationUpdates() {
		if (googleApiClient != null && googleApiClient.isConnected()) {
			googleApiClient.disconnect();
		}
	}

	@Override
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub
		locationRequest = LocationRequest.create();
		locationRequest.setInterval(5000); // milliseconds
		locationRequest.setFastestInterval(5000); // the fastest rate in
													// milliseconds at which
													// your app can handle
													// location updates
		locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

		LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
		mLastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
		if (mLastLocation != null) {
			googleMap.animateCamera(CameraUpdateFactory
					.newLatLngZoom(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()), 13));
		}
	}

	@Override
	public void onConnectionSuspended(int arg0) {
		// TODO Auto-generated method stub

	}

}