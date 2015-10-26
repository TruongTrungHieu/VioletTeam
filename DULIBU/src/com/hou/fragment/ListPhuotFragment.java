package com.hou.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

public class ListPhuotFragment extends Fragment {
	
	private ProgressDialog pDialog;
	private ArrayList<Diemphuot> listPhuot;
	private GridView gv_phuot;

	private SwipeRefreshLayout swipeRefreshLayoutPhuot;
	private ExecuteQuery exeQ;
	private int p = 1;
	
	private DiemphuotAdapter adapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		exeQ = new ExecuteQuery(getActivity());
		exeQ.createDatabase();
		exeQ.open();
		
		listPhuot = new ArrayList<Diemphuot>();
		adapter = new DiemphuotAdapter(getActivity(), listPhuot);
		
		listPhuot = exeQ.getAllDiemphuot();
		gv_phuot.setAdapter(adapter);
		/*
		 * loadAnh(adapter, listPhuot);
		 */
		DeviceStatus ds = new DeviceStatus();
		String a = ds.getCurrentConnection(getActivity());
		if (a.equals("Wifi") || a.equals("3G")) {
			getPlacetoServer();
		}
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
				
				Bundle b = new Bundle();
				b.putString("maDiemPhuot", listPhuot.get(position)
						.getMaDiemphuot());
				b.putString("tenDiemPhuot", listPhuot.get(position)
						.getTenDiemphuot());
				b.putString("ghiChu", listPhuot.get(position).getGhichu());
				b.putInt("trangThaiChuan", listPhuot.get(position)
						.getTrangthaiChuan());
				b.putString("image", listPhuot.get(position).getImage());
				Log.e("OnClickViet","Link anh:" + listPhuot.get(position).getImage() );
				
				Intent intent = new Intent(getActivity(),
						PhuotDetailManager.class);
				intent.putExtra("myBundle",b);
				startActivity(intent);
			}
		});
		
		swipeRefreshLayoutPhuot = (SwipeRefreshLayout) view
				.findViewById(R.id.swipe_refresh_layout_phuot);
		swipeRefreshLayoutPhuot.setColorSchemeColors(R.color.StatusBarColor);
		swipeRefreshLayoutPhuot.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				Toast.makeText(getActivity(), "ok, refeshing",
						Toast.LENGTH_SHORT).show();
				DeviceStatus ds = new DeviceStatus();
				String a = ds.getCurrentConnection(getActivity());
				if (a.equals("Wifi") || a.equals("3G")) {
					getPlacetoServer();
				} else if (a.equals("NoInternetAccess")) {
					Toast.makeText(getActivity(),
							"No connection, try again later",
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
		// showSlideImage(SlideImageArr);
		//LoadGrid(view);
		gv_phuot.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				/*
				 * Toast.makeText(getActivity(), "TÃªn: " + ((Diemphuot)
				 * phuots.get(position)).getTenDiemphuot() + "\n" + "id áº£nh: "
				 * + ((Diemphuot) phuots.get(position)).getImage(),
				 * Toast.LENGTH_LONG).show();
				 */
				// Intent intent = new Intent(getActivity(),
				// PhuotDetailManager.class);
				// Bundle bundle = new Bundle();
				// bundle.putString("",);
				//ListPhuotFragment f = new ListPhuotFragment();
//				Bundle b = new Bundle();
//				b.putString("maDiemPhuot", phuots.get(position)
//						.getMaDiemphuot());
//				b.putString("tenDiemPhuot", phuots.get(position)
//						.getTenDiemphuot());
//				b.putString("ghiChu", phuots.get(position).getGhichu());
//				b.putInt("trangThaiChuan", phuots.get(position)
//						.getTrangthaiChuan());
//				b.putString("image", phuots.get(position).getImage());
//				b.putString("lat_diemphuot",phuots.get(position).getLat());
//				b.putString("lon_diemphuot",phuots.get(position).getLon());
//				Log.e("OnClickViet","Link anh:" +phuots.get(position).getImage() );
//				//f.setArguments(b);
//
//				
//				Intent intent = new Intent(getActivity(),
//						PhuotDetailManager.class);
//				intent.putExtra("myBundle",b);

				/*intent.putExtra("maDiemPhuot", phuots.get(position)
						.getMaDiemphuot());
				intent.putExtra("tenDiemPhuot", phuots.get(position)
						.getTenDiemphuot());
				intent.putExtra("ghiChu", phuots.get(position).getGhichu());
				intent.putExtra("trangThaiChuan", phuots.get(position)
						.getTrangthaiChuan());*/

				//startActivity(intent);
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

	public void getPlacetoServer() {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("p", p);
		client.get(Global.BASE_URI + "/" + Global.URI_GETPLACE_PATH, params,
				new AsyncHttpResponseHandler() {
					public void onSuccess(String response) {
						Log.e("getPlacetoServer", response);						
						listPlace(response);						
					}
					@Override
					public void onFailure(int statusCode, Throwable error,
							String content) {
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
				String note = placeLocationJson.optString("note");
				String lat = placeLocationJson.optString("lat") + "";
				String lon = placeLocationJson.optString("lon") + "";
				String name = placeLocationJson.optString("name");
				String image = placeLocationJson.optString("image");
				String address = placeLocationJson.optString("address");
				int verify = placeLocationJson.optInt("verify", 0);
				
				JSONObject objectLocation = placeLocationJson
						.getJSONObject("location");
				String id_city = objectLocation.optString("_id");
//				String lat_city = objectLocation.optString("lat");
//				String lon_city = objectLocation.optString("lon");
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

	private void loadAnh(ArrayList<Diemphuot> dps) {
		getImagePhuot gidp = new getImagePhuot(dps);
		gidp.execute();
		adapter.notifyDataSetChanged();
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
							imageOnServer.downloadFileFromServer(imageName,
									imageLink);
							Log.d("doInBackGroundListPhuot", "imageName: "
									+ imageName + "imageLink: " + imageLink);
							publishProgress();
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						Toast.makeText(getActivity(), "ImageLink: \n" + e,
								Toast.LENGTH_SHORT).show();
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

			Toast.makeText(getActivity(), "Ä�Ã£ xong: áº£nh",
					Toast.LENGTH_SHORT).show();
		}
	}

}
