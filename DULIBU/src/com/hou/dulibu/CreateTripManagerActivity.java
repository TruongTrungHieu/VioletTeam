package com.hou.dulibu;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;


import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
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
import android.widget.Button;
import android.widget.Spinner;

public class CreateTripManagerActivity extends ActionBarActivity implements
		OnMapReadyCallback {
	private Spinner spStartPlace;
	private Spinner spEndPlace;
	Button btnCreatePlace;
	GoogleMap mMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_trip_manager);
		 if (getSupportActionBar() != null) {
		 getSupportActionBar().setDisplayShowCustomEnabled(true);
		 getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0aae44")));
		 getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		 }
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
