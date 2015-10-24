package com.hou.dulibu;

import java.io.File;
import java.io.FileNotFoundException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.hou.app.Global;
import com.hou.fragment.ProfileFragment.ImageDialog;
import com.hou.ultis.ImageUltiFunctions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hou.database_handler.ExecuteQuery;
import com.hou.dulibu.RegisterManagerActivity.DatePickerFragment;
import com.hou.model.Diemphuot;
import com.hou.model.Tinh_Thanhpho;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class CreateTripManagerActivity extends ActionBarActivity implements
		OnMapReadyCallback {
	private Spinner spStartPlace;
	private Spinner spEndPlace;
	public static Point screenSize = new Point();
	private static final int PICK_FROM_CAMERA = 1;
	private static final int PICK_FROM_FILE = 2;
	private File fromCameraFile, fromGallery;
	private Uri mImageCaptureUri;
	private String path;
	Context context = this;

	Button btnCreatePlace, btnCreateTrip, btnChooseImage;
	// TimePicker tpTimePK;
	GoogleMap mMap;
	EditText edTripName, edKinhPhi, edPlaceStart, edPlaceOffline, edNotes;
	TextView edTimePlace, edDayStart, edDayEnd, edStartTime, edEndTime,
			edOfflineTime, edDateOffline;

	private ArrayList<Diemphuot> listAll, listPlace;
	private ArrayList<Diemphuot> listTouch;
	private ArrayList<Diemphuot> listCreate;
	private Tinh_Thanhpho startPlace, endPlace;
	String dateNow;

	private ExecuteQuery exeQ;

	// dialog
	private Marker markerTouch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_trip_manager);

		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		this.getWindowManager().getDefaultDisplay().getSize(screenSize);

		if (getSupportActionBar() != null) {
			// getSupportActionBar().setDisplayShowCustomEnabled(true);
			getSupportActionBar().setBackgroundDrawable(
					new ColorDrawable(Color.parseColor("#0aae44")));
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}
		Calendar c = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",
				Locale.US);
		dateNow = dateFormat.format(c.getTime());
		
		edTripName = (EditText) findViewById(R.id.txtNameTrip);
		edDayStart = (TextView) findViewById(R.id.txtStartDay);
		edDayEnd = (TextView) findViewById(R.id.txtEndDay);
		edKinhPhi = (EditText) findViewById(R.id.txtKinhphi);
		edTimePlace = (TextView) findViewById(R.id.txtTimePlace);
		edPlaceStart = (EditText) findViewById(R.id.txtPlaceStart);
		edStartTime = (TextView) findViewById(R.id.txtStartTime);
		edEndTime = (TextView) findViewById(R.id.txtEndTime);
		edOfflineTime = (TextView) findViewById(R.id.txtTimeOffline);
		edDateOffline = (TextView) findViewById(R.id.txtDateOffline);
		edPlaceOffline = (EditText) findViewById(R.id.txtPlaceOffline);
		edNotes = (EditText) findViewById(R.id.txtNotes);

		exeQ = new ExecuteQuery(getApplicationContext());
		exeQ.createDatabase();
		exeQ.open();

		listCreate = new ArrayList<Diemphuot>();
		listPlace = new ArrayList<Diemphuot>();
		listTouch = new ArrayList<Diemphuot>();
		listAll = new ArrayList<Diemphuot>();
		listAll = exeQ.getAllDiemphuot();

		// GPS when leave
		spStartPlace = (Spinner) findViewById(R.id.spStartPlace);
		spEndPlace = (Spinner) findViewById(R.id.spEndPlace);

		btnChooseImage = (Button) findViewById(R.id.btnChooseImage);
		btnChooseImage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new ImageDialog(context, R.layout.select_image_layout).show();
			}
		});

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
				if (validatorButton() == true) {
					if (startPlace == null) {
						startPlace = exeQ.getTinhByTentinh(spStartPlace
								.getSelectedItem().toString());
					}
					if (endPlace == null) {
						endPlace = exeQ.getTinhByTentinh(spEndPlace
								.getSelectedItem().toString());
					}
					createNewTrip(edTripName.getText().toString().trim(),
							startPlace.getMaTinh(), endPlace.getMaTinh(),
							edDayStart.getText().toString(), edDayEnd.getText()
									.toString(), edStartTime.getText()
									.toString(),
							edEndTime.getText().toString(), edKinhPhi.getText()
									.toString(), edDateOffline.getText()
									.toString(), edOfflineTime.getText()
									.toString(), edPlaceOffline.getText()
									.toString(), edTimePlace.getText()
									.toString(), edPlaceStart.getText()
									.toString(), edNotes.getText().toString());
					//createNewTrip();
					/*Truyen 8 tham số vào theo thứ tự : name,image,begin_location,
					end_location,start_date,start_time,end_date,end_time*/
					
						
					Toast.makeText(getBaseContext(), "Done", Toast.LENGTH_SHORT)
							.show();
					Toast.makeText(getBaseContext(),
							"" + edTripName.getText().toString(),
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getBaseContext(),
							"Please check your infomations again",
							Toast.LENGTH_SHORT).show();
					checkEditText();
					//createNewTrip();
					/*Truyen 8 tham số vào theo thứ tự : name,image,begin_location,
					end_location,start_date,start_time,end_date,end_time*/
					
						
					Toast.makeText(getBaseContext(), "Done", Toast.LENGTH_SHORT)
							.show();
					Toast.makeText(getBaseContext(),
							"" + edTripName.getText().toString(),
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getBaseContext(),
							"Xem lại thông tin bạn vừa nhập",
							Toast.LENGTH_SHORT).show();
					checkEditText();
				}
			}
		});
		edOfflineTime.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				timePikerDialog(R.id.tpCreateTripTimePicker,
						R.id.btnDoneCreateTripTimePiker,
						R.id.btnCancelCreateTripTimePiker, edOfflineTime,
						R.layout.time_picker, R.string.titleTimeDialog);
			}
		});
		edEndTime.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				timePikerDialog(R.id.tpCreateTripTimePicker,
						R.id.btnDoneCreateTripTimePiker,
						R.id.btnCancelCreateTripTimePiker, edEndTime,
						R.layout.time_picker, R.string.titleTimeDialog);
			}
		});
		edStartTime.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				timePikerDialog(R.id.tpCreateTripTimePicker,
						R.id.btnDoneCreateTripTimePiker,
						R.id.btnCancelCreateTripTimePiker, edStartTime,
						R.layout.time_picker, R.string.titleTimeDialog);
			}
		});
		edDayEnd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				datePikerDialog(R.id.dpCreateDatePicker,
						R.id.btnDoneCreateTripDatePiker,
						R.id.btnCancelCreateTripDatePiker, edDayEnd,
						R.layout.date_picker, R.string.titleTimeDialog);
			}
		});
		edDayStart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				datePikerDialog(R.id.dpCreateDatePicker,
						R.id.btnDoneCreateTripDatePiker,
						R.id.btnCancelCreateTripDatePiker, edDayStart,
						R.layout.date_picker, R.string.titleTimeDialog);
			}
		});
		edDateOffline.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				datePikerDialog(R.id.dpCreateDatePicker,
						R.id.btnDoneCreateTripDatePiker,
						R.id.btnCancelCreateTripDatePiker, edDateOffline,
						R.layout.date_picker, R.string.titleTimeDialog);
			}
		});
		edTimePlace.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// timePlacePikerDialog();
				timePikerDialog(R.id.tpCreateTripTimePicker,
						R.id.btnDoneCreateTripTimePiker,
						R.id.btnCancelCreateTripTimePiker, edTimePlace,
						R.layout.time_picker, R.string.titleTimeDialog);
			}

		});
	}

	public void datePikerDialog(int datePickerID, int btnDoneID,
			int btnCancelID, final TextView tv, int Layout, int dialogTitle) {
		final Dialog dialog = new Dialog(this);
		dialog.setContentView(Layout);
		dialog.setTitle(dialogTitle);
		dialog.setCancelable(true);
		final DatePicker dpDatePK = (DatePicker) dialog
				.findViewById(datePickerID);

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
				// TextView edTimePlace = (TextView) findViewById(textID);
				String res = dpDatePK.getYear() + "";
				int month = dpDatePK.getMonth() + 1;
				int date = dpDatePK.getDayOfMonth();
				if (month < 10) {
					res += "-0" + month;
				} else {
					res += "-" + month;
				}
				if (date < 10) {
					res += "-0" + date;
				} else {
					res += "-" + date;
				}

				tv.setText(res);
				dialog.dismiss();
			}
		});
		// Show Dialog
		dialog.show();
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
				tv.setText(res);
				dialog.dismiss();
			}
		});
		// Show Dialog
		dialog.show();
	}

	@SuppressLint("InflateParams")
	public void displayAlertDialog() {
		LayoutInflater inflater = getLayoutInflater();
		View alertLayout = inflater.inflate(R.layout.choose_place_maps, null);

		ImageButton btnImDeletePlace = (ImageButton) alertLayout
				.findViewById(R.id.btnImDeletePlace);
		ImageButton btnImAddPlace = (ImageButton) alertLayout
				.findViewById(R.id.btnImAddPlace);

		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setView(alertLayout);
		MapFragment mapFragment = (MapFragment) getFragmentManager()
				.findFragmentById(R.id.map);
		mapFragment.getMapAsync(this);

		startPlace = exeQ.getTinhByTentinh(spStartPlace.getSelectedItem()
				.toString());
		endPlace = exeQ.getTinhByTentinh(spEndPlace.getSelectedItem()
				.toString());
		listPlace = exeQ.getAllDiemphuotBy2MaTinh(startPlace.getMaTinh(),
				endPlace.getMaTinh());

		mMap = mapFragment.getMap();
		mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		mMap.getUiSettings().setMyLocationButtonEnabled(true);
		mMap.setMyLocationEnabled(true);
		mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
				new LatLng(Double.parseDouble(startPlace.getLat()), Double
						.parseDouble(startPlace.getLon())), 7));

		showMakerFirst();

		mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

			@Override
			public boolean onMarkerClick(Marker m) {
				markerTouch = m;
				m.showInfoWindow();
				return false;
			}
		});

		mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

			@Override
			public void onMapClick(LatLng arg0) {
				// TODO Auto-generated method stub
				listTouch = new ArrayList<Diemphuot>();
				mMap.clear();
				// Loop for searching Diemphuot
				for (Diemphuot dp : listAll) {
					LatLng dpLL = new LatLng(Double.parseDouble(dp.getLat()),
							Double.parseDouble(dp.getLat()));
					double distance = CalculationByDistance(arg0, dpLL);
					// 5km
					if ((distance > 0) && (distance < 0.001)) {
						mMap.addMarker(new MarkerOptions()
								.position(
										new LatLng(Double.parseDouble(dp
												.getLat()), Double
												.parseDouble(dp.getLon())))
								.title(dp.getTenDiemphuot())
								.icon(BitmapDescriptorFactory
										.fromResource(R.drawable.marker_touch))
								.snippet(dp.getMaDiemphuot()));
						listTouch.add(dp);
					}
				}
				showMarkerTouch();
				showMakerFirst();
				showMarkerChoice();
			}
		});

		btnImDeletePlace.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				for (Diemphuot dp : listCreate) {
					if (dp.getMaDiemphuot().equals(markerTouch.getSnippet())) {
						listCreate.remove(dp);
						break;
					}
				}
				mMap.clear();
				showMakerFirst();
				showMarkerChoice();
			}
		});

		btnImAddPlace.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (markerTouch != null
						&& markerTouch.getPosition().latitude != Double
								.parseDouble(startPlace.getLat())
						&& markerTouch.getPosition().longitude != Double
								.parseDouble(endPlace.getLat())) {
					boolean a = true;
					for (Diemphuot dp : listCreate) {
						if (dp.getMaDiemphuot()
								.equals(markerTouch.getSnippet())) {
							a = !a;
						}
					}
					if (a) {
						for (Diemphuot dp : listAll) {
							if (dp.getMaDiemphuot().equals(
									markerTouch.getSnippet())) {
								listCreate.add(dp);
								break;
							}
						}
					}
				}
				mMap.clear();
				showMakerFirst();
				showMarkerChoice();
			}
		});

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

	private void showMarkerTouch() {
		// Diemphuot da touch
		for (Diemphuot dp : listTouch) {
			mMap.addMarker(new MarkerOptions()
					.position(
							new LatLng(Double.parseDouble(dp.getLat()), Double
									.parseDouble(dp.getLon())))
					.title(dp.getTenDiemphuot())
					.icon(BitmapDescriptorFactory
							.fromResource(R.drawable.marker_touch))
					.snippet(dp.getMaDiemphuot()));
		}
	}

	private void showMarkerChoice() {
		// Diemphuot da chon
		for (Diemphuot dp : listCreate) {
			mMap.addMarker(new MarkerOptions()
					.position(
							new LatLng(Double.parseDouble(dp.getLat()), Double
									.parseDouble(dp.getLon())))
					.title(dp.getTenDiemphuot())
					.icon(BitmapDescriptorFactory
							.fromResource(R.drawable.marker_choice))
					.snippet(dp.getMaDiemphuot()));
		}
	}

	private void showMakerFirst() {
		// End
		mMap.addMarker(new MarkerOptions()
				.position(
						new LatLng(Double.parseDouble(startPlace.getLat()),
								Double.parseDouble(startPlace.getLon())))
				.title(startPlace.getTenTinh())
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.marker_start_end_places))
				.snippet(startPlace.getMaTinh()));

		// Start
		mMap.addMarker(new MarkerOptions()
				.position(
						new LatLng(Double.parseDouble(endPlace.getLat()),
								Double.parseDouble(endPlace.getLon())))
				.title(endPlace.getTenTinh())
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.marker_start_end_places))
				.snippet(endPlace.getMaTinh()));

		// Diemphuot thuoc Start + end
		for (Diemphuot dp : listPlace) {
			mMap.addMarker(new MarkerOptions()
					.position(
							new LatLng(Double.parseDouble(dp.getLat()), Double
									.parseDouble(dp.getLon())))
					.title(dp.getTenDiemphuot())
					.icon(BitmapDescriptorFactory
							.fromResource(R.drawable.marker_touch))
					.snippet(dp.getMaDiemphuot()));
		}
	}

	public boolean checkEditText() {
		if (edTripName.getText().toString().isEmpty()) {
			edTripName.setError(getString(R.string.checknull));
			edTripName.requestFocus();
			return true;
		}
		if (edDayStart.getText().toString().isEmpty()) {
			edDayStart.setError(getString(R.string.checknull));
			edDayStart.requestFocus();
			return true;
		}
		if (edDayEnd.getText().toString().isEmpty()) {
			edDayEnd.setError(getString(R.string.checknull));
			edDayEnd.requestFocus();
			return true;
		}
		if (edStartTime.getText().toString().isEmpty()) {
			edStartTime.setError(getString(R.string.checknull));
			edStartTime.requestFocus();
			return true;
		}
		if (edEndTime.getText().toString().isEmpty()) {
			edEndTime.setError(getString(R.string.checknull));
			edEndTime.requestFocus();
			return true;
		}
		if (edKinhPhi.getText().toString().isEmpty()) {
			edKinhPhi.setError(getString(R.string.checknull));
			edKinhPhi.requestFocus();
			return true;
		}
		if (edDateOffline.getText().toString().equals("") || edOfflineTime.getText().toString().equals("")
				|| edPlaceOffline.getText().toString().equals("")) {
			if (edDateOffline.getText().toString().isEmpty()) {
				edDateOffline.setError(getString(R.string.checknull));
				edDateOffline.requestFocus();
				return true;
			}
			if (edOfflineTime.getText().toString().isEmpty()) {
				edOfflineTime.setError(getString(R.string.checknull));
				edOfflineTime.requestFocus();
				return true;
			}
			if (edPlaceOffline.getText().toString().isEmpty()) {
				edPlaceOffline.setError(getString(R.string.checknull));
				edPlaceOffline.requestFocus();
				return true;
			}
		}

		if (edTimePlace.getText().toString().isEmpty()) {
			edTimePlace.setError(getString(R.string.checknull));
			edTimePlace.requestFocus();
			return true;
		}
		if (edPlaceStart.getText().toString().isEmpty()) {
			edPlaceStart.setError(getString(R.string.checknull));
			edPlaceStart.requestFocus();
			return true;
		}

		return false;
	}

	public boolean validatorButton() {
		boolean check = true;
		if (checkEditText()) {
			check = false;
		}
		if (edDayStart.getText().toString().compareTo(dateNow) < 0) {
			edDayStart.setError(getString(R.string.error_dateNowTrip));
			edDayStart.requestFocus();
			check = false;
		}
		if (edDayStart.getText().toString()
				.compareTo(edDayEnd.getText().toString()) == 0) {
			if (edStartTime.getText().toString()
					.compareTo(edEndTime.getText().toString()) >= 0) {
				edEndTime.setError(getString(R.string.error_time));
				edEndTime.requestFocus();
				check = false;
			}
		} else {
			if (edDayStart.getText().toString()
					.compareTo(edDayEnd.getText().toString()) > 0) {
				edDayEnd.setError(getString(R.string.error_dateTrip));
				edDayEnd.requestFocus();
				check = false;
			}
		}
		if (edDateOffline.getText().toString().equals("") || edOfflineTime.getText().toString().equals("")
				|| edPlaceOffline.getText().toString().equals("")) {
			if (edDateOffline.getText().toString().compareTo(dateNow) < 0) {
				edDateOffline.setError(getString(R.string.error_DateOffTrip));
				edDateOffline.requestFocus();
				check = false;
			}
			if (edDateOffline.getText().toString()
					.compareTo(edDayStart.getText().toString()) == 0) {
				if (edOfflineTime.getText().toString()
						.compareTo(edStartTime.getText().toString()) >= 0) {
					edOfflineTime
							.setError(getString(R.string.error_TimeOffTrip));
					edOfflineTime.requestFocus();
					check = false;
				}
			} else {
				if (edDateOffline.getText().toString()
						.compareTo(edDayStart.getText().toString()) > 0) {
					edDateOffline
							.setError(getString(R.string.error_TimeOffTrip));
					edDateOffline.requestFocus();
					check = false;
				}
			}
		}
		if (edTimePlace.getText().toString()
				.compareTo(edStartTime.getText().toString()) > 0) {
			edTimePlace.setError(getString(R.string.error_TimeOffPlace));
			edTimePlace.requestFocus();
			check = false;
		}
		return check;
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

	private void removeFragmentMaps() {
		FragmentManager fm = getFragmentManager();
		Fragment fragment = (fm.findFragmentById(R.id.map));
		FragmentTransaction ft = fm.beginTransaction();
		ft.remove(fragment);
		ft.commit();
	}

	public class ImageDialog extends Dialog implements View.OnClickListener {
		private final TextView tvFromCamera;
		private final TextView tvFromGallery;
		Context mContext;

		public ImageDialog(Context context, int resource) {
			super(context);
			mContext = context;
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setCancelable(true);
			setContentView(resource);
			tvFromCamera = (TextView) findViewById(R.id.tvFromCamera);
			tvFromGallery = (TextView) findViewById(R.id.tvFromGallery);
			tvFromCamera.setOnClickListener(this);
			tvFromGallery.setOnClickListener(this);
			getWindow().setLayout((int) (screenSize.x * 0.95),
					ViewGroup.LayoutParams.WRAP_CONTENT);

			WindowManager.LayoutParams wmlp = getWindow().getAttributes();

			wmlp.gravity = Gravity.TOP | Gravity.LEFT;
			wmlp.x = 100; // x position
			wmlp.y = 100; // y position
			// getWindow().getDecorView().setBackgroundResource(0);
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.tvFromGallery:
				// Intent cameraIntent = new Intent();
				// cameraIntent.setType("image/*");
				// cameraIntent.setAction(Intent.ACTION_GET_CONTENT);
				// startActivityForResult(Intent.createChooser(cameraIntent,
				// "Complete action using"), PICK_FROM_FILE);
				// Create intent to Open Image applications like Gallery, Google
				// Photos
				Intent galleryIntent = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				// Start the Intent
				startActivityForResult(galleryIntent, PICK_FROM_FILE);
				this.cancel();
				break;
			case R.id.tvFromCamera:
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				Global.createFolderDULIBU();
				fromCameraFile = new File(
						Environment.getExternalStorageDirectory() + "/"
								+ Global.DULIBU, "tmp_avatar_"
								+ String.valueOf(System.currentTimeMillis())
								+ ".jpg");
				mImageCaptureUri = Uri.fromFile(fromCameraFile);
				try {
					intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
							mImageCaptureUri);
					intent.putExtra("return-data", true);
					startActivityForResult(intent, PICK_FROM_CAMERA);
				} catch (Exception e) {
					e.printStackTrace();
				}
				this.cancel();
				break;
			}
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == this.RESULT_OK) {
			if (requestCode == PICK_FROM_FILE) {
				Uri selectedImage = data.getData();
				String[] filePathColumn = { MediaStore.Images.Media.DATA };

				// Get the cursor
				Cursor cursor = this.getContentResolver().query(selectedImage,
						filePathColumn, null, null, null);
				// Move to first row
				cursor.moveToFirst();

				int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
				String imgDecodableString = cursor.getString(columnIndex);
				cursor.close();
				// Set the Image in ImageView after decoding the String
				// ivProfile.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));
				btnChooseImage.setBackground(new BitmapDrawable(getResources(),
						BitmapFactory.decodeFile(imgDecodableString)));
				btnChooseImage.setText("");
				fromGallery = new File(imgDecodableString, "tmp_avatar_"
						+ String.valueOf(System.currentTimeMillis()) + ".jpg");
				fromCameraFile = null;

			} else {
				path = mImageCaptureUri.getPath();
			}
			if (fromCameraFile != null) {
				Bitmap bm = ImageUltiFunctions.decodeSampledBitmapFromFile(
						fromCameraFile, 500, 500);
				// ivProfile.setImageBitmap(bm);
				BitmapDrawable bitmapDrawable = new BitmapDrawable(
						getResources(), bm);
				btnChooseImage.setBackground(bitmapDrawable);
				btnChooseImage.setText("");
				fromGallery = null;
			}

		}
	}

	private double CalculationByDistance(LatLng StartP, LatLng EndP) {
		double lat1 = StartP.latitude / 1E6;
		double lat2 = EndP.latitude / 1E6;
		double lon1 = StartP.longitude / 1E6;
		double lon2 = EndP.longitude / 1E6;
		double dLat = Math.toRadians(lat2 - lat1);
		double dLon = Math.toRadians(lon2 - lon1);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
				+ Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
				* Math.sin(dLon / 2);
		double c = 2 * Math.asin(Math.sqrt(a));
		return 6371.00 * c;
	}

	private void createNewTrip(String nameTrip, String startPlace,
			String endPlace, String startDay, String endDay, String startTime,
			String endTime, String expense, String dateOffline,
			String timeOffline, String placeOffline, String timeTaptrung,
			String PlaceTaptrung, String Notes) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		if (fromCameraFile == null) {
			try {
				params.put("image", fromGallery);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		if (fromGallery == null) {
			try {
				params.put("image", fromCameraFile);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		params.put("name", nameTrip);
		params.put("begin_location", startPlace);
		params.put("end_location", endPlace);
		params.put("start_date", startDay);
		params.put("start_time", startTime);
		params.put("end_date", endDay);
		params.put("end_time", endTime);
		params.put("expense", expense);
		params.put("offline_time", dateOffline + " " + timeOffline);
		params.put("offline_position", placeOffline);
		params.put("gathering_time", startDay + " " + timeTaptrung);
		params.put("gathering_position", PlaceTaptrung);
		params.put("note", Notes);
		client.post(Global.BASE_URI + "/" + Global.URI_CREATENEWTRIP_PATH
				+ "?access_token=" + Global.getPreference(this, Global.ACCESS_TOKEN, "none"), params,
				new AsyncHttpResponseHandler() {
					public void onSuccess(String response) {
						Log.e("createNewTrip", response+"");

						if (executeWhenCreateSuccess(response)) {
							NoticeRegisSuccsess();

						}
					}

					@Override
					public void onFailure(int statusCode, Throwable error,
							String content) {
						NoticeRegisFalse();
						Log.e("false_send", content+"");
					}
				});
	}

	private void NoticeRegisSuccsess() {
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();

		// Setting Dialog Title
		alertDialog.setTitle("Thành công!");

		// Setting Dialog Message
		alertDialog.setMessage("Tạo chuyến đi thành công");

		// Setting Icon to Dialog
		alertDialog.setIcon(R.drawable.icon_tick);
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// do something!
				onBackPressed();
			}
		});

		// Showing Alert Message
		alertDialog.show();
	}

	private void NoticeRegisFalse() {
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();

		// Setting Dialog Title
		alertDialog.setTitle("Thất bại!");

		// Setting Dialog Message
		alertDialog.setMessage("Tạo chuyến đi thất bại!");

		// Setting Icon to Dialog
		alertDialog.setIcon(R.drawable.icon_error);
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// do something!
			}
		});

		// Showing Alert Message
		alertDialog.show();
	}

	private boolean executeWhenCreateSuccess(String response) {
		try {
			AsyncHttpClient client = new AsyncHttpClient();
			RequestParams params = new RequestParams();
			JSONObject userJson = new JSONObject(response);

			String _id = userJson.optString("_id");
			//send listdiem
			String path_lat = "", path_lon="";
			String comma = "";
			if (listCreate.size() > 0) {
				for (Diemphuot element : listCreate) {
					path_lat += comma + element.getLat();
					path_lon += comma + element.getLon();
					comma = ",";
				}
				params.put("_id", _id);
				params.put("path_lat", path_lat);
				params.put("path_lon", path_lon);
				client.post(Global.BASE_URI + "/" + Global.URI_UPDATETRIPLOCATIONS_PATH
						+ "?access_token=" + Global.getPreference(this, Global.ACCESS_TOKEN, "none"), params,
						new AsyncHttpResponseHandler() {
							public void onSuccess(String response) {
								Log.e("send list", response);
								
							}

							@Override
							public void onFailure(int statusCode, Throwable error,
									String content) {
								Log.e("false_send_list", content);
							}
						});
				
			}
			return true;
		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}
	}
}
