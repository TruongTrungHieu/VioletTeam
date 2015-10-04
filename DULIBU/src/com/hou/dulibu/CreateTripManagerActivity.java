package com.hou.dulibu;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class CreateTripManagerActivity extends ActionBarActivity {
	Button btnCreatePlace;
	GoogleMap supportMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_trip_manager);
		if (getSupportActionBar() != null) {
			getSupportActionBar().setDisplayShowCustomEnabled(true);
			getSupportActionBar().setBackgroundDrawable(
					new ColorDrawable(Color.parseColor("#0aae44")));
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}

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

	public void showChoosePlace() {
		LayoutInflater li = LayoutInflater.from(context);
		View promptsView = li.inflate(R.layout.choose_place_maps, null);
	public void showChoosePlace() {
		
	
		LayoutInflater li = LayoutInflater.from(context);
		View promptsView = li.inflate(R.layout.choose_place_maps, null);

		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setView(alertLayout);
		SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager()
	            .findFragmentById(R.id.mapView);
	    supportMap = fm.getMap();
		alert.setCancelable(false);
		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						return;
					}
				});

		alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);
		alertDialogBuilder.setView(promptsView);
		alertDialog = alertDialogBuilder.create();
		alertDialog.show();
		final Button btnSubmitPlace = (Button) promptsView.findViewById(R.id.btnSubmitPlace);
		btnSubmitPlace.setOnClickListener(new View.OnClickListener() {
			
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);
		alertDialogBuilder.setView(promptsView);
	
		alertDialog = alertDialogBuilder.create();
	
		
		alertDialog.show();
		final Button btnSubmitPlace = (Button) promptsView.findViewById(R.id.btnSubmitPlace);
		btnSubmitPlace.setOnClickListener(new View.OnClickListener() {
			
			@Override

			public void onClick(DialogInterface dialog, int which) {
				return;
			public void onClick(View v) {
				// TODO Auto-generated method stub
				alertDialog.dismiss();
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				alertDialog.dismiss();
				
			}
		});
		AlertDialog dialog = alert.create();
		dialog.show();
	}

	// public void showChoosePlace() {
	// LayoutInflater li = LayoutInflater.from(context);
	// View promptsView = li.inflate(R.layout.choose_place_maps, null);
	// AlertDialog.Builder alertDialogBuilder = new
	// AlertDialog.Builder(context);
	// alertDialogBuilder.setView(promptsView);
	// alertDialog = alertDialogBuilder.create();
	// alertDialog.show();
	// final Button btnSubmitPlace = (Button)
	// promptsView.findViewById(R.id.btnSubmitPlace);
	// btnSubmitPlace.setOnClickListener(new View.OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// // TODO Auto-generated method stub
	// alertDialog.dismiss();
	// }
	// });
	// }

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
}
