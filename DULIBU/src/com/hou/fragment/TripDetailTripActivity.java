package com.hou.fragment;

import io.socket.emitter.Emitter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.ls.LSSerializer;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;

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
import com.hou.dulibu.R.layout;
import com.hou.dulibu.TripDetailManagerActivity;
import com.hou.fragment.TripDetailMemberActivity.getImage;
import com.hou.gps.GetLocationService;
import com.hou.model.InfoTracking;
import com.hou.model.LichtrinhMember;
import com.hou.model.Nearby;
import com.hou.model.User;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.ActivityManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class TripDetailTripActivity extends Fragment implements
		GoogleApiClient.ConnectionCallbacks,
		GoogleApiClient.OnConnectionFailedListener,
		com.google.android.gms.location.LocationListener {
	private ProgressDialog pDialog;
	private LocationRequest locationRequest;
	private GoogleApiClient googleApiClient;
	ImageButton ivShareLocation, ivStopShareLocation, ivMapChange;
	ImageView imgMapPlace, imgMapWarnning, imgMapTeam, imgMapHospital,
			imgMapGas;
	private Location mLastLocation;
	int status = 0; // 0b
	TextView tvShare;
	Boolean stSlide = true;
	ProgressBar mProgressBar;
	String url = "";
	ArrayList<ImageView> lstImg;
	private ArrayList<Nearby> listHospital, listGas;
	private ArrayList<InfoTracking> lstInforTracking;
	ArrayList<LichtrinhMember> arrListMember;
	// private ArrayList<Nearby> listWarning;

	private GoogleMap googleMap;
	private MapView mMapView;
	double lat;
	double lon;
	int map_type;
	Context context;

	private Map<String, InfoTracking> userTrackers;

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
		tvShare = (TextView) v.findViewById(R.id.tvShare);
		startTracking();
		arrListMember = new ArrayList<LichtrinhMember>();
		lstInforTracking = new ArrayList<InfoTracking>();

		mProgressBar = (ProgressBar) v.findViewById(R.id.pb);
		mProgressBar.setVisibility(View.GONE);
		ivShareLocation = (ImageButton) v.findViewById(R.id.ivShareLocation);
		ivStopShareLocation = (ImageButton) v
				.findViewById(R.id.ivStopShareLocation);
		imgMapPlace = (ImageView) v.findViewById(R.id.imgMapPlace);
		imgMapWarnning = (ImageView) v.findViewById(R.id.imgMapWarnning);
		imgMapTeam = (ImageView) v.findViewById(R.id.imgMapTeam);
		imgMapHospital = (ImageView) v.findViewById(R.id.imgMapHospital);
		imgMapGas = (ImageView) v.findViewById(R.id.imgMapGas);

		mMapView = (MapView) v.findViewById(R.id.mapView);
		mMapView.onCreate(savedInstanceState);
		mMapView.onResume();

		userTrackers = new HashMap<>();

		Global.getSocketServer(getActivity()).on("tracking",
				new Emitter.Listener() {

					@Override
					public void call(Object... arg0) {
						// TODO Auto-generated method stub
						getMemberTracking(arg0[0]);

					}
				});

		if (Global.isServiceRunning(getActivity(),
				getActivity().ACTIVITY_SERVICE)) {
			ivShareLocation.setVisibility(View.INVISIBLE);
			ivStopShareLocation.setVisibility(View.VISIBLE);
		}

		try {
			MapsInitializer.initialize(getActivity().getApplicationContext());
		} catch (Exception e) {
			e.printStackTrace();
		}
		googleMap = mMapView.getMap();
		googleMap.setMyLocationEnabled(true);
		
		context = getActivity().getApplicationContext();
		map_type = Global.getIntPreference(context, "mapType", 0);
		if (map_type != 0) {
			googleMap.setMapType(map_type);
		} else {
			googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		}
		
		ivMapChange = (ImageButton) v.findViewById(R.id.tripMemberMapSetting);
		ivMapChange.setVisibility(View.VISIBLE);
		ivMapChange.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialogChangeMapType(googleMap, context, map_type);
			}
		});
		
		ivStopShareLocation.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ivStopShareLocation.setVisibility(View.INVISIBLE);
				ivShareLocation.setVisibility(View.VISIBLE);

				mProgressBar.setVisibility(View.VISIBLE);

				Global.StopServiceGetLocation(getActivity(), new Intent(
						getActivity(), GetLocationService.class));

				(new Handler()).postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						mProgressBar.setVisibility(View.GONE);
					}

				}, 3000);
			}
		});
		ivShareLocation.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ivShareLocation.setVisibility(View.INVISIBLE);
				ivStopShareLocation.setVisibility(View.VISIBLE);

				mProgressBar.setVisibility(View.VISIBLE);
				tvShare.setVisibility(View.VISIBLE);
				
					String user_id = Global.getPreference(getActivity(), Global.USER_MAUSER, "+++");

					if (!userTrackers.containsKey(user_id)) {
						String target_id = Global.getPreference(getActivity(), Global.TRIP_TRIP_ID, "0");
						String target_type = "TRIP";

						String user_fullname = Global.getPreference(getActivity(), Global.USER_FULLNAME, "invalid");
						String avatar = Global.getPreference(getActivity(), Global.USER_AVATAR, "");

						InfoTracking infoTracking = new InfoTracking(target_id,
								target_type, user_id, user_fullname, avatar, lat, lon);

						userTrackers.put(user_id, infoTracking);
					} else {
						userTrackers.get(user_id).setLat(lat);
						userTrackers.get(user_id).setLon(lon);
					}

					Log.d("update_tracking_socket", lat + "," + lon + "uid = "
							+ user_id);

					updateTracking(user_id);
					
				
				

				Global.StartServiceGetLocation(getActivity(), new Intent(
						getActivity(), GetLocationService.class));

				(new Handler()).postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						mProgressBar.setVisibility(View.GONE);

						tvShare.setVisibility(View.GONE);
					}
				}, 3000);

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
		// FixWidthBottom(imgMapPlace, imgMapWarnning, imgMapTeam,
		// imgMapHospital,
		// imgMapGas);

		return v;

	}
	private void dialogChangeMapType(GoogleMap gm, Context c, int maptype) {
		FragmentManager fm = getFragmentManager();
		ChangeMap cm = new ChangeMap(gm, c, maptype);
		// cm.setStyle(R.style.dialogFragment, R.style.dialogFragment);
		cm.show(fm, "Change map");
	}

	// public void CheckStatusBottom() {
	//
	// ArrayList<ImageView> lstImg = new ArrayList<ImageView>();
	//
	// lstImg.add(imgMapWarnning);
	// lstImg.add(imgMapHospital);
	// lstImg.add(imgMapTeam);
	// lstImg.add(imgMapGas);
	// lstImg.add(imgMapPlace);
	//
	// for (int i = lstImg.size() - 1; i >= 0; i--) {
	// lstImg.get(i).setAlpha((((status >> i) & 1) == 0 ? 1f : 0.5f));
	// }
	// }

	// public void FixWidthBottom(ImageView place, ImageView warnning,
	// ImageView team, ImageView hospital, ImageView gas) {
	// ArrayList<ImageView> arrIv = new ArrayList<ImageView>();
	// arrIv.add(place);
	// arrIv.add(warnning);
	// arrIv.add(team);
	// arrIv.add(hospital);
	// arrIv.add(gas);
	// DisplayMetrics displaymetrics = new DisplayMetrics();
	// getActivity().getWindowManager().getDefaultDisplay()
	// .getMetrics(displaymetrics);
	// int width = displaymetrics.widthPixels;
	//
	// if (true) {
	// int t = (width - arrIv.size() * 60) / (6);
	// int b = t;
	//
	// for (ImageView imageView : arrIv) {
	// RelativeLayout.LayoutParams arrImageBottom = new
	// RelativeLayout.LayoutParams(
	// imageView.getLayoutParams());
	// arrImageBottom.setMargins(t, 0, 0, 0);
	// imageView.setLayoutParams(arrImageBottom);
	// t = t + 60 + b;
	// }
	// }
	// }

	private void updateMarked() {
		googleMap.clear();

		if (((status >> 4) & 1) == 1) {
			// place

		}
		if (((status >> 2) & 1) == 1) {
			// team
			// markerGps(lstTracking);
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
			// warning

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

				int id = getActivity().getResources()
						.getIdentifier(n.getIcon(), "drawable",
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

//	private void markerGps(final Map<String, InfoTracking> userTrackers) {
//		getActivity().runOnUiThread(new Runnable() {
//
//			@Override
//			public void run() {
//				if (googleMap == null) {
//					googleMap = mMapView.getMap();
//				} else {
//
////					googleMap.clear();
//					if (userTrackers.size() > 0) {
//						for (Map.Entry<String, InfoTracking> entry : userTrackers
//								.entrySet()) {
//							InfoTracking user = entry.getValue();
//							Log.d("lat + lon", ""+user.getLat()+ user
//													.getLon());
//
////							int id = getActivity()
////									.getResources()
////									.getIdentifier(
////											user.getAvatar(),
////											Environment
////													.getExternalStorageDirectory()
////													+ "/" + "DULIBU",
////											getActivity().getPackageName());
//							googleMap.addMarker(new MarkerOptions()
//									.position(
//											new LatLng(user.getLat(), user
//													.getLon()))
//									.title(user.getUser_fullname()));
//							
//						mMapView.postInvalidate();
//						}
//					}
//				}
//			}
//		});
//
//	}

	private void startTracking() {

		if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity()) == ConnectionResult.SUCCESS) {

			googleApiClient = new GoogleApiClient.Builder(getActivity())
					.addApi(LocationServices.API).addConnectionCallbacks(this)
					.addOnConnectionFailedListener(this).build();

			if (!googleApiClient.isConnected()
					|| !googleApiClient.isConnecting()) {
				googleApiClient.connect();
			}
		} else {
		}
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		if (location != null) {
			// url = Global.URL_NEARBY(location.getLatitude(),
			// location.getLongitude(), Global.NEARBY_RADIUS, type);

			lat = location.getLatitude();
			lon = location.getLongitude();
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

		LocationServices.FusedLocationApi.requestLocationUpdates(
				googleApiClient, locationRequest, this);
		mLastLocation = LocationServices.FusedLocationApi
				.getLastLocation(googleApiClient);
		if (mLastLocation != null) {
			googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
					new LatLng(mLastLocation.getLatitude(), mLastLocation
							.getLongitude()), 13));
		}

	}

	@Override
	public void onConnectionSuspended(int arg0) {
		// TODO Auto-generated method stub

	}

	private void getMemberTracking(Object resposive) {
		try {
			JSONObject data = (JSONObject) resposive;
			String user_id = data.getJSONObject("sender").optString("_id");

			double lat = data.optDouble("lat");
			double lon = data.optDouble("lon");

			if (!userTrackers.containsKey(user_id)) {
				String target_id = data.optString("target_id");
				String target_type = data.optString("target_type");

				String user_fullname = data.getJSONObject("sender").optString(
						"fullname");
				String avatar = data.getJSONObject("sender")
						.optString("avatar");

				InfoTracking infoTracking = new InfoTracking(target_id,
						target_type, user_id, user_fullname, avatar, lat, lon);

				userTrackers.put(user_id, infoTracking);
			} else {
				userTrackers.get(user_id).setLat(lat);
				userTrackers.get(user_id).setLon(lon);
			}

			Log.d("update_tracking_socket", lat + "," + lon + "uid = "
					+ user_id);

			updateTracking(user_id);
			// lstInforTracking.add(infoTracking);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	//
	// ////
	// private void convertToMap() {
	// AsyncHttpClient client = new AsyncHttpClient();
	// RequestParams params = new RequestParams();
	// client.get(Global.BASE_URI, params, new AsyncHttpResponseHandler() {
	// @Override
	// public void onSuccess(int statusCode, String content) {
	// // TODO Auto-generated method stub
	// JSONArray json = new JSONArray(content);
	// }
	//
	// @Override
	// @Deprecated
	// public void onFailure(Throwable error, String content) {
	// // TODO Auto-generated method stub
	// super.onFailure(error, content);
	// }
	// });
	// }
	//
	// //////

	// private void showUserTrackers() {
	// for (Map.Entry<String, InfoTracking> entry : userTrackers.entrySet()) {
	// InfoTracking user = entry.getValue();
	// // new marker
	// updateTracking(user.getUser_id(), new LatLng(user.getLat(),
	// user.getLon()));
	// }
	// }

	/**
	 * Cáº­p nháº­t location cho ngÆ°á»�i cÃ³ mÃ£ user key lÃªn báº£n Ä‘á»“
	 * 
	 * @param key
	 * @param latLng
	 */
	private void updateTracking(final String key) {

		if (getActivity() == null) {
			return;
		}
		getActivity().runOnUiThread(new Runnable() {

			@Override
			public void run() {

				// TODO Auto-generated method stub
				if (userTrackers.containsKey(key)) {
					InfoTracking tracker = userTrackers.get(key);
					LatLng ll = new LatLng(tracker.getLat(), tracker.getLon());
					Marker marker = null;
					// new user ?
					 if (tracker.getMarker() == null) {
					marker = googleMap.addMarker(new MarkerOptions().title(
							tracker.getUser_fullname()).position(ll));
					tracker.setMarker(marker);
					Log.d("update_tracking_socket new", lat + "," + lon
							+ "uid = " + key);
					 } else {
						 Log.d("update_tracking_socket old", lat + "," + lon +
						 "uid = " + key);
						 marker = tracker.getMarker();
						 marker.setPosition(ll);
					 }
					 
				}
			}
		});

	}

}