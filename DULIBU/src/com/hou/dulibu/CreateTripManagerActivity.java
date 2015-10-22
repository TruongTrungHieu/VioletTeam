package com.hou.dulibu;
import java.io.File;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

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
	private File fromCameraFile;
	private Uri mImageCaptureUri;
	private String path;
	Context context = this;

	Button btnCreatePlace, btnCreateTrip, btnChooseImage;
	// TimePicker tpTimePK;
	GoogleMap mMap;
	EditText edTripName, edKinhPhi, edPlaceStart;
	TextView edTimePlace, edDayStart, edDayEnd, edStartTime, edEndTime,
			edOfflineTime;

	private ArrayList<Diemphuot> listAll, listPlace;
	private ArrayList<Diemphuot> listTouch;
	private ArrayList<Diemphuot> listCreate;
	private Tinh_Thanhpho startPlace, endPlace;

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
		edTripName = (EditText) findViewById(R.id.txtNameTrip);
		edDayStart = (TextView) findViewById(R.id.txtStartDay);
		edDayEnd = (TextView) findViewById(R.id.txtEndDay);
		edKinhPhi = (EditText) findViewById(R.id.txtKinhphi);
		edTimePlace = (TextView) findViewById(R.id.txtTimePlace);
		edPlaceStart = (EditText) findViewById(R.id.txtPlaceStart);
		edStartTime = (TextView) findViewById(R.id.txtStartTime);
		edEndTime = (TextView) findViewById(R.id.txtEndTime);
		edOfflineTime = (TextView) findViewById(R.id.txtTimeOffline);

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
		 

		if (getSupportActionBar() != null) {
			// getSupportActionBar().setDisplayShowCustomEnabled(true);
			getSupportActionBar().setBackgroundDrawable(
					new ColorDrawable(Color.parseColor("#0aae44")));
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}

		exeQ = new ExecuteQuery(getApplicationContext());
		exeQ.createDatabase();
		exeQ.open();

		listCreate = new ArrayList<Diemphuot>();
		listPlace = new ArrayList<Diemphuot>();
		listTouch = new ArrayList<Diemphuot>();
		listAll = new ArrayList<Diemphuot>();
		listAll = exeQ.getAllDiemphuot();

		edTripName = (EditText) findViewById(R.id.txtNameTrip);
		edDayStart = (TextView) findViewById(R.id.txtStartDay);
		edDayEnd = (TextView) findViewById(R.id.txtEndDay);
		edKinhPhi = (EditText) findViewById(R.id.txtKinhphi);
		edTimePlace = (TextView) findViewById(R.id.txtTimePlace);
		edPlaceStart = (EditText) findViewById(R.id.txtPlaceStart);
		edStartTime = (TextView) findViewById(R.id.txtStartTime);
		edEndTime = (TextView) findViewById(R.id.txtEndTime);
		edOfflineTime = (TextView) findViewById(R.id.txtTimeOffline);

		// GPS when leave
		spStartPlace = (Spinner) findViewById(R.id.spStartPlace);
		spEndPlace = (Spinner) findViewById(R.id.spEndPlace);

		btnChooseImage = (Button) findViewById(R.id.btnChooseImage);
		btnChooseImage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new ImageDialog(context,
						R.layout.select_image_layout).show();
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
					Toast.makeText(getBaseContext(), "Done", Toast.LENGTH_SHORT)
							.show();
					Toast.makeText(getBaseContext(),
							"" + edTripName.getText().toString(),
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getBaseContext(),
							"Xem lại các thông tin đăng ký",
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
				int month = dpDatePK.getMonth() + 1;
				tv.setText(dpDatePK.getDayOfMonth() + "/" + month + "/"
						+ dpDatePK.getYear());
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
				tv.setText(tpTimePK.getCurrentHour() + ":"
						+ tpTimePK.getCurrentMinute());
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

	public void checkEditText() {
		if (edPlaceStart.getText().toString().isEmpty()) {
			edPlaceStart.setHint(R.string.textHintPlaceStart);
			edPlaceStart.requestFocus();
		}
		if (edTimePlace.getText().toString().isEmpty()) {
			edTimePlace.setHint(R.string.textHintTimeStart);
			edTimePlace.requestFocus();
		}
		if (edKinhPhi.getText().toString().isEmpty()) {
			edKinhPhi.setHint(R.string.textHintKinhPhi);
			edKinhPhi.requestFocus();
		}
		if (edDayEnd.getText().toString().isEmpty()) {
			edDayEnd.setHint(R.string.textHintDayEnd);
			edDayEnd.requestFocus();
		}
		if (edDayStart.getText().toString().isEmpty()) {
			edDayStart.setHint(R.string.textHintDayStart);
			edDayStart.requestFocus();
		}
		if (edTripName.getText().toString().isEmpty()) {
			edTripName.setHint(R.string.textHintTitleTrip);
			edTripName.requestFocus();
		}
	}

	public boolean validatorButton() {
		if (edTripName.getText().toString().isEmpty() == false
				&& edDayStart.getText().toString().isEmpty() == false
				&& edDayEnd.getText().toString().isEmpty() == false
				&& edKinhPhi.getText().toString().isEmpty() == false
				&& edTimePlace.getText().toString().isEmpty() == false
				&& edPlaceStart.getText().toString().isEmpty() == false) {
			return true;
		} else {
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
				btnChooseImage.setBackground(new BitmapDrawable(
						getResources(), BitmapFactory
								.decodeFile(imgDecodableString)));
				btnChooseImage.setText("");

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
			}

		}
	}
	public void showDatePickerDialog(View v) {
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
				ngaysinh = dateFormat
						.format(Global.getDateFromDatePicket(view));
			} catch (Exception e) {
				// TODO: handle exception
			}
			edtNgaysinh.setText(ngaysinh);

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
	
	private void sendDataToServer(String nameTrip){
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("nametrip", nameTrip);
	}
}
