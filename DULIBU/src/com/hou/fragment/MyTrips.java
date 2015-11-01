package com.hou.fragment;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hou.adapters.LichtrinhAdapter;
import com.hou.adapters.MyTripAdapter;
import com.hou.app.Global;
import com.hou.database_handler.ExecuteQuery;
import com.hou.dulibu.CreateTripManagerActivity;
import com.hou.dulibu.DeviceStatus;
import com.hou.dulibu.R;
import com.hou.dulibu.TripDetailManagerActivity;
import com.hou.dulibu.TripDetailManagerForUser;
import com.hou.fragment.ListTripFragment.getImage;
import com.hou.model.Lichtrinh;
import com.hou.model.LichtrinhMember;
import com.hou.model.Tinh_Thanhpho;
import com.hou.ultis.ImageUltiFunctions;
import com.hou.upload.MD5;
import com.hou.upload.imageOnServer;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

public class MyTrips extends android.support.v4.app.Fragment {
	ProgressDialog pDialog;
	ArrayList<Lichtrinh> lichtrinh;
	ListView lvListTrip;
	private SwipeRefreshLayout swipeRefreshLayout;
	LichtrinhAdapter adapter;
	private ExecuteQuery exeQ;
	private Tinh_Thanhpho startPlace, endPlace;
	ArrayList<LichtrinhMember> arrListUsers;
	ArrayList<String> lstRoles;
	ProgressBar progressBar;
	private int page = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.list_trip_manager, container,
				false);
		lichtrinh = new ArrayList<Lichtrinh>();
		arrListUsers = new ArrayList<LichtrinhMember>();
		lvListTrip = (ListView) view.findViewById(R.id.lvTripList);
		adapter = new LichtrinhAdapter(getActivity(), R.layout.list_trip_item,
				lichtrinh);
		lstRoles = new ArrayList<String>();
		progressBar = (ProgressBar) view.findViewById(R.id.pb);
		LoadDataFromServer();
		exeQ = new ExecuteQuery(getActivity());
		exeQ.createDatabase();
		exeQ.open();
		// DeviceStatus ds = new DeviceStatus();
		swipeRefreshLayout = (SwipeRefreshLayout) view
				.findViewById(R.id.swipe_refresh_layout);
		swipeRefreshLayout.setColorSchemeColors(R.color.StatusBarColor);
		swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				lichtrinh.clear();
				LoadDataFromServer();
				Handler h = new Handler();
				h.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						swipeRefreshLayout.setRefreshing(false);
					}
				}, 3000);

			}
		});

		return view;

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// Log.d("change", Global.getPreference(getActivity(),
		// Global.USER_ROLE_CHANGE, "1"));
		// if (Global.getPreference(getActivity(), Global.USER_ROLE_CHANGE, "1")
		// .equals("1")) {
		// Global.savePreference(getActivity(), Global.USER_ROLE_CHANGE, "0");
		lichtrinh.clear();
		LoadDataFromServer();

		// }

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
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();
		switch (id) {
		case R.id.addTripMyTripActionBar:
			Intent intent = new Intent(getActivity(),
					CreateTripManagerActivity.class);
			startActivity(intent);
		default:
			return super.onOptionsItemSelected(item);

		}
	}

	public void LoadDataFromServer() {
		progressBar.setVisibility(View.VISIBLE);
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(
				Global.BASE_URI
						+ "/"
						+ Global.URI_TRIP_TRIP
						+ "/me?p="
						+ page
						+ "&user_id="
						+ Global.getPreference(getActivity(),
								Global.USER_MAUSER, "user_id")
						+ "&access_token="
						+ Global.getPreference(getActivity(),
								Global.USER_ACCESS_TOKEN, "access_token"),
				new AsyncHttpResponseHandler() {
					public void onSuccess(String response) {
						Log.e("DATA", response);
						executeWhenSendDataSuccess(response);
						if (lichtrinh.size() > 0) {
							lvListTrip.setAdapter(adapter);
							progressBar.setVisibility(View.INVISIBLE);
							lvListTrip
									.setOnItemClickListener(new AdapterView.OnItemClickListener() {

										@Override
										public void onItemClick(
												AdapterView<?> parent,
												View view, int position, long id) {
											// TODO Auto-generated method
											// stub
											try {
												Global.writeFile(
														lichtrinh.get(position),
														new MD5()
																.getMD5("4/4/1994"));

												Global.savePreference(
														getActivity(),
														Global.TRIP_TRIP_ID,
														lichtrinh
																.get(position)
																.getMaLichtrinh());

												Global.savePreference(
														getActivity(),
														"check_role_1",
														lstRoles.get(position)
																.toString());
												Log.d("role____ListFrag",
														lstRoles.get(position));
												Intent intent;
												if (lstRoles
														.get(position)
														.toString()
														.compareTo(
																Global.USER_ROLE_USER) > 0) {
													intent = new Intent(
															getActivity(),
															TripDetailManagerActivity.class);
												} else {
													intent = new Intent(
															getActivity(),
															TripDetailManagerForUser.class);
												}
												startActivity(intent);
											} catch (NoSuchAlgorithmException e) {
												e.printStackTrace();
											}
										}
									});
						}

					}

					@Override
					public void onFailure(int statusCode, Throwable error,
							String content) {
						Log.e("Error_get_data", statusCode + content + "sadasd");
						switch (statusCode) {
						case 400:
							Toast.makeText(getActivity(),
									getResources().getString(R.string.e400),
									Toast.LENGTH_LONG).show();
							break;
						case 403:
							Toast.makeText(getActivity(),
									getResources().getString(R.string.e403),
									Toast.LENGTH_LONG).show();
							break;
						case 404:
							Toast.makeText(getActivity(),
									getResources().getString(R.string.e404),
									Toast.LENGTH_LONG).show();
							break;
						case 503:
							Toast.makeText(getActivity(),
									getResources().getString(R.string.e503),
									Toast.LENGTH_LONG).show();
							break;
						default:
							break;
						}
					}
				});

	}

	public class getImage extends AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				imageOnServer.downloadFileFromServer(
						(new MD5()).getMD5(params[0]), params[0]);
			} catch (NoSuchAlgorithmException | IOException e) {
				// TODO Auto-generated catch block
				Log.e("Lỗi tải ảnh", e.getMessage() + "");
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			lvListTrip.setAdapter(adapter);
		}

	}

	private void executeWhenSendDataSuccess(String response) {
		lstRoles.clear();
		try {
			JSONArray data = new JSONArray(response);
			for (int i = 0; i < data.length(); i++) {
				JSONObject item = data.getJSONObject(i);
				String _id = item.optString("_id");
				String name = item.optString("name");
				String diemBatdau = item.optJSONObject("begin_location")
						.optString("name");
				String diemKetthuc = item.optJSONObject("end_location")
						.optString("name");
				String tgBatdau = item.optString("start_date");
				String tgKetthuc = item.optString("end_date");
				String admin = "";
				if (!item.optString("created_by").equals("")) {
					admin = item.getJSONObject("created_by").optString(
							"fullname");
				}
				String chiphicanhan = item.optString("expense", "0");
				int chiphicanhans = Integer.parseInt(chiphicanhan);
				String thoigian_xuatphat = item.optString("gathering_time");
				String diadiem_xuatphat = item.optString("gathering_position");
				String note = item.optString("note");
				String image = item.optString("image");

				File f = ImageUltiFunctions.getFileFromUri(Global
						.getURI(new MD5().getMD5(image)));
				if (f == null) {
					getImage img = new getImage();
					img.execute(image);
				}

				Lichtrinh dataTrip;
				dataTrip = new Lichtrinh(_id, name, diemBatdau, diemKetthuc,
						tgBatdau, tgKetthuc, "1", "1", "1", admin, "", "",
						chiphicanhans, 0, "", image, diadiem_xuatphat,
						thoigian_xuatphat, note);
				lichtrinh.add(dataTrip);
				lstRoles.add(item.optString("_role"));
			}

		} catch (JSONException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
}
