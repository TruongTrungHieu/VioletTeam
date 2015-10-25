package com.hou.dulibu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Context;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.hou.adapters.KhoanChiArrayAdapter;
import com.hou.adapters.SuKienAdapter;
import com.hou.app.Global;
import com.hou.model.Chitieu;
import com.hou.model.Sukien;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class Offline_Activity extends ActionBarActivity {

	final Context context = this;
	ArrayList<Sukien> arrSuKien = new ArrayList<Sukien>();
	ArrayList<Sukien> arrEvent = new ArrayList<Sukien>();
	
	SuKienAdapter adapter = null;
	ListView lvSukien = null;
	String maLichTrinh="";

	// GoogleMap map;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_offline);
	//	maLichTrinh = com.hou.app.Global.getPreference(context,Global.TRIP_TRIP_ID,"Viet");
		maLichTrinh = com.hou.app.Global.getPreference(context,Global.TRIP_TRIP_ID,"Viet");
		lvSukien = (ListView) findViewById(R.id.lvMap);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		
		getEvent(maLichTrinh);
	}
	public void loadData() {
		arrSuKien = arrEvent;
		adapter = new SuKienAdapter(this, R.layout.sukien_item, arrSuKien);
		
		lvSukien.setAdapter(adapter);
	
	}
	private void createEvent(String tripId, String name, String time,String lat,
			String lon) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();

		params.put("trip_id", tripId);
		params.put("name", name);
		params.put("time", time);
		params.put("lat", lat);
		params.put("lon", lon);
		Log.e("createEvent", lat);
		client.post(
				Global.BASE_URI
						+ "/"
						+ Global.URI_POSTEVENT_PATH
						+ "?access_token="
						+ Global.getPreference(this, Global.USER_ACCESS_TOKEN,
								""), params, new AsyncHttpResponseHandler() {
					public void onSuccess(String response) {
						Log.e("createNewTrip", response);

						if (executeWhenRegisterSuccess(response)) {
							Toast.makeText(getApplicationContext(),
									"Tao moi su kien thanh cong",
									Toast.LENGTH_SHORT).show();
							// Intent intent = new Intent(
							// RegisterManagerActivity.this,
							// LoginManagerActivity.class);

						} else {
							Toast.makeText(getApplicationContext(),
									"Khong tao moi duoc su kien",
									Toast.LENGTH_LONG).show();
							
						}
					}

					@Override
					public void onFailure(int statusCode, Throwable error,
							String content) {
						Log.d("Tao event that bai", content);
					}
				});
	}
	public void getEvent(String idTrip) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("id", idTrip);
		params.put("access_token",
				Global.getPreference(this, Global.USER_ACCESS_TOKEN, ""));

		client.get(Global.BASE_URI + "/" + Global.URI_GETEVENT_PATH, params,
				new AsyncHttpResponseHandler() {
					public void onSuccess(String response) {
						Log.e("getEvent", response);
					
					//	listChiPhi(response);
					//	getTotalMoney(maLichTrinh);
						listEvent(response);
						loadData();
						
						// loadData();
					}

					@Override
					public void onFailure(int statusCode, Throwable error,
							String content) {
						Log.e("LayListEvent", content);

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
				
				eventSK.setMaLichtrinh(com.hou.app.Global.getPreference(context,Global.TRIP_TRIP_ID,"Viet"));
				arrEvent.add(eventSK);
			//	Log.e("listChiPhiVIet", sotien + "");
				
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
		if(id == android.R.id.home){
			onBackPressed();
		}
		if (id == R.id.action_add) {
			// Toast.makeText(getApplication(),"Select form add Chi tiet",
			// Toast.LENGTH_SHORT).show();
			final Dialog dialog = new Dialog(context);
			dialog.setContentView(R.layout.dialog_sukien);
			dialog.setTitle(getString(R.string.titleOfflineDialog));
			
	    
			Button btnOK = (Button) dialog.findViewById(R.id.btnAdd);
			// if button is clicked, close the custom dialog
			btnOK.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {

				
					xulyNhap();
					
					Toast.makeText(getApplication(), "Click on button ADD",
							Toast.LENGTH_SHORT).show();
					dialog.dismiss();
				}

				private void xulyNhap() {

					final EditText txtTenSuKien = (EditText) dialog
							.findViewById(R.id.txtTenSuKien);
					final EditText txtThoigian = (EditText) dialog
							.findViewById(R.id.txtThoiGian);
					final EditText txtDiadiem = (EditText) dialog
							.findViewById(R.id.txtDiadiem);
					String ten, thoigian, diadiem;
					String lat = "0";
					String lon = "0";
					ten = txtTenSuKien.getText().toString();
					thoigian = txtThoigian.getText().toString();
					diadiem = txtDiadiem.getText().toString();
					
					if (ten.equals("") || thoigian.equals("") || diadiem.equals("")) {
						Toast.makeText(getApplication(),
								"Ban dien thieu thong tin", Toast.LENGTH_SHORT)
								.show();
						return;
					}
					
					Sukien sk = new Sukien();
					sk.setTenSukien(ten);
					sk.setThoigian(thoigian);
					sk.setDiadiem(diadiem);
					
					Address vitri = getLocationFromAddress(diadiem);
					
					if (vitri != null) {
						sk.setLat(vitri.getLatitude()+"");
						sk.setLon(vitri.getLongitude()+"");
					} else {
						sk.setLat("20.984434");
						sk.setLon("105.838914");
					}
					
					
					
//					Sukien sk = new Sukien();
//					sk.setTenSukien("96 Dinh Cong");
//					sk.setThoigian("16h");
//					sk.setDiadiem("96 dinh cong");

					
		    	

					arrSuKien.add(sk);
					createEvent(maLichTrinh, sk.getTenSukien(), sk.getThoigian(), sk.getLat(), sk.getLon());
					
					// LatLng latLng=new LatLng(-14.235004,-51.925280);
					// map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));

					adapter.notifyDataSetChanged();
					
					//dialog.dismiss();

				}

			
			});
			Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
			btnCancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					dialog.dismiss();
				}
			});

			
			dialog.show();
		}
		return super.onOptionsItemSelected(item);
	}

	private Address getLocationFromAddress(String strTim) {
		Geocoder coder = new Geocoder(context);
    	List<Address> address = null;
    	try {
			address = coder.getFromLocationName(strTim,1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			
		}
    			
    	//Toast.makeText(getApplicationContext(),strTim, Toast.LENGTH_LONG).show();
    	Address location = null;
    	if (address.size() > 0) {
    		location = address.get(0);
    	}
    	
    	return location;
	
	}

}
