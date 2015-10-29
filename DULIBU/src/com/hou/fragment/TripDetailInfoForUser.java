package com.hou.fragment;

import java.io.File;
import java.security.NoSuchAlgorithmException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hou.app.Global;
import com.hou.dulibu.ChiTieu_Activity;
import com.hou.dulibu.Offline_Activity;
import com.hou.dulibu.R;
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

import android.app.Activity;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class TripDetailInfoForUser extends Fragment implements OnClickListener {
	TextView tvKinhPhi, tvTitleTrip, tvleader, tvTimeTrip, tvTimeStart,
			tvKinhPhis, tvNotes, tvMoneyTrip, tvLotrinh;
	Button btnJoinUser, btnLeaveUser;
	ImageView ivTripBG;

	private Lichtrinh lichtrinh;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.trip_detail_info_for_user,
				container, false);
		findViewById(v);
		try {
			lichtrinh = (Lichtrinh) Global.readFileObject(new MD5()
					.getMD5("4/4/1994"));
		} catch (Exception e) {
			Intent intent = new Intent(getActivity(),
					SplashScreenActivity.class);
			startActivity(intent);
		}
		LoadDataInfo();

		if (Global.getPreference(getActivity(), "check_role_1", "0")
				.equals("1")) {
			btnJoinUser.setVisibility(View.INVISIBLE);
			btnLeaveUser.setVisibility(View.VISIBLE);
		}
		btnJoinUser.setOnClickListener(this);
		btnLeaveUser.setOnClickListener(this);

		return v;
	}

	public void findViewById(View v) {
		btnJoinUser = (Button) v.findViewById(R.id.btnJoin1);
		btnLeaveUser = (Button) v.findViewById(R.id.btnLeave1);

		tvTitleTrip = (TextView) v.findViewById(R.id.tvTitleTrip1);
		tvleader = (TextView) v.findViewById(R.id.tvleader1);
		tvTimeTrip = (TextView) v.findViewById(R.id.tvTimeTrip1);
		tvTimeStart = (TextView) v.findViewById(R.id.tvTimeStart1);
		tvKinhPhis = (TextView) v.findViewById(R.id.tvKinhPhis1);
		tvNotes = (TextView) v.findViewById(R.id.tvNotes1);
		tvMoneyTrip = (TextView) v.findViewById(R.id.tvMoneyTrip1);
		tvLotrinh = (TextView) v.findViewById(R.id.tvLotrinh1);
		ivTripBG = (ImageView) v.findViewById(R.id.ivTripBG1);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnJoin1:
			btnJoinUser.setVisibility(View.INVISIBLE);
			btnLeaveUser.setVisibility(View.VISIBLE);
			joinTrip(lichtrinh.getMaLichtrinh());
			break;
		case R.id.btnLeave1:
			btnJoinUser.setVisibility(View.VISIBLE);
			btnLeaveUser.setVisibility(View.INVISIBLE);
			leaveTrip(lichtrinh.getMaLichtrinh());
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
					.equals("1")) {
				btnJoinUser.setEnabled(false);
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
			File f;
			try {
				f = ImageUltiFunctions.getFileFromUri(Global.getURI(new MD5()
						.getMD5(dataTrip.getImage())));
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

	public void joinTrip(String _idTrip) {
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
						Log.d("false tham gia", "  "+content);
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
}
