package com.hou.fragment;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hou.adapters.DiemphuotAdapter;
import com.hou.app.Global;
import com.hou.database_handler.ExecuteQuery;
import com.hou.dulibu.DeviceStatus;
import com.hou.dulibu.PhuotDetailManager;
import com.hou.dulibu.R;
import com.hou.model.Diemphuot;
import com.hou.upload.imageOnServer;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.Toast;

public class ListPhuotFragment extends Fragment {

	private ProgressDialog pDialog;
	private ArrayList<Diemphuot> listPhuot;
	private GridView gv_phuot;

	private SwipeRefreshLayout swipeRefreshLayoutPhuot;
	private ExecuteQuery exeQ;

	private DiemphuotAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		exeQ = new ExecuteQuery(getActivity());
		exeQ.createDatabase();
		exeQ.open(); 

		listPhuot = new ArrayList<Diemphuot>();
		listPhuot = exeQ.getAllDiemphuot();

		adapter = new DiemphuotAdapter(getActivity(), listPhuot);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.list_phuot_manager,
				container, false);

		gv_phuot = (GridView) view.findViewById(R.id.gvPhuotGrid);
		gv_phuot.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {

				Context context = getActivity();
				com.hou.app.Global.savePreference(context, "maDiemPhuot",
						listPhuot.get(position).getMaDiemphuot());
				com.hou.app.Global.savePreference(context, "tenDiemPhuot",
						listPhuot.get(position).getTenDiemphuot());
				com.hou.app.Global.savePreference(context, "ghiChu", listPhuot
						.get(position).getGhichu());
				com.hou.app.Global.savePreference(context, "imagePhuot",
						listPhuot.get(position).getImage());
				com.hou.app.Global.savePreference(context, "lat_diemphuot",
						listPhuot.get(position).getLat());
				com.hou.app.Global.savePreference(context, "lon_diemphuot",
						listPhuot.get(position).getLon());
				com.hou.app.Global.savePreference(context, "trangThaiChuan",
						listPhuot.get(position).getTrangthaiChuan() + "");

				Intent intent = new Intent(getActivity(),
						PhuotDetailManager.class);
				startActivity(intent);
			}
		});

		gv_phuot.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if (firstVisibleItem + visibleItemCount >= totalItemCount) {
					int p = Global.getIntPreference(getActivity(),
							Global.PAGE_NUMBER, Global.PAGE_PHUOT_DEFAULT);
//					String lastDiemphuot = exeQ.getLastDiemphuot();
					getPlacetoServer(p);
				}
			}
		});

		gv_phuot.setAdapter(adapter);

		DeviceStatus ds = new DeviceStatus();
		String a = ds.getCurrentConnection(getActivity());
		if (a.equals("Wifi") || a.equals("3G")) {
			int p = Global.getIntPreference(getActivity(), Global.PAGE_NUMBER,
					Global.PAGE_PHUOT_DEFAULT);
			getPlacetoServer(p);
		}

		swipeRefreshLayoutPhuot = (SwipeRefreshLayout) view
				.findViewById(R.id.swipe_refresh_layout_phuot);
		swipeRefreshLayoutPhuot.setColorSchemeColors(R.color.StatusBarColor);
		swipeRefreshLayoutPhuot.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				Toast.makeText(getActivity().getBaseContext(), getString(R.string.pulltorefresh),
						Toast.LENGTH_SHORT).show();
				DeviceStatus ds = new DeviceStatus();
				String a = ds.getCurrentConnection(getActivity());
				if (a.equals("Wifi") || a.equals("3G")) {
					int p = Global.getIntPreference(getActivity(),
							Global.PAGE_NUMBER, Global.PAGE_PHUOT_DEFAULT);
					getPlacetoServer(p);
				} else if (a.equals("NoInternetAccess")) {
					Toast.makeText(getActivity(),
							"No connection. Please try again later !",
							Toast.LENGTH_SHORT).show();
				}
				Handler h = new Handler();
				h.postDelayed(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						swipeRefreshLayoutPhuot.setRefreshing(false);
					}
				}, 3000);
			}
		});
		return view;
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
		inflater.inflate(R.menu.fragment_list_phuot_menu, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();
		switch (id) {
		case R.id.addPhuotActionBar:
			break;
		case R.id.searchPhuot:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void getPlacetoServer(final int page) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		client.get(Global.BASE_URI + "/" + Global.URI_GETPLACE_PATH + "?p="
				+ page, params, new AsyncHttpResponseHandler() {
			public void onSuccess(String response) {
				Log.e("getPlacetoServer", response);
				if (!response.equals("[]")) {
					int p = Global.getIntPreference(getActivity(),
							Global.PAGE_NUMBER, Global.PAGE_PHUOT_DEFAULT);
					Global.saveIntPreference(getActivity(), Global.PAGE_NUMBER,
							p + 1);
					listPlace(response);
				}
			}

			@Override
			public void onFailure(int statusCode, Throwable error,
					String content) {
				Log.e("lỗi load", statusCode + ""+content);
				Toast.makeText(getActivity(), getString(R.string.efail),
						Toast.LENGTH_SHORT).show();
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

				JSONObject objectLocation = placeLocationJson
						.getJSONObject("location");
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
				listPhuot.add(place);

				exeQ.insert_tbl_diemphuot_single(place);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			Log.d("listPlace - listPhuotFragment", e.getMessage());
		}
		adapter.notifyDataSetChanged();
		loadAnh(listTemp);
	}

	private void loadAnh(ArrayList<Diemphuot> list) {
		for (Diemphuot dp : list) {
			getImagePhuot gidp = new getImagePhuot(dp);
			gidp.execute();
		}
	}

	public class getImagePhuot extends AsyncTask<Void, Void, Void> {

		Diemphuot dp;

		public getImagePhuot(Diemphuot phuot) {
			// TODO Auto-generated constructor stub
			this.dp = phuot;
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				String[] arr = dp.getImage().trim().split("/");
				String imageLink = dp.getImage();
				String imageName = arr[arr.length - 1];
				imageOnServer.downloadFileFromServer(imageName, imageLink);
				Log.d("doInBackGroundListPhuot", "imageName: " + imageName
						+ "imageLink: " + imageLink);
				publishProgress();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			adapter.notifyDataSetChanged();
		}
	}

}
