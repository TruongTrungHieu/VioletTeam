package com.hou.fragment;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import com.hou.dulibu.R;
import com.hou.dulibu.SettingActivity;
import com.hou.model.Trangthai_User;
import com.hou.ultis.CircleImageView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileFragment extends Fragment implements OnClickListener {
	private ProgressDialog pDialog;
	private Menu currentMenu;
	public static Point screenSize = new Point();
	RelativeLayout lnImgInfo;
	ImageView ivProfile;
	TextView tvUserName, tvStatus;
	EditText etFullName, etUserName, etEmail, etBirthday, etPhone, etContact;
	private CircleImageView ivStatus;
	List<Trangthai_User> statusList;

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

		initView(view);
		statusList = new ArrayList<Trangthai_User>();
		statusList.add(new Trangthai_User("1", "Tot", "brown"));
		statusList.add(new Trangthai_User("2", "Kha", "green"));
		statusList.add(new Trangthai_User("3", "Trung binh", "orange"));
		statusList.add(new Trangthai_User("4", "Yeu", "pink"));
		return view;
	}

	public void initView(View view) {
		lnImgInfo = (RelativeLayout) view.findViewById(R.id.rlImgInfo);
		ivProfile = (ImageView) view.findViewById(R.id.ivProfile);
		tvUserName = (TextView) view.findViewById(R.id.tvUserName);
		etFullName = (EditText) view.findViewById(R.id.etFullName);
		ivStatus = (CircleImageView) view.findViewById(R.id.ivStatus);
		tvStatus = (TextView) view.findViewById(R.id.tvStatus);

		etEmail = (EditText) view.findViewById(R.id.etEmail);
		etBirthday = (EditText) view.findViewById(R.id.etBirthday);
		etPhone = (EditText) view.findViewById(R.id.etPhone);
		etContact = (EditText) view.findViewById(R.id.etContact);

		ivProfile.setOnClickListener(this);
		ivStatus.setOnClickListener(this);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		inflater.inflate(R.menu.fragment_profile, menu);
		currentMenu = menu;
		currentMenu.getItem(2).setVisible(true);
		currentMenu.getItem(3).setVisible(false);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();
		switch (id) {
		case R.id.editting_actionbar:
			currentMenu.getItem(2).setVisible(false);
			currentMenu.getItem(3).setVisible(true);
			// Toast.makeText(getActivity(), "Hello", Toast.LENGTH_LONG).show();
			break;
		case R.id.done_setting_actionbar:
			currentMenu.getItem(2).setVisible(true);
			currentMenu.getItem(3).setVisible(false);
			// Toast.makeText(getActivity(), "Hell", Toast.LENGTH_LONG).show();
			break;
		case R.id.setting:
			Intent intent = new Intent(getActivity(), SettingActivity.class);
			startActivity(intent);
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
		case R.id.ivStatus:
			listDialog();
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

			WindowManager.LayoutParams wmlp = getWindow().getAttributes();

			wmlp.gravity = Gravity.TOP | Gravity.LEFT;
			wmlp.x = 100; // x position
			wmlp.y = 100; // y position
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
					ivStatus.setBackgroundResource(R.drawable.item_brown);
					ivStatus.setImageResource(R.drawable.icon_about);
					tvStatus.setText(statusList.get(arg2).getTenTrangthai());
					dialog.dismiss();
					break;
				case 1:
					ivStatus.setBackgroundResource(R.drawable.item_green);
					ivStatus.setImageResource(R.drawable.icon_address);
					tvStatus.setText(statusList.get(arg2).getTenTrangthai());
					dialog.dismiss();
					break;
				case 2:
					ivStatus.setBackgroundResource(R.drawable.item_orange);
					ivStatus.setImageResource(R.drawable.icon_bday);
					tvStatus.setText(statusList.get(arg2).getTenTrangthai());
					dialog.dismiss();
					break;
				case 3:
					ivStatus.setBackgroundResource(R.drawable.item_pink);
					ivStatus.setImageResource(R.drawable.icon_heart);
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

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(context.LAYOUT_INFLATER_SERVICE);
			StatusHolder holder;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.tesst, parent, false);
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
			holder.icon = (CircleImageView) view.findViewById(R.id.ivStatus);
		}

		public void setData(StatusHolder holder, Trangthai_User status) {
			holder.name.setText(status.getTenTrangthai());
			switch (status.getGhichu()) {
			case "brown":
				holder.icon.setBackgroundResource(R.drawable.item_brown);
				holder.icon.setImageResource(R.drawable.icon_about);
				break;
			case "green":
				holder.icon.setBackgroundResource(R.drawable.item_green);
				holder.icon.setImageResource(R.drawable.icon_address);
				break;
			case "orange":
				holder.icon.setBackgroundResource(R.drawable.item_orange);
				holder.icon.setImageResource(R.drawable.icon_bday);
				break;
			case "pink":
				holder.icon.setBackgroundResource(R.drawable.item_pink);
				holder.icon.setImageResource(R.drawable.icon_heart);
				break;
			default:
				holder.icon.setBackgroundResource(R.drawable.item_brown);
				holder.icon.setImageResource(R.drawable.item_brown);
				break;
			}
		}
	}

	public static class StatusHolder {
		public TextView name;
		public CircleImageView icon;
	}
}
