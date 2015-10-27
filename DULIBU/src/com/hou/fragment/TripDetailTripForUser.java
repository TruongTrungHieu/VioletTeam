package com.hou.fragment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hou.dulibu.DeviceStatus;
import com.hou.dulibu.R;
import com.hou.model.Diemphuot;
import com.hou.model.Nearby;
import com.hou.model.Tinh_Thanhpho;
import com.hou.ultis.ImageUltiFunctions;
import com.hou.upload.imageOnServer;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hou.adapters.MyArrayAdapterPlace;
import com.hou.app.Global;
import com.hou.database_handler.ExecuteQuery;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelSlideListener;

public class TripDetailTripForUser extends Fragment {
	// setHasOptionsMenu(true);
	private MyArrayAdapterPlace adapter;
	// ArrayList<MyPlace> lstPlace;
	private ArrayList<Diemphuot> lstPlace, listPlace;
	private SlidingUpPanelLayout mLayout;
	ImageView imgSlide;
	private ExecuteQuery exeQ;
	private int p = 1;
	private GoogleMap googleMap;
	private MapView mMapView;
	private Tinh_Thanhpho startPlace, endPlace;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		exeQ = new ExecuteQuery(getActivity());
		exeQ.createDatabase();
		exeQ.open();

		lstPlace = new ArrayList<Diemphuot>();
		lstPlace = exeQ.getAllDiemphuot();

		adapter = new MyArrayAdapterPlace(getActivity(), R.layout.list_row_slide, lstPlace);

		/*
		 * loadAnh(adapter, listPhuot);
		 */

	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
			@Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.trip_detail_trip_for_user, container, false);

		imgSlide = (ImageView) v.findViewById(R.id.imgSlide);
		final ListView lvSlide = (ListView) v.findViewById(R.id.lvSlide);

		DeviceStatus ds = new DeviceStatus();
		String a = ds.getCurrentConnection(getActivity());
		if (a.equals("Wifi") || a.equals("3G")) {
			getPlacetoServer();
		}
		adapter = new MyArrayAdapterPlace(getActivity(), R.layout.list_row_slide, lstPlace);
		lvSlide.setAdapter(adapter);

		mMapView = (MapView) v.findViewById(R.id.map);
		mMapView.onCreate(savedInstanceState);
		mMapView.onResume();
		try {
			MapsInitializer.initialize(getActivity().getApplicationContext());
		} catch (Exception e) {
			e.printStackTrace();
		}
		googleMap = mMapView.getMap();

		/*
		 * startPlace = exeQ.getTinhByTentinh(spStartPlace.getSelectedItem()
		 * .toString()); endPlace =
		 * exeQ.getTinhByTentinh(spEndPlace.getSelectedItem() .toString());
		 */
		startPlace = exeQ.getTinhByTentinh(getString(R.string.Hanoi));
		endPlace = exeQ.getTinhByTentinh(getString(R.string.Hagiang));
		listPlace = exeQ.getAllDiemphuotBy2MaTinh(startPlace.getMaTinh(), endPlace.getMaTinh());

		googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		googleMap.getUiSettings().setMyLocationButtonEnabled(true);
		googleMap.setMyLocationEnabled(true);
		googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
				new LatLng(Double.parseDouble(startPlace.getLat()), Double.parseDouble(startPlace.getLon())), 7));

		showMakerFirst();
		// googleMap.setInfoWindowAdapter(new MyInfoWindowAdapter(inflater));

		mLayout = (SlidingUpPanelLayout) v.findViewById(R.id.sliding_layout);
		mLayout.setPanelSlideListener(new PanelSlideListener() {
			@Override
			public void onPanelSlide(View panel, float slideOffset) {
				// Log.i(TAG, "onPanelSlide, offset " + slideOffset);
			}

			@Override
			public void onPanelExpanded(View panel) {
				// Log.i(TAG, "onPanelExpanded");
				imgSlide.setImageResource(R.drawable.slide_down);
			}

			@Override
			public void onPanelCollapsed(View panel) {
				// Log.i(TAG, "onPanelCollapsed");
				imgSlide.setImageResource(R.drawable.slide_up);
			}

			@Override
			public void onPanelAnchored(View panel) {
				// Log.i(TAG, "onPanelAnchored");
			}

			@Override
			public void onPanelHidden(View panel) {
				// Log.i(TAG, "onPanelHidden");
			}
		});

		return v;
	}

	private void showMakerFirst() {
		// End
		googleMap
				.addMarker(new MarkerOptions()
						.position(new LatLng(Double.parseDouble(startPlace.getLat()),
								Double.parseDouble(startPlace.getLon())))
						.title(startPlace.getTenTinh())
						.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_start_end_places))
						.snippet(startPlace.getMaTinh()));

		// Start
		googleMap
				.addMarker(new MarkerOptions()
						.position(new LatLng(Double.parseDouble(endPlace.getLat()),
								Double.parseDouble(endPlace.getLon())))
						.title(endPlace.getTenTinh())
						.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_start_end_places))
						.snippet(endPlace.getTenTinh()));

		// Diemphuot thuoc Start + end
		for (Diemphuot dp : listPlace) {
			googleMap
					.addMarker(
							new MarkerOptions()
									.position(new LatLng(Double.parseDouble(dp.getLat()),
											Double.parseDouble(dp.getLon())))
									.title(dp.getTenDiemphuot())
									.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_touch))
									.snippet(dp.getDiachi()));
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

	public void getPlacetoServer() {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		client.get(Global.BASE_URI + "/" + Global.URI_GETPLACE_PATH + "?p=" + p, params,
				new AsyncHttpResponseHandler() {
					public void onSuccess(String response) {
						Log.e("getPlacetoServer", response);
						listPlace(response);
					}

					@Override
					public void onFailure(int statusCode, Throwable error, String content) {
						Toast.makeText(getActivity(), getString(R.string.e503), Toast.LENGTH_SHORT).show();
					}
				});
	}

	private void listPlace(String response) {
		ArrayList<Diemphuot> listTemp = new ArrayList<Diemphuot>();
		try {
			JSONArray arrObj = new JSONArray(response);
			for (int i = 0; i < arrObj.length(); i++) {
				JSONObject placeLocationJson = arrObj.getJSONObject(i);
				Diemphuot place = new Diemphuot();

				String _id = placeLocationJson.optString("_id");
				String note = placeLocationJson.optString("note", "");
				String lat = placeLocationJson.optString("lat");
				String lon = placeLocationJson.optString("lon");
				String name = placeLocationJson.optString("name");
				String image = placeLocationJson.optString("image");
				String address = placeLocationJson.optString("address");
				int verify = placeLocationJson.optInt("verify", 0);

				JSONObject objectLocation = placeLocationJson.getJSONObject("location");
				String id_city = objectLocation.optString("_id");
				// String lat_city = objectLocation.optString("lat");
				// String lon_city = objectLocation.optString("lon");
				String name_city = objectLocation.optString("name");

				if (address.equals("") || address == null) {
					address = name_city;
				}

				place.setMaDiemphuot(_id);
				place.setGhichu(note);
				place.setLat(lat);
				place.setLon(lon);
				place.setTenDiemphuot(name);
				place.setImage(image);
				place.setDiachi(address);
				place.setTrangthaiChuan(verify);
				place.setMaTinh(id_city);

				listTemp.add(place);
				lstPlace.add(place);

				exeQ.insert_tbl_diemphuot_single(place);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			Log.d("listPlace - listPhuotFragment", e.getMessage());
		}
		adapter.notifyDataSetChanged();
		loadAnh(listTemp);
	}

	private void loadAnh(ArrayList<Diemphuot> dps) {
		getImagePhuot gidp = new getImagePhuot(dps);
		gidp.execute();
	}

	public class getImagePhuot extends AsyncTask<Void, Void, Void> {

		ArrayList<Diemphuot> arr;

		public getImagePhuot(ArrayList<Diemphuot> phuots) {
			// TODO Auto-generated constructor stub
			this.arr = phuots;
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			if (arr.size() > 0) {
				for (int i = 0; i < arr.size(); i++) {
					Diemphuot dp = arr.get(i);
					try {
						String imageLink = dp.getImage();
						String imageName;
						if (imageLink.length() > 41) {
							imageName = imageLink.substring(41);
							imageOnServer.downloadFileFromServer(imageName, imageLink);
							Log.d("doInBackGroundListPhuot", "imageName: " + imageName + "imageLink: " + imageLink);
							publishProgress();
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						Toast.makeText(getActivity(), "ImageLink: \n" + e, Toast.LENGTH_SHORT).show();
						e.printStackTrace();
					}
				}
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
			adapter.notifyDataSetChanged();
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}
	}

	class MyInfoWindowAdapter implements InfoWindowAdapter {

		private final View myContentsView;
		LayoutInflater li;

		MyInfoWindowAdapter(LayoutInflater li) {
			myContentsView = li.inflate(R.layout.info_window_custom, null);
		}

		@Override
		public View getInfoContents(Marker marker) {

			TextView tvTitle = ((TextView) myContentsView.findViewById(R.id.customIWtitle));
			tvTitle.setText(marker.getTitle());
			ImageView iv = ((ImageView) myContentsView.findViewById(R.id.customIWimage));
			String imageLink = marker.getSnippet();
			String imageName;
			if (imageLink.length() > 41) {
				imageName = imageLink.substring(41);
				File f = ImageUltiFunctions.getFileFromUri(Global.getURI(imageName));
				if (f != null) {
					Bitmap b = ImageUltiFunctions.decodeSampledBitmapFromFile(f, 500, 500);
					iv.setImageBitmap(b);
				} else {
					iv.setImageResource(R.drawable.trip1);
				}
			} else {
				iv.setImageResource(R.drawable.trip1);
			}

			return myContentsView;
		}

		@Override
		public View getInfoWindow(Marker marker) {
			// TODO Auto-generated method stub
			return null;
		}

	}

}
