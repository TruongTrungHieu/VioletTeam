package com.hou.dulibu;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;


import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class CreateTripManagerActivity extends ActionBarActivity implements
		OnMapReadyCallback {
	private Spinner spStartPlace;
	private Spinner spEndPlace;
	Button btnCreatePlace, btnCreateTrip;
	//TimePicker tpTimePK;
	GoogleMap mMap;
	EditText edTripName, edKinhPhi, edPlaceStart;
	TextView edTimePlace, edDayStart, edDayEnd, edStartTime, edEndTime, edOfflineTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_trip_manager);
		 if (getSupportActionBar() != null) {
		 //getSupportActionBar().setDisplayShowCustomEnabled(true);
		 getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0aae44")));
		 getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		 }
		 edTripName = (EditText) findViewById(R.id.txtNameTrip);
		 edDayStart = (TextView) findViewById(R.id.txtStartDay);
		 edDayEnd = (TextView) findViewById(R.id.txtEndDay);
		 edKinhPhi = (EditText) findViewById(R.id.txtKinhphi);
		 edTimePlace = (TextView) findViewById(R.id.txtTimePlace);
		 edPlaceStart = (EditText) findViewById(R.id.txtPlaceStart);
		 edStartTime =(TextView) findViewById(R.id.txtStartTime);
		 edEndTime = (TextView) findViewById(R.id.txtEndTime);
		 edOfflineTime = (TextView) findViewById(R.id.txtTimeOffline);
		 
		// GPS when leave
		spStartPlace = (Spinner) findViewById(R.id.spStartPlace);
		spEndPlace = (Spinner) findViewById(R.id.spEndPlace);
		
		btnCreatePlace = (Button) findViewById(R.id.btnCreatePlace);
		btnCreatePlace.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				displayAlertDialog();
			}
		});
		btnCreateTrip = (Button) findViewById(R.id.btnCreateTrip);
		btnCreateTrip.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(validatorButton()==true){
					Toast.makeText(getBaseContext(), "Done", Toast.LENGTH_SHORT).show();
					Toast.makeText(getBaseContext(), ""+edTripName.getText().toString(), Toast.LENGTH_SHORT).show();
				}
				else{
					Toast.makeText(getBaseContext(), "Please check your infomations again", Toast.LENGTH_SHORT).show();
					checkEditText();
				}
			}
		});
		edOfflineTime.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				timePikerDialog(R.id.tpCreateTripTimePicker, R.id.btnDoneCreateTripTimePiker, R.id.btnCancelCreateTripTimePiker, edOfflineTime, R.layout.time_picker, R.string.titleTimeDialog);
			}
		});
		edEndTime.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				timePikerDialog(R.id.tpCreateTripTimePicker, R.id.btnDoneCreateTripTimePiker, R.id.btnCancelCreateTripTimePiker, edEndTime, R.layout.time_picker, R.string.titleTimeDialog);
			}
		});
		edStartTime.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				timePikerDialog(R.id.tpCreateTripTimePicker, R.id.btnDoneCreateTripTimePiker, R.id.btnCancelCreateTripTimePiker, edStartTime, R.layout.time_picker, R.string.titleTimeDialog);
			}
		});
		edDayEnd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				datePikerDialog(R.id.dpCreateDatePicker, R.id.btnDoneCreateTripDatePiker, R.id.btnCancelCreateTripDatePiker, edDayEnd, R.layout.date_picker, R.string.titleTimeDialog);
			}
		});
		edDayStart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				datePikerDialog(R.id.dpCreateDatePicker, R.id.btnDoneCreateTripDatePiker, R.id.btnCancelCreateTripDatePiker, edDayStart, R.layout.date_picker, R.string.titleTimeDialog);
			}
		});
		edTimePlace.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//timePlacePikerDialog();
				timePikerDialog(R.id.tpCreateTripTimePicker, R.id.btnDoneCreateTripTimePiker, R.id.btnCancelCreateTripTimePiker, edTimePlace, R.layout.time_picker, R.string.titleTimeDialog);
			}
			
		});
	}
	public void datePikerDialog(int datePickerID, int btnDoneID, int btnCancelID, final TextView tv, int Layout, int dialogTitle){
		final Dialog dialog = new Dialog(this);
        dialog.setContentView(Layout);
        dialog.setTitle(dialogTitle);
        dialog.setCancelable(true);
        final DatePicker dpDatePK = (DatePicker) dialog.findViewById(datePickerID);
        
        Button btnCancelDatePiker = (Button) dialog.findViewById(btnCancelID);
        btnCancelDatePiker.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
        Button btnDoneDatePicker = (Button) dialog.findViewById(btnDoneID);
        btnDoneDatePicker.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//TextView edTimePlace =  (TextView) findViewById(textID);
				int month = dpDatePK.getMonth()+1;
				tv.setText(dpDatePK.getDayOfMonth()+"/"+month+"/"+dpDatePK.getYear());
				dialog.dismiss();
			}
		});
        //Show Dialog
        dialog.show();
	}
	public void timePikerDialog(int timePickerID, int btnDoneID, int btnCancelID, final TextView tv, int Layout, int dialogTitle){
		final Dialog dialog = new Dialog(this);
        dialog.setContentView(Layout);
        dialog.setTitle(dialogTitle);
        dialog.setCancelable(true);
        final TimePicker tpTimePK = (TimePicker) dialog.findViewById(timePickerID);
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
				//TextView edTimePlace =  (TextView) findViewById(textID);
				tv.setText(tpTimePK.getCurrentHour()+":"+tpTimePK.getCurrentMinute());
				dialog.dismiss();
			}
		});
        //Show Dialog
        dialog.show();
	}
	

	public void displayAlertDialog() {
		LayoutInflater inflater = getLayoutInflater();
		View alertLayout = inflater.inflate(R.layout.choose_place_maps, null);
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setView(alertLayout);
		MapFragment mapFragment = (MapFragment) getFragmentManager()
				.findFragmentById(R.id.map);
		mapFragment.getMapAsync(this);
		mMap = mapFragment.getMap();
		mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		mMap.getUiSettings().setMyLocationButtonEnabled(true);
		mMap.setMyLocationEnabled(true);
		mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(21.028860,
				105.852330), 14));
		alert.setCancelable(false);
		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						removeFragmentMaps();
						return;
						
					}
				});

		alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				removeFragmentMaps();
				return;
			}
		});
		AlertDialog dialog = alert.create();
		dialog.show();
	}
	public void checkEditText(){
		if(edPlaceStart.getText().toString().isEmpty()){
			edPlaceStart.setHint(R.string.textHintPlaceStart);
			edPlaceStart.requestFocus();
		}
		if(edTimePlace.getText().toString().isEmpty()){
			edTimePlace.setHint(R.string.textHintTimeStart);
			edTimePlace.requestFocus();
		}
		if(edKinhPhi.getText().toString().isEmpty()){
			edKinhPhi.setHint(R.string.textHintKinhPhi);
			edKinhPhi.requestFocus();
		}
		if(edDayEnd.getText().toString().isEmpty()){
			edDayEnd.setHint(R.string.textHintDayEnd);
			edDayEnd.requestFocus();
		}
		if(edDayStart.getText().toString().isEmpty()){
			edDayStart.setHint(R.string.textHintDayStart);
			edDayStart.requestFocus();
		}
		if(edTripName.getText().toString().isEmpty()){
			edTripName.setHint(R.string.textHintTitleTrip);
			edTripName.requestFocus();
		}
	}
	public boolean validatorButton(){
		if(edTripName.getText().toString().isEmpty() ==false && edDayStart.getText().toString().isEmpty()==false
				&& edDayEnd.getText().toString().isEmpty()==false && edKinhPhi.getText().toString().isEmpty()==false
				&& edTimePlace.getText().toString().isEmpty()==false && edPlaceStart.getText().toString().isEmpty()==false){
			return true;
		}
		else{
			return false;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_trip_manager, menu);
		return true;
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
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
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onMapReady(GoogleMap arg0) {
		// TODO Auto-generated method stub

	}
	private void removeFragmentMaps(){
		FragmentManager fm = getFragmentManager();
	    Fragment fragment = (fm.findFragmentById(R.id.map));
	    FragmentTransaction ft = fm.beginTransaction();
	    ft.remove(fragment);
	    ft.commit();
	}
	
	
}
