package com.hou.fragment;

import java.io.File;
import java.security.NoSuchAlgorithmException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hou.app.Global;
import com.hou.dulibu.ChiTieu_Activity;
import com.hou.dulibu.LoginManagerActivity;
import com.hou.dulibu.Offline_Activity;
import com.hou.dulibu.R;
import com.hou.dulibu.RegisterManagerActivity;
import com.hou.dulibu.SplashScreenActivity;
import com.hou.dulibu.TripDetailManagerActivity;
import com.hou.dulibu.R.layout;
import com.hou.model.Lichtrinh;
import com.hou.ultis.ImageUltiFunctions;
import com.hou.upload.ImageDownloader;
import com.hou.upload.MD5;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class TripDetailInfoActivity extends Fragment implements OnClickListener {
	TextView tvKinhPhi, tvBtnOffline, tvTitleTrip, tvleader, tvTimeTrip,
			tvTimeStart, tvKinhPhis, tvNotes, tvMoneyTrip, tvLotrinh;
	Button btnInviteUser, btnLeaveUser;
	ImageView ivTripBG;
	private Lichtrinh lichtrinh;;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.trip_detail_info, container, false);
		findViewById(v);
		tvKinhPhi.setOnClickListener(this);
		tvBtnOffline.setOnClickListener(this);
		btnInviteUser.setOnClickListener(this);
		btnLeaveUser.setOnClickListener(this);
		try {
			lichtrinh = (Lichtrinh) Global.readFileObject(new MD5()
					.getMD5("4/4/1994"));
		} catch (Exception e) {
			Intent intent = new Intent(getActivity(),
					SplashScreenActivity.class);
			startActivity(intent);
		}
		LoadDataInfo();

		return v;
	}

	public void findViewById(View v) {
		tvKinhPhi = (TextView) v.findViewById(R.id.tvKinhPhi);
		tvBtnOffline = (TextView) v.findViewById(R.id.tvBtnOffline);
		btnInviteUser = (Button) v.findViewById(R.id.btnInvite);
		btnLeaveUser = (Button) v.findViewById(R.id.btnLeave);

		tvTitleTrip = (TextView) v.findViewById(R.id.tvTitleTrip);
		tvleader = (TextView) v.findViewById(R.id.tvleader);
		tvTimeTrip = (TextView) v.findViewById(R.id.tvTimeTrip);
		tvTimeStart = (TextView) v.findViewById(R.id.tvTimeStart);
		tvKinhPhis = (TextView) v.findViewById(R.id.tvKinhPhis);
		tvNotes = (TextView) v.findViewById(R.id.tvNotes);
		tvMoneyTrip = (TextView) v.findViewById(R.id.tvMoneyTrip);
		tvLotrinh = (TextView) v.findViewById(R.id.tvLotrinh);
		ivTripBG = (ImageView) v.findViewById(R.id.ivTripBG);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tvKinhPhi:
			Intent i = new Intent(getActivity(), ChiTieu_Activity.class);
			startActivity(i);
			break;
		case R.id.btnInvite:
			inviteUser();
			break;
		case R.id.btnLeave:
			Global.savePreference(getActivity(), Global.USER_ROLE_CHANGE, "1");
			leaveTrip(Global.getPreference(getActivity(), Global.TRIP_TRIP_ID,
					"id"));
			getActivity().onBackPressed();
			break;
		case R.id.tvBtnOffline:
			Intent offline = new Intent(getActivity(), Offline_Activity.class);
			startActivity(offline);
			break;
		// Toast.makeText(getActivity(), "Offline Events",
		// Toast.LENGTH_SHORT).show();
		default:
			break;
		}
	}

	private void LoadDataInfo() {

		Lichtrinh dataTrip = lichtrinh;
		if (dataTrip != null) {

			if (Global.getPreference(getActivity(), "check_role_1", " ")
					.equals(Global.USER_ROLE_ADMIN)) {
				btnLeaveUser.setText("Admin");
				btnLeaveUser.setEnabled(false);
			}
			tvTitleTrip.setText(dataTrip.getTenLichtrinh());
			tvLotrinh.setText(dataTrip.getDiemBatdau() + " - "
					+ dataTrip.getDiemKetthuc());
			tvleader.setText(dataTrip.getAdmin());
			tvTimeTrip.setText(dataTrip.getTgBatdau() + " - "
					+ dataTrip.getTgKetthuc());
			tvTimeStart.setText(dataTrip.getThoigian_xuatphat() + " - \n"
					+ dataTrip.getDiadiem_xuatphat());
			tvMoneyTrip.setText(dataTrip.getChiphicanhan() + "");
			Global.savePreference(getActivity(), Global.TRIP_MONEY,
					dataTrip.getChiphicanhan() + "");
			tvNotes.setText(dataTrip.getNote());

			try {
				File f = ImageUltiFunctions.getFileFromUri(Global
						.getURI(new MD5().getMD5(dataTrip.getImage())));
				if (f != null) {
					Bitmap b = ImageUltiFunctions.decodeSampledBitmapFromFile(
							f, 500, 500);
					ivTripBG.setImageBitmap(b);
				} else {
					ivTripBG.setImageResource(R.drawable.default_bg_detail);
				}
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void inviteUser(String _idTrip) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("id", _idTrip);
		client.post(
				Global.BASE_URI
						+ "/"
						+ Global.TRIP_REGISTER
						+ "?access_token="
						+ Global.getPreference(getActivity(),
								Global.USER_ACCESS_TOKEN, " "), params,
				new AsyncHttpResponseHandler() {
					public void onSuccess(String response) {
						Log.e("DATA", response);

					}

					@Override
					public void onFailure(int statusCode, Throwable error,
							String content) {
						switch (statusCode) {
						case 400:
							Toast.makeText(getActivity(),
									getResources().getString(R.string.e400),
									Toast.LENGTH_LONG).show();
							break;
						case 403:
							Toast.makeText(getActivity(),
									getResources().getString(R.string.e403),
									Toast.LENGTH_LONG).show();
							break;
						case 404:
							Toast.makeText(getActivity(),
									getResources().getString(R.string.e404),
									Toast.LENGTH_LONG).show();
							break;
						case 503:
							Toast.makeText(getActivity(),
									getResources().getString(R.string.e503),
									Toast.LENGTH_LONG).show();
							break;
						default:
							break;
						}
					}
				});
	}

	public void leaveTrip(String _idTrip) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("id", _idTrip);
		client.post(
				Global.BASE_URI
						+ "/"
						+ Global.TRIP_LEAVE
						+ "?access_token="
						+ Global.getPreference(getActivity(),
								Global.USER_ACCESS_TOKEN, " "), params,
				new AsyncHttpResponseHandler() {
					public void onSuccess(String response) {
						Log.e("DATA", response);

					}

					@Override
					public void onFailure(int statusCode, Throwable error,
							String content) {
						switch (statusCode) {
						case 400:
							Toast.makeText(getActivity(),
									getResources().getString(R.string.e400),
									Toast.LENGTH_LONG).show();
							break;
						case 403:
							Toast.makeText(getActivity(),
									getResources().getString(R.string.e403),
									Toast.LENGTH_LONG).show();
							break;
						case 404:
							Toast.makeText(getActivity(),
									getResources().getString(R.string.e404),
									Toast.LENGTH_LONG).show();
							break;
						case 503:
							Toast.makeText(getActivity(),
									getResources().getString(R.string.e503),
									Toast.LENGTH_LONG).show();
							break;
						default:
							break;
						}
					}
				});
	}

	@SuppressLint("InflateParams")
	private void inviteUser() {

		LayoutInflater inflater = getActivity().getLayoutInflater();
		View alertLayout = inflater.inflate(R.layout.dialog_active_register,
				null);
		AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
		final EditText txtActiveCode = (EditText) alertLayout
				.findViewById(R.id.edActiveCode);
		final TextView tvForget = (TextView) alertLayout
				.findViewById(R.id.tvCaption);
		tvForget.setText(getString(R.string.title_invite));
		txtActiveCode.setHint(R.string.hintDialogForget);
		alert.setView(alertLayout);
		alert.setCancelable(false);
		alert.setTitle(getString(R.string.title_confirm));
		alert.setNegativeButton(getString(R.string.confim_cancel), null);

		alert.setPositiveButton(getString(R.string.invite), null);
		AlertDialog dialog = alert.create();
		dialog.setOnShowListener(new DialogInterface.OnShowListener() {

			@Override
			public void onShow(final DialogInterface dialog) {

				Button b = ((AlertDialog) dialog)
						.getButton(AlertDialog.BUTTON_POSITIVE);
				b.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View view) {
						// TODO Do something
						if (!Global.isValidEmail(txtActiveCode.getText()
								.toString())) {
							txtActiveCode
									.setError(getString(R.string.register_err_email));
							txtActiveCode.requestFocus();
						} else {
							InviteUser(txtActiveCode.getText().toString());
							dialog.dismiss();
							return;
						}
					}
				});
			}
		});
		dialog.show();
	}

	private void InviteUser(String txtEmail) {
		// TODO Auto-generated method stub
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("email", txtEmail);
		params.put("id", Global.getPreference(getActivity(),
				Global.TRIP_TRIP_ID, "_id_trip"));
		client.post(
				Global.BASE_URI
						+ "/"
						+ Global.TRIP_INVITE
						+ "?access_token="
						+ Global.getPreference(getActivity(),
								Global.USER_ACCESS_TOKEN, "access_token"),
				params, new AsyncHttpResponseHandler() {
					public void onSuccess(String response) {
						Log.e("Invite", response);
						NoticeRegisSuccsess(getString(R.string.success),
								getString(R.string.invite_success));

					}

					@Override
					public void onFailure(int statusCode, Throwable error,
							String content) {
						Log.e("ERROR_INVITE", content);
						NoticeRegisFalse(getString(R.string.ConfirmFalse),
								getString(R.string.invite_false));
					}
				});

	}

	private void NoticeRegisSuccsess(String title, String content) {
		AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
				.create();

		// Setting Dialog Title
		alertDialog.setTitle(title);

		// Setting Dialog Message
		alertDialog.setMessage(content);

		// Setting Icon to Dialog
		alertDialog.setIcon(R.drawable.icon_tick);
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// do something!
			}
		});

		// Showing Alert Message
		alertDialog.show();
	}

	private void NoticeRegisFalse(String title, String content) {
		AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
				.create();

		// Setting Dialog Title
		alertDialog.setTitle(title);

		// Setting Dialog Message
		alertDialog.setMessage(content);

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
}
