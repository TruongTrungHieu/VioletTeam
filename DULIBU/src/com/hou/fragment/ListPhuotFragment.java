package com.hou.fragment;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hou.adapters.DiemphuotAdapter;
import com.hou.adapters.LichtrinhViewPagerAdapter;
import com.hou.app.Global;
import com.hou.database_handler.ExecuteQuery;
import com.hou.dulibu.DeviceStatus;
import com.hou.dulibu.R;
import com.hou.model.Diemphuot;
import com.hou.sliding_tab.LichTrinhSlidingTabLayout;
import com.hou.upload.imageOnServer;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

public class ListPhuotFragment extends Fragment {
	ProgressDialog pDialog;
	ArrayList<Diemphuot> phuots, phuotsTemp;
	ListView lvListTrip;
	GridView gv_phuot;

	LichTrinhSlidingTabLayout tabs;
	// Toolbar toolbar;
	LichtrinhViewPagerAdapter adapter;
	private SwipeRefreshLayout swipeRefreshLayoutPhuot;
	ViewPager pager;
	CharSequence Titles[] = { "TÃ¡ÂºÂ¥t cÃ¡ÂºÂ£", "MÃ¡Â»â€ºi", "TÃ¡Â»â€°nh-TP", "Ã„ï¿½Ã¡Â»â„¢ khÃƒÂ³" };
	int Numboftabs = 4;
	private ExecuteQuery exeQ;
	private int p = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// toolbar = (Toolbar) findViewById(R.id.tool_bar);
		// setSupportActionBar(toolbar);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.list_phuot_manager, container, false);
		swipeRefreshLayoutPhuot = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout_phuot);
		swipeRefreshLayoutPhuot.setColorSchemeColors(R.color.StatusBarColor);
		swipeRefreshLayoutPhuot.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				Toast.makeText(getActivity(), "ok, refeshing", Toast.LENGTH_SHORT).show();
				DeviceStatus ds = new DeviceStatus();
				String a = ds.getCurrentConnection(getActivity());
				if (a.equals("Wifi") || a.equals("3G")) {
					getPlacetoServer();
					LoadGrid(view);
				} else if (a.equals("NoInternetAccess")) {
					Toast.makeText(getActivity(), "No connection, try again later", Toast.LENGTH_SHORT).show();
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
		LoadGrid(view);
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
			/*
			 * Intent intent = new Intent(getActivity(),
			 * CreateTripManagerActivity.class); startActivity(intent);
			 */
			break;
		case R.id.searchPhuot:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	class DiemPhuotCompare implements Comparator<Diemphuot> {

		@Override
		public int compare(Diemphuot e1, Diemphuot e2) {
			if (e1.getMaDiemphuot().compareTo(e2.getMaDiemphuot()) > 0) {
				return 1;
			} else {
				return -1;
			}
		}

	}

	public void getPlacetoServer() {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("p", p);
		client.get(Global.BASE_URI + "/" + Global.URI_GETPLACE_PATH, params, new AsyncHttpResponseHandler() {
			public void onSuccess(String response) {
				Log.e("getPlacetoServer", response);
				// Toast.makeText(getApplicationContext(),
				// "vï¿½o thï¿½nh cï¿½ng", Toast.LENGTH_SHORT).show();\
				listPlace(response);
				// phuots = exeQ.getAllDiemphuot();
				// adapter.notifyDataSetChanged();
			}

			@Override
			public void onFailure(int statusCode, Throwable error, String content) {

			}
		});
	}

	private String listPlace(String response) {

		try {
			JSONArray arrObj = new JSONArray(response);
			Log.e("StringResponse", response);
			for (int i = 0; i < arrObj.length(); i++) {
				JSONObject placeLocationJson = arrObj.getJSONObject(i);
				Diemphuot place = new Diemphuot();

				String _id = placeLocationJson.optString("_id");
				String note = placeLocationJson.optString("note");
				String lat = placeLocationJson.optString("lat") + "";
				String lon = placeLocationJson.optString("lon") + "";
				String name = placeLocationJson.optString("name");
				String image = placeLocationJson.optString("image");
				JSONObject objectLocation = placeLocationJson.getJSONObject("location");
				String id_city = objectLocation.optString("_id");
				String lat_city = objectLocation.optString("lat");
				String lon_city = objectLocation.optString("lon");
				String name_city = objectLocation.optString("name");

				place.setMaDiemphuot(_id);
				place.setGhichu(note);
				place.setLat(lat);
				place.setLon(lon);
				place.setTenDiemphuot(name);
				place.setImage(image);
				place.setDiachi("a" + i);
				// phuots.add(place);
				// Log.d("PhuotImageLink", phuots.get(i).getImage());
				exeQ.insert_tbl_diemphuot_single(place);
			}
			phuots = exeQ.getAllDiemphuot();
			return "true";
		} catch (JSONException e) {
			e.printStackTrace();
			return "fail";
		}
	}

	private void LoadGrid(View view) {
		exeQ = new ExecuteQuery(getActivity());
		exeQ.createDatabase();
		exeQ.open();
		phuots = new ArrayList<Diemphuot>();
		gv_phuot = (GridView) view.findViewById(R.id.gvPhuotGrid);
		getPlacetoServer();
		phuots = exeQ.getAllDiemphuot();
		
		Collections.sort(phuots, new Comparator<Diemphuot>() {

			@Override
			public int compare(Diemphuot st1, Diemphuot st2) {

				return (st1.getMaDiemphuot()).compareTo(st2.getMaDiemphuot());
			}
		});
		//getPlacetoServer();
		DiemphuotAdapter adapter = new DiemphuotAdapter(getActivity(), phuots);
		//loadDiemPhuot(adapter);
		gv_phuot.setAdapter(adapter);
		loadAnh(adapter, phuots);
	}

	/*private void loadDiemPhuot(DiemphuotAdapter adapter){
		getDiemPhuot gdt = new getDiemPhuot();
		gdt.execute();
		adapter.notifyDataSetChanged();
	}*/
	private void loadAnh(DiemphuotAdapter adapter, ArrayList<Diemphuot> dps) {
		getImagePhuot gidp = new getImagePhuot(dps, adapter);
		gidp.execute();
		adapter.notifyDataSetChanged();
	}

	public class getDiemPhuot extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			getPlacetoServer();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Toast.makeText(getActivity(), "Đã xong: điểm", Toast.LENGTH_SHORT).show();
			//adapter.notifyDataSetChanged();
		}
	}

	public class getImagePhuot extends AsyncTask<Void, Void, Void> {

		ArrayList<Diemphuot> arr;
		DiemphuotAdapter adapter;

		public getImagePhuot(ArrayList<Diemphuot> phuots, DiemphuotAdapter adapter) {
			// TODO Auto-generated constructor stub
			this.arr = phuots;
			this.adapter = adapter;
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
			
			Toast.makeText(getActivity(), "Đã xong: ảnh", Toast.LENGTH_SHORT).show();
		}
	}
	/*void initGridView(View view) {
		gv_phuot = (GridView) view.findViewById(R.id.gvPhuotGrid);

		phuots = new ArrayList<Diemphuot>();
		Diemphuot diemphuot;

		for (int i = 0; i < Global.LIST_DIEMPHUOT.size(); ++i) {
			diemphuot = new Diemphuot();
			diemphuot.setMaDiemphuot(Global.LIST_DIEMPHUOT.get(i).getMaDiemphuot());
			diemphuot.setTenDiemphuot(Global.LIST_DIEMPHUOT.get(i).getTenDiemphuot());
			diemphuot.setLat(Global.LIST_DIEMPHUOT.get(i).getLat());
			diemphuot.setLon(Global.LIST_DIEMPHUOT.get(i).getLon());
			diemphuot.setMaTinh(Global.LIST_DIEMPHUOT.get(i).getMaTinh());
			diemphuot.setDiachi(Global.LIST_DIEMPHUOT.get(i).getDiachi());
			diemphuot.setGhichu(Global.LIST_DIEMPHUOT.get(i).getGhichu());
			diemphuot.setImage(Global.LIST_DIEMPHUOT.get(i).getImage());
			diemphuot.setTrangthaiChuan(Global.LIST_DIEMPHUOT.get(i).getTrangthaiChuan());

			
			 * diemphuot.setImage(Global.LIST_DIEMPHUOT.get(i) .getIddiem() +
			 * ".jpg");
			 
			phuots.add(diemphuot);
		}
		// gv_phuot.setAdapter(new DiemphuotAdapter(getActivity(), phuots));
		gv_phuot.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				Toast.makeText(getActivity(), "" + phuots.get(position).getGhichu(), Toast.LENGTH_SHORT).show();
				
				 * ListPhuotFragment f = new ListPhuotFragment(); Bundle b = new
				 * Bundle(); b.putString("maDiemPhuot",
				 * phuots.get(position).getMaDiemphuot());
				 * b.putString("tenDiemPhuot",
				 * phuots.get(position).getTenDiemphuot());
				 * b.putString("ghiChu", phuots.get(position).getGhichu());
				 * b.putString("trangThaiChuan",
				 * phuots.get(position).getTrangthaiChuan()); f.setArguments(b);
				 
				Intent intent = new Intent(getActivity(), PhuotDetailManager.class);
				
				 * intent.putExtra("maDiemPhuot",
				 * phuots.get(position).getMaDiemphuot());
				 * intent.putExtra("tenDiemPhuot",
				 * phuots.get(position).getTenDiemphuot());
				 * intent.putExtra("ghiChu", phuots.get(position).getGhichu());
				 * intent.putExtra("trangThaiChuan",
				 * phuots.get(position).getTrangthaiChuan());
				 
				startActivity(intent);
			}
		});
	}

	int getImageId(String imageName) {
		imageName = imageName.substring(3);
		return this.getResources().getIdentifier(imageName, "drawable", getActivity().getPackageName());
	}*/
}
