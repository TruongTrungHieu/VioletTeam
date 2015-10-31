package com.hou.dulibu;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.hou.adapters.SuKienAdapter;
import com.hou.app.Global;
import com.hou.model.Sukien;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class Offline_Activity extends ActionBarActivity {

	ArrayList<Sukien> arrSuKien = new ArrayList<Sukien>();
	ArrayList<Sukien> arrEvent = new ArrayList<Sukien>();
	Dialog dialog;

	SuKienAdapter adapter = null;
	ListView lvSukien = null;
	String maLichTrinh = "";
	private static String thoigian_sk = "";
	EditText txtTenSuKien;
	EditText txtThoigian;
	EditText txtDiadiem;

	// GoogleMap map;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_offline);
		

		maLichTrinh = com.hou.app.Global.getPreference(getBaseContext(),
				Global.TRIP_TRIP_ID, "Viet");
		lvSukien = (ListView) findViewById(R.id.lvMap);
		adapter = new SuKienAdapter(this, R.layout.sukien_item, arrEvent);
		lvSukien.setAdapter(adapter);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		getEvent(maLichTrinh);
	}

	public void loadData() {
		arrSuKien = arrEvent;		
		adapter.notifyDataSetChanged();
	}

	private void createEvent(String tripId, String name, String time,
			String lat, String lon) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();

		params.put("trip_id", tripId);
		params.put("name", name);
		params.put("time", time);
		params.put("lat", lat);
		params.put("lon", lon);
		Log.e("createEvent", lat);
		String url = Global.BASE_URI
				+ "/"
				+ Global.URI_POSTEVENT_PATH
				+ "?access_token="
				+ Global.getPreference(getBaseContext(),
						Global.USER_ACCESS_TOKEN, "");
		client.post(url, params, new AsyncHttpResponseHandler() {
			public void onSuccess(String response) {
				Log.e("createNewTrip", response);

				if (executeWhenRegisterSuccess(response)) {
					Toast.makeText(getApplicationContext(),
							"Tao su kien thanh cong", Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(getApplicationContext(),
							"Khong tao duoc su kien", Toast.LENGTH_LONG).show();

				}
			}

			@Override
			public void onFailure(int statusCode, Throwable error,
					String content) {
				Log.d("Tao su kien that bai", content);
			}
		});
	}

	public void getEvent(String idTrip) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("id", idTrip);
		params.put("access_token", Global.getPreference(
				getApplicationContext(), Global.USER_ACCESS_TOKEN, ""));

		client.get(Global.BASE_URI + "/" + Global.URI_GETEVENT_PATH, params,
				new AsyncHttpResponseHandler() {
					public void onSuccess(String response) {
						Log.e("getEvent", response);
						listEvent(response);
						
					}

					@Override
					public void onFailure(int statusCode, Throwable error,
							String content) {
						Log.e("LayListEvent", content);
					}

					@Override
					public void onFinish() {
						// TODO Auto-generated method stub
						super.onFinish();
						
						//loadData();
						adapter.notifyDataSetChanged();
					}
					
					
				});
	}

	private String listEvent(String response) {
		try {
			JSONArray arrObj = new JSONArray(response);
			for (int i = 0; i < arrObj.length(); i++) {
				JSONObject eventJson = arrObj.getJSONObject(i);
				Sukien eventSK = new Sukien();

				String event_id = eventJson.optString("_id");
				String name = eventJson.optString("name");
				String time = eventJson.optString("time");
				String event_lat = eventJson.optString("lat");
				String event_lon = eventJson.optString("lon");

				eventSK.setMaSukien(event_id);
				eventSK.setDiadiem("");
				eventSK.setTenSukien(name);
				eventSK.setLat(event_lat);
				eventSK.setLon(event_lon);
				eventSK.setThoigian(time);

				eventSK.setMaLichtrinh(com.hou.app.Global.getPreference(
						getBaseContext(), Global.TRIP_TRIP_ID, "Viet"));
				arrEvent.add(eventSK);
				// Log.e("listChiPhiVIet", sotien + "");

			}

			// Toast.makeText(getApplicationContext(), "KQ JSON",
			// Toast.LENGTH_LONG).show();

			return "true";
		} catch (JSONException e) {
			e.printStackTrace();
			return "false";

		}
	}

	private boolean executeWhenRegisterSuccess(String reponse) {
		boolean check = true;
		return check;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.offline_, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == android.R.id.home) {
			onBackPressed();
		}
		if (id == R.id.action_add) {
			addEvent();
		}
		return super.onOptionsItemSelected(item);
	}

	public void showDatePickerDialog() {
		DialogFragment newFragment = new DatePickerFragment();
		newFragment.show(getSupportFragmentManager(), "datePicker");
	}

	public static class DatePickerFragment extends DialogFragment implements
			DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the default date in the picker
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);

			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}

		public void onDateSet(DatePicker view, int year, int month, int day) {
			// Do something with the date chosen by the user
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",
					Locale.US);
			try {
				thoigian_sk = dateFormat.format(Global
						.getDateFromDatePicket(view));
			} catch (Exception e) {
				// TODO: handle exception
			}
			// txtThoigian.setText(thoigian_sk);

		}
	}

	public void timePikerDialog(int timePickerID, int btnDoneID,
			int btnCancelID, final TextView tv, int Layout, int dialogTitle) {
		final Dialog dialog = new Dialog(this);
		dialog.setContentView(Layout);
		dialog.setTitle(dialogTitle);
		dialog.setCancelable(true);
		final TimePicker tpTimePK = (TimePicker) dialog
				.findViewById(timePickerID);
		tpTimePK.setIs24HourView(true);
		Button btnCancelTimePiker = (Button) dialog.findViewById(btnCancelID);
		btnCancelTimePiker.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		Button btnDoneTimePicker = (Button) dialog.findViewById(btnDoneID);
		btnDoneTimePicker.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// TextView edTimePlace = (TextView) findViewById(textID);
				String res = "";
				if (tpTimePK.getCurrentHour() < 10) {
					res += "0" + tpTimePK.getCurrentHour();
				} else {
					res += tpTimePK.getCurrentHour();
				}
				if (tpTimePK.getCurrentMinute() < 10) {
					res += ":0" + tpTimePK.getCurrentMinute();
				} else {
					res += ":" + tpTimePK.getCurrentMinute();
				}
				res += ":00";
				tv.setText(res + " " + thoigian_sk);
				dialog.dismiss();
			}
		});
		// Show Dialog
		dialog.show();
	}

	private Address getLocationFromAddress(String strTim) {
		Geocoder coder = new Geocoder(getBaseContext());
		List<Address> address = null;
		try {
			address = coder.getFromLocationName(strTim, 1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		// Toast.makeText(getApplicationContext(),strTim,
		// Toast.LENGTH_LONG).show();
		Address location = null;
		if (address.size() > 0) {
			location = address.get(0);
		}

		return location;

	}

	@SuppressLint("InflateParams") private void addEvent() {

		// Toast.makeText(getApplication(),"Select form add Chi tiet",
		// Toast.LENGTH_SHORT).show();
		// dialog = new Dialog(getBaseContext());
		// dialog.setContentView(R.layout.dialog_sukien);
		// dialog.setTitle(getString(R.string.titleOfflineDialog));
		// txtTenSuKien = (EditText) dialog.findViewById(R.id.txtTenSuKien);
		// txtThoigian = (EditText) dialog.findViewById(R.id.txtThoiGian);
		// txtDiadiem = (EditText) dialog.findViewById(R.id.txtDiadiem);
		// txtThoigian.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// // datePikerDialog(R.id.dpCreateDatePicker,
		// // R.id.btnDoneCreateTripDatePiker,
		// // R.id.btnCancelCreateTripDatePiker, edtNgaysinh,
		// // R.layout.date_picker, R.string.titleTimeDialog);
		//
		// // showTimePickerDialog();
		//
		// // TODO Auto-generated method stub
		// timePikerDialog(R.id.tpCreateTripTimePicker,
		// R.id.btnDoneCreateTripTimePiker,
		// R.id.btnCancelCreateTripTimePiker, txtThoigian,
		// R.layout.time_picker, R.string.titleTimeDialog);
		// txtThoigian.setError(null);
		// txtThoigian.setFocusableInTouchMode(true);
		// txtThoigian.setFocusable(true);
		// showDatePickerDialog();
		//
		// }
		// });
		//
		// Button btnOK = (Button) dialog.findViewById(R.id.btnAdd);
		// // if button is clicked, close the custom dialog
		// btnOK.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		//
		// xulyNhap();
		//
		// dialog.dismiss();
		// }
		//
		// private void xulyNhap() {
		//
		// String ten, thoigian, diadiem;
		// String lat = "0";
		// String lon = "0";
		// ten = txtTenSuKien.getText().toString();
		// thoigian = txtThoigian.getText().toString();
		// diadiem = txtDiadiem.getText().toString();
		//
		// if (ten.equals("") || thoigian.equals("") || diadiem.equals("")) {
		// Toast.makeText(getApplication(),
		// "Ban dien thieu thong tin", Toast.LENGTH_SHORT)
		// .show();
		// return;
		// }
		//
		// Sukien sk = new Sukien();
		// Address vitri = getLocationFromAddress(diadiem);
		// if (vitri != null) {
		// sk.setLat(vitri.getLatitude() + "");
		// sk.setLon(vitri.getLongitude() + "");
		// } else {
		// /*
		// * sk.setLat("20.984434"); sk.setLon("105.838914");
		// */
		// Toast.makeText(getApplication(), "Khong tim thay dia diem",
		// Toast.LENGTH_LONG).show();
		// return;
		// }
		//
		// sk.setTenSukien(ten);
		// sk.setThoigian(thoigian);
		// sk.setDiadiem(diadiem);
		//
		// // Sukien sk = new Sukien();
		// // sk.setTenSukien("96 Dinh Cong");
		// // sk.setThoigian("16h");
		// // sk.setDiadiem("96 dinh cong");
		//
		// arrSuKien.add(sk);
		// createEvent(maLichTrinh, sk.getTenSukien(), sk.getThoigian(),
		// sk.getLat(), sk.getLon());
		//
		// // LatLng latLng=new LatLng(-14.235004,-51.925280);
		// // map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,
		// // 13));
		//
		// adapter.notifyDataSetChanged();
		//
		// // dialog.dismiss();
		//
		// }
		//
		// });
		// Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
		// btnCancel.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		//
		// dialog.dismiss();
		// }
		// });
		//
		// dialog.show();

		LayoutInflater inflater = getLayoutInflater();
		View alertLayout = inflater.inflate(R.layout.dialog_sukien, null);
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		txtTenSuKien = (EditText) alertLayout.findViewById(R.id.txtTenSuKien);
		txtThoigian = (EditText) alertLayout.findViewById(R.id.txtThoiGian);
		txtDiadiem = (EditText) alertLayout.findViewById(R.id.txtDiadiem);

		txtThoigian.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// datePikerDialog(R.id.dpCreateDatePicker,
				// R.id.btnDoneCreateTripDatePiker,
				// R.id.btnCancelCreateTripDatePiker, edtNgaysinh,
				// R.layout.date_picker, R.string.titleTimeDialog);

				// showTimePickerDialog();

				// TODO Auto-generated method stub
				timePikerDialog(R.id.tpCreateTripTimePicker,
						R.id.btnDoneCreateTripTimePiker,
						R.id.btnCancelCreateTripTimePiker, txtThoigian,
						R.layout.time_picker, R.string.titleTimeDialog);
				txtThoigian.setError(null);
				txtThoigian.setFocusableInTouchMode(true);
				txtThoigian.setFocusable(true);
				showDatePickerDialog();

			}
		});

		alert.setView(alertLayout);
		alert.setCancelable(false);
		alert.setTitle(getString(R.string.titleOfflineDialog));
		final AlertDialog dialog = alert.create();

		Button btnOK = (Button) alertLayout.findViewById(R.id.btnAdd);
		// if button is clicked, close the custom dialog
		btnOK.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				xulyNhap();

				dialog.dismiss();
			}

		});

		Button btnCancel = (Button) alertLayout.findViewById(R.id.btnCancel);
		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				dialog.dismiss();
			}
		});

		dialog.show();

	}

	private void xulyNhap() {

		String ten, thoigian, diadiem;
		ten = txtTenSuKien.getText().toString();
		thoigian = txtThoigian.getText().toString();
		diadiem = txtDiadiem.getText().toString();

		if (ten.equals("") || thoigian.equals("") || diadiem.equals("")) {
			Toast.makeText(getApplication(), "Ban dien thieu thong tin",
					Toast.LENGTH_SHORT).show();
			return;
		}

		Sukien sk = new Sukien();
		sk.setLat("0");
		sk.setLon("0");
		Address vitri = getLocationFromAddress(diadiem);
		if (vitri != null) {
			sk.setLat(vitri.getLatitude() + "");
			sk.setLon(vitri.getLongitude() + "");
		} else {
			/*
			 * sk.setLat("20.984434"); sk.setLon("105.838914");
			 */
			Toast.makeText(getApplication(), "Khong tim thay dia diem",
					Toast.LENGTH_LONG).show();
			return;
		}

		sk.setTenSukien(ten);
		sk.setThoigian(thoigian);
		sk.setDiadiem(diadiem);

		// Sukien sk = new Sukien();
		// sk.setTenSukien("96 Dinh Cong");
		// sk.setThoigian("16h");
		// sk.setDiadiem("96 dinh cong");

		arrSuKien.add(sk);
		adapter.notifyDataSetChanged();
		createEvent(maLichTrinh, sk.getTenSukien(), sk.getThoigian(),
				sk.getLat(), sk.getLon());

		// LatLng latLng=new LatLng(-14.235004,-51.925280);
		// map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,
		// 13));

		adapter.notifyDataSetChanged();

		// dialog.dismiss();

	}

}
