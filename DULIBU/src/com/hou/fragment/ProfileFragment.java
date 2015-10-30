package com.hou.fragment;

import it.neokree.materialnavigationdrawer.elements.MaterialAccount;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.hou.app.Global;
import com.hou.dulibu.ProfileManagerActivity;
import com.hou.dulibu.R;
import com.hou.dulibu.SettingActivity;
import com.hou.dulibu.RegisterManagerActivity.DatePickerFragment;
import com.hou.model.Trangthai_User;
import com.hou.ultis.CircularImageView;
import com.hou.ultis.ImageUltiFunctions;
import com.hou.upload.ImageDownloader;
import com.hou.upload.MD5;
import com.hou.upload.imageOnServer;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileFragment extends Fragment implements OnClickListener {
	private Menu currentMenu;
	public static Point screenSize = new Point();
	RelativeLayout lnImgInfo;
	CircularImageView ivProfile;
	TextView tvUserName, tvStatus;
	EditText etFullName, etUserName, etEmail;
	static EditText etBirthday;
	EditText etPhone;
	EditText etContact;
	private CircularImageView ivStatus;
	List<Trangthai_User> statusList;
	private static final int PICK_FROM_CAMERA = 1;
	private static final int PICK_FROM_FILE = 2;
	private File fromCameraFile;
	private Uri mImageCaptureUri;
	private String path;
	private static String ngaysinh = "";
	private ImageDownloader downloader;
	private static Bitmap bmp;
	String pathAvartar = "";
	private String email, birthday, contact, fullname, phone;
	private String email2, birthday2, contact2, fullname2, phone2;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		currentData();
		// TODO Auto-generated method stub
		View view = inflater
				.inflate(R.layout.profile_manager, container, false);
		getActivity().getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		getActivity().getWindowManager().getDefaultDisplay()
				.getSize(screenSize);
		pathAvartar = Global.getPreference(getActivity(), Global.USER_AVATAR,
				"");
		initView(view);
		statusList = new ArrayList<Trangthai_User>();
		statusList.add(new Trangthai_User("1", "An toàn", "brown"));
		statusList.add(new Trangthai_User("2", "Nguy hiểm", "green"));
		File f = ImageUltiFunctions.getFileFromUri(Global
				.getURI(getFileName(pathAvartar)));
		if (f != null) {
			Bitmap bm = ImageUltiFunctions.decodeSampledBitmapFromFile(f, 500,
					500);
			ivProfile.setImageBitmap(bm);
		} else {
			new getAvatarFromUser().execute(getFileName(pathAvartar),
					pathAvartar);
		}
		FillDataProfile();

		return view;
	}

	public String getFileName(String path) {
		String[] temp = path.split("/");

		return temp[temp.length - 1];
	}

	public class getAvatarFromUser extends AsyncTask<String, Void, Void> {
		String fileName;

		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub

			try {
				fileName = params[0];
				imageOnServer.downloadFileFromServer(params[0], params[1]);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			File file = ImageUltiFunctions.getFileFromUri(Global
					.getURI(fileName));
			Bitmap bm = ImageUltiFunctions.decodeSampledBitmapFromFile(file, 500, 500);
			ivProfile.setImageBitmap(bm);
			ProfileManagerActivity activity = (ProfileManagerActivity) getActivity();
			MaterialAccount account = activity.getAccount();
			account.setPhoto(bm);
			activity.setAccount(account);
		}
	}

	public void initView(View view) {
		lnImgInfo = (RelativeLayout) view.findViewById(R.id.rlImgInfo);
		ivProfile = (CircularImageView) view.findViewById(R.id.ivProfile);
		tvUserName = (TextView) view.findViewById(R.id.tvUserName);
		etFullName = (EditText) view.findViewById(R.id.etFullName);
		ivStatus = (CircularImageView) view.findViewById(R.id.ivStatus);
		tvStatus = (TextView) view.findViewById(R.id.tvStatus);

		etEmail = (EditText) view.findViewById(R.id.etEmail);
		etBirthday = (EditText) view.findViewById(R.id.etBirthday);
		etPhone = (EditText) view.findViewById(R.id.etPhone);
		etContact = (EditText) view.findViewById(R.id.etContact);

		ivProfile.setOnClickListener(this);
		ivStatus.setOnClickListener(this);
		etBirthday.setOnClickListener(this);
	}

	private void currentData() {
		email = Global.getPreference(getActivity().getApplicationContext(),
				Global.USER_EMAIL, "");
		birthday = Global.getPreference(getActivity().getApplicationContext(),
				Global.USER_NGAYSINH, "");
		contact = Global.getPreference(getActivity().getApplicationContext(),
				Global.USER_SDT_LIENHE, "");
		fullname = Global.getPreference(getActivity().getApplicationContext(),
				Global.USER_FULLNAME, "");
		phone = Global.getPreference(getActivity().getApplicationContext(),
				Global.USER_SDT, "");
	}

	

	private void FillDataProfile() {
		tvUserName.setText(Global.getPreference(getActivity(),
				Global.USER_FULLNAME, ""));
		etFullName.setText(Global.getPreference(getActivity(),
				Global.USER_FULLNAME, ""));
		etEmail.setText(Global.getPreference(getActivity(), Global.USER_EMAIL,
				""));
		etBirthday.setText(Global.getPreference(getActivity(),
				Global.USER_NGAYSINH, ""));
		etPhone.setText(Global
				.getPreference(getActivity(), Global.USER_SDT, ""));
		if (Global.getPreference(getActivity(), Global.USER_GIOITINH, "")
				.equalsIgnoreCase("1")) {
			tvUserName.setCompoundDrawablesWithIntrinsicBounds(
					R.drawable.icon_male, 0, 0, 0);
		} else {
			tvUserName.setCompoundDrawablesWithIntrinsicBounds(
					R.drawable.icon_female, 0, 0, 0);
			
		}
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		inflater.inflate(R.menu.fragment_profile, menu);
		currentMenu = menu;
		currentMenu.getItem(0).setVisible(true);
		currentMenu.getItem(1).setVisible(false);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		// TODO Auto-generated method stub
		int id = item.getItemId();
		switch (id) {
		case R.id.editting_actionbar:
			currentMenu.getItem(0).setVisible(false);
			currentMenu.getItem(1).setVisible(true);
			setEnable();
			InputMethodManager mgr = (InputMethodManager) getActivity()
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			mgr.showSoftInput(etFullName, InputMethodManager.SHOW_IMPLICIT);
			etFullName.requestFocus();
			email = etEmail.getText().toString().toLowerCase();
			birthday = etBirthday.getText().toString().toLowerCase();
			contact =  Global.getPreference(getActivity(),
					Global.USER_SDT_LIENHE, "");
			fullname = etFullName.getText().toString().toLowerCase();
			phone = etPhone.getText().toString().toLowerCase();
		
			break;
		case R.id.done_setting_actionbar:
			
			// setDisable();
			//etBirthday.setError(null);
			
			birthday2 = etBirthday.getText().toString().toLowerCase();
			
			fullname2 = etFullName.getText().toString().toLowerCase();
			phone2 = etPhone.getText().toString().toLowerCase();
			if (fullname2.equals("")) {
				etFullName.requestFocus();
				}
			else{
				if (birthday2.equals("")) {
					etBirthday.requestFocus();						
					}
				else{
					if (phone2.equals("")) {
						etPhone.requestFocus();						
					}
					else{
						if (checkValidateAge()) {			
							if(checkValidateChanged()){
								postChangeInfo(Global.getPreference(getActivity(), Global.USER_EMAIL,
										""), fullname2, birthday2,
										Global.getPreference(getActivity()
												.getApplicationContext(), Global.USER_GIOITINH,
												"1"), phone2, Global.getPreference(getActivity(), Global.USER_SDT_LIENHE,
														""));
								currentMenu.getItem(0).setVisible(true);
								currentMenu.getItem(1).setVisible(false);
								setDisable();
							}
							else{
								currentMenu.getItem(0).setVisible(true);
								currentMenu.getItem(1).setVisible(false);
								//FillDataProfile();
								setDisable();
							}
							
							
						} else {
							etBirthday.setError(getString(R.string.error_tuoi));
							etBirthday.requestFocus();
							//setDisable();
							FillDataProfile();
							
						}
					}
				}
			}
				
			
			
			
			
			
			
			

			// isDataChange();

			// if(isDataChange()){}
			// else{
			// Toast.makeText(getActivity(), "cho phep thay doi data ",
			// Toast.LENGTH_SHORT).show();
			// }
			break;
		/*
		 * case R.id.setting: Intent intent = new Intent(getActivity(),
		 * SettingActivity.class); startActivity(intent); break;
		 */
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ivProfile:
			new ImageDialog(getActivity(), R.layout.select_image_layout).show();
			break;
		case R.id.ivStatus:
			listDialog();
			break;
		case R.id.etBirthday:
			etBirthday.setError(null);
			showDatePickerDialog(v);
			etBirthday.setFocusableInTouchMode(true);
			etBirthday.setFocusable(true);
			break;
		}

	}

	public void showDatePickerDialog(View v) {
		DialogFragment newFragment = new DatePickerFragment();
		newFragment.show(getChildFragmentManager(), "datePicker");
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
			etBirthday.setText(ngaysinh);

		}
	}

	@SuppressWarnings("deprecation")
	private boolean checkValidateAge() {
		boolean check = true;

		//birthday2 = etBirthday.getText().toString().toLowerCase();
		
		
		int tuoi = 0;
		Calendar c = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",
				Locale.US);
		try {
			Date date = dateFormat.parse(birthday2);
			tuoi = (int) (c.get(Calendar.YEAR) - date.getYear());
			if (c.get(Calendar.MONTH) == date.getMonth()) {
				if (c.get(Calendar.DATE) >= date.getDate()) {
					tuoi = tuoi - 1900;
				} else {
					tuoi = tuoi - 1901;
				}
			} else {
				if (c.get(Calendar.MONTH) > date.getMonth()) {
					tuoi = tuoi - 1900;
				} else {
					tuoi = tuoi - 1901;
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (tuoi <= 15) {

			check = false;
		}

		return check;
	}
	private boolean checkValidateChanged() {
		boolean check = true;

		if(fullname.equals(fullname2) && birthday.equals(birthday2) && phone.equals(phone2) ){
			check = false;
		}
			
	


		return check;
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

	@SuppressWarnings("static-access")
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == getActivity().RESULT_OK) {
			if (requestCode == PICK_FROM_FILE) {
				Uri selectedImage = data.getData();
				String[] filePathColumn = { MediaStore.Images.Media.DATA };

				// Get the cursor
				Cursor cursor = getActivity().getContentResolver().query(
						selectedImage, filePathColumn, null, null, null);
				// Move to first row
				cursor.moveToFirst();

				int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
				String imgDecodableString = cursor.getString(columnIndex);
				cursor.close();
				// Set the Image in ImageView after decoding the String
				String fileName = getFileName(imgDecodableString);
				fromCameraFile = ImageUltiFunctions.getFileFromUri(Global
						.getURI(fileName));

			} else {
				path = mImageCaptureUri.getPath();
			}
			if (fromCameraFile != null) {
				Bitmap bm = ImageUltiFunctions.decodeSampledBitmapFromFile(
						fromCameraFile, 500, 500);
				ivProfile.setImageBitmap(bm);

				updateAva();
			}

		}
	}

	public void updateAva() {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		try {
			params.put("file", fromCameraFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		client.post(
				Global.URI_UPDATEAVATAR_PATH
						+ "?access_token="
						+ Global.getPreference(getActivity(),
								Global.USER_ACCESS_TOKEN, ""), params,
				new AsyncHttpResponseHandler() {
					public void onSuccess(String response) {
						Toast.makeText(
								getActivity(),
								getActivity().getResources().getString(
										R.string.update_ava_success),
								Toast.LENGTH_LONG).show();
						// new File().delete();

					}

					@Override
					public void onFailure(int statusCode, Throwable error,
							String content) {
						Toast.makeText(
								getActivity(),
								getActivity().getResources().getString(
										R.string.update_ava_false),
								Toast.LENGTH_LONG).show();
					}
				});
	}

	public void listDialog() {
		final Dialog dialog = new Dialog(getActivity());
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.getWindow().setBackgroundDrawableResource(
				android.R.color.transparent);
		dialog.setContentView(R.layout.list_status);
		ListView lv = (ListView) dialog.findViewById(R.id.listStatus);

		ListStatusAdapter adapter = new ListStatusAdapter(getActivity(), 0,
				statusList);
		lv.setAdapter(adapter);
		lv.setBackgroundResource(android.R.color.transparent);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				switch (arg2) {
				case 0:
					ivStatus.setBackgroundResource(R.drawable.item_pink);
					ivStatus.setImageResource(R.drawable.icon_fine);
					tvStatus.setText(statusList.get(arg2).getTenTrangthai());
					dialog.dismiss();
					break;
				case 1:
					ivStatus.setBackgroundResource(R.drawable.item_green);
					ivStatus.setImageResource(R.drawable.icon_address);
					tvStatus.setText(statusList.get(arg2).getTenTrangthai());
					dialog.dismiss();
					break;
				default:
					break;
				}
			}
		});
		WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
		wmlp.width = (int) (screenSize.x * 0.55);
		wmlp.gravity = Gravity.TOP | Gravity.LEFT;
		wmlp.x = ivStatus.getLeft();
		wmlp.y = ivStatus.getTop() + 230;

		dialog.getWindow().clearFlags(
				WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		dialog.show();
	}

	public class ListStatusAdapter extends ArrayAdapter<Trangthai_User> {
		Context context;
		List<Trangthai_User> list;

		public ListStatusAdapter(Context context, int resource,
				List<Trangthai_User> objects) {
			super(context, resource, objects);
			// TODO Auto-generated constructor stub
			this.context = context;
			this.list = objects;
		}

		@SuppressWarnings("static-access")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(context.LAYOUT_INFLATER_SERVICE);
			StatusHolder holder;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.item_status_user,
						parent, false);
				holder = new StatusHolder();
				initView(holder, convertView);
				convertView.setTag(holder);
			} else {
				holder = (StatusHolder) convertView.getTag();
			}
			setData(holder, list.get(position));
			return convertView;
		}

		public void initView(StatusHolder holder, View view) {
			holder.name = (TextView) view.findViewById(R.id.tvStatusName);
			holder.icon = (CircularImageView) view.findViewById(R.id.ivStatus);
		}

		public void setData(StatusHolder holder, Trangthai_User status) {
			holder.name.setText(status.getTenTrangthai());
			switch (status.getGhichu()) {
			case "brown":
				holder.icon.setBackgroundResource(R.drawable.item_pink);
				holder.icon.setImageResource(R.drawable.icon_fine);
				break;
			case "green":
				holder.icon.setBackgroundResource(R.drawable.item_green);
				holder.icon.setImageResource(R.drawable.icon_address);
				break;
			default:
				holder.icon.setBackgroundResource(R.drawable.item_brown);
				holder.icon.setImageResource(R.drawable.item_brown);
				break;
			}
		}
	}

	private void postChangeInfo(final String email, final String fullname,
			final String bday, final String gender, final String phone,
			final String phone_contact) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("email", email);
		params.put("fullname", fullname);
		params.put("bday", bday);
		params.put("gender", gender);
		params.put("phone", phone);
		params.put("phone_contact", phone_contact);

		client.post(
				Global.BASE_URI
						+ "/"
						+ Global.URI_UPDATEINFO_PATH
						+ "?access_token="
						+ Global.getPreference(getActivity(),
								Global.USER_ACCESS_TOKEN, " "), params,
				new AsyncHttpResponseHandler() {
					public void onSuccess(String response) {
						Log.e("postChangeInfo", response);

						Global.savePreference(getActivity()
								.getApplicationContext(), Global.USER_FULLNAME,
								fullname);
						Global.savePreference(getActivity()
								.getApplicationContext(), Global.USER_EMAIL,
								email);
						Global.savePreference(getActivity()
								.getApplicationContext(), Global.USER_NGAYSINH,
								bday);
						Global.savePreference(getActivity()
								.getApplicationContext(), Global.USER_SDT,
								phone);

					
						etPhone.setText(Global.getPreference(getActivity()
								.getApplicationContext(), Global.USER_SDT,
								fullname));
						etBirthday.setText(Global.getPreference(getActivity()
								.getApplicationContext(), Global.USER_NGAYSINH,
								fullname));
						etFullName.setText(Global.getPreference(getActivity()
								.getApplicationContext(), Global.USER_FULLNAME,
								fullname));
//						etUserName.setText(Global.getPreference(getActivity()
//								.getApplicationContext(), Global.USER_FULLNAME,
//								fullname));

					

					}

					@Override
					public void onFailure(int statusCode, Throwable error,
							String content) {
						Log.e("__Viethhh", content);
					}
				});
	}

	public static class StatusHolder {
		public TextView name;
		public CircularImageView icon;
	}

	public void setEnable() {
		etEmail.setEnabled(false);
		etBirthday.setEnabled(true);
		etContact.setEnabled(false);
		etFullName.setEnabled(true);
		etPhone.setEnabled(true);
	}

	public void setDisable() {
		etEmail.setEnabled(false);
		etBirthday.setEnabled(false);
		etContact.setEnabled(false);
		etFullName.setEnabled(false);
		etPhone.setEnabled(false);
	}
}