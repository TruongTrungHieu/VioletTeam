package com.hou.fragment;

import com.hou.dulibu.R;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileFragment extends Fragment implements OnClickListener {
	private ProgressDialog pDialog;
	private Menu currentMenu;
	public static Point screenSize = new Point();
	LinearLayout lnImgInfo;
	ImageView ivProfile;
	TextView tvUserName;
	EditText etFullName, etUserName, etEmail, etBirthday, etPhone, etContact;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater
				.inflate(R.layout.profile_manager, container, false);
		getActivity().getWindowManager().getDefaultDisplay()
				.getSize(screenSize);

		findViewById(view);
		// showSlideImage(SlideImageArr);

		// initGridView(view);
		// showSlideImage(SlideImageArr);
		// initGridView(view);
		return view;
	}

	public void findViewById(View view) {
		lnImgInfo = (LinearLayout) view.findViewById(R.id.lnImgInfo);
		ivProfile = (ImageView) view.findViewById(R.id.ivProfile);
		tvUserName = (TextView) view.findViewById(R.id.tvUserName);
		etFullName = (EditText) view.findViewById(R.id.etFullName);
		etUserName = (EditText) view.findViewById(R.id.etUserName);
		etEmail = (EditText) view.findViewById(R.id.etEmail);
		etBirthday = (EditText) view.findViewById(R.id.etBirthday);
		etPhone = (EditText) view.findViewById(R.id.etPhone);
		etContact = (EditText) view.findViewById(R.id.etContact);
		
		
		ivProfile.setOnClickListener(this);
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
			// Toast.makeText(getActivity(), "Hello", Toast.LENGTH_LONG).show();
			break;
		case R.id.done_setting_actionbar:
			currentMenu.getItem(0).setVisible(true);
			currentMenu.getItem(1).setVisible(false);
			// Toast.makeText(getActivity(), "Hell", Toast.LENGTH_LONG).show();
			break;
		case R.id.setting:
			break;
		case R.id.messages:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ivProfile:

			break;
		}
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
			// getWindow().getDecorView().setBackgroundResource(0);
		}

		@Override
		public void onClick(View v) {
			// switch (v.getId()) {
			// case R.id.tvFromGallery:
			// Intent cameraIntent = new Intent();
			// cameraIntent.setType("image/*");
			// cameraIntent.setAction(Intent.ACTION_GET_CONTENT);
			// startActivityForResult(Intent.createChooser(cameraIntent,
			// "Complete action using"), PICK_FROM_FILE);
			// this.cancel();
			// break;
			// case R.id.tvFromCamera:
			// Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			// fromCameraFile = new File(
			// Environment.getExternalStorageDirectory(),
			// "tmp_avatar_"
			// + String.valueOf(System.currentTimeMillis())
			// + ".jpg");
			// mImageCaptureUri = Uri.fromFile(fromCameraFile);
			// try {
			// intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
			// mImageCaptureUri);
			// intent.putExtra("return-data", true);
			// startActivityForResult(intent, PICK_FROM_CAMERA);
			// } catch (Exception e) {
			// e.printStackTrace();
			// }
			// this.cancel();
			// break;
			// }
		}
	}
	//
	// @Override
	// public void onActivityResult(int requestCode, int resultCode, Intent
	// data) {
	// super.onActivityResult(requestCode, resultCode, data);
	// if (resultCode == getActivity().RESULT_OK) {
	// ivSelectedLogo.setOnClickListener(this);
	// if (requestCode == PICK_FROM_FILE) {
	// mImageCaptureUri = data.getData();
	// path = IntentUtils.getPath(getActivity(), mImageCaptureUri); //from
	// Gallery
	// fromCameraFile = new File(path);
	// } else {
	// path = mImageCaptureUri.getPath();
	// }
	// if (mImageCaptureUri != null && path.length() > 0) {
	// RelativeLayout.LayoutParams lp = new
	// RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
	// RelativeLayout.LayoutParams.MATCH_PARENT);
	// lp.setMargins(0, 0, 0, 0);
	// ivSelectedLogo.setLayoutParams(lp);
	// ivSelectedLogo.setScaleType(ImageView.ScaleType.CENTER_CROP);
	// File file = ImageUtils.RotateImageToFile(fromCameraFile, ivSelectedLogo,
	// getActivity());
	// path = file.getPath();
	// Bitmap bm = ImageUtils.RotateImage(fromCameraFile, ivSelectedLogo,
	// getActivity());
	// ivSelectedLogo.setImageBitmap(bm);
	// }
	// }
	// }
}
