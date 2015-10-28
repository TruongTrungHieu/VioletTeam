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
	TextView tvKinhPhi, tvBtnOffline, tvTitleTrip, tvleader, tvTimeTrip,
			tvTimeStart, tvKinhPhis, tvNotes, tvMoneyTrip, tvLotrinh;
	Button btnJoinUser, btnLeaveUser;
	ImageView ivTripBG;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.trip_detail_info_for_user, container, false);
		findViewById(v);
		tvBtnOffline.setOnClickListener(this);
		btnJoinUser.setOnClickListener(this);
		btnLeaveUser.setOnClickListener(this);
		LoadDataFromServer();
		if (Global.getPreference(getActivity(), "check_role_1", "0").equals("1")) {
			btnJoinUser.setVisibility(View.INVISIBLE);
			btnLeaveUser.setVisibility(View.VISIBLE);
		}

		return v;
	}

	public void findViewById(View v) {
		tvBtnOffline = (TextView) v.findViewById(R.id.tvBtnOffline1);
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
			joinTrip(Global.getPreference(getActivity(), Global.TRIP_TRIP_ID, "id"));
			break;
		case R.id.btnLeave1:
			btnJoinUser.setVisibility(View.VISIBLE);
			btnLeaveUser.setVisibility(View.INVISIBLE);
			leaveTrip(Global.getPreference(getActivity(), Global.TRIP_TRIP_ID, "id"));
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

//	public void LoadDataFromServer() {
//		Log.d("adasd", "đang load...");
//		AsyncHttpClient client = new AsyncHttpClient();
//		client.get(Global.BASE_URI + "/" + Global.URI_TRIP_GET_TRIP + "?id="
//				+ Global.getPreference(getActivity(), Global.TRIP_TRIP_ID, ""),
//				new AsyncHttpResponseHandler() {
//					public void onSuccess(String response) {
//						Log.e("DATA", response);
//						executeWhenSendDataSuccess(response);
//
//					}
//
//					@Override
//					public void onFailure(int statusCode, Throwable error,
//							String content) {
//						Log.d("Lỗi rồi", content + statusCode);
//						switch (statusCode) {
//						case 400:
//							Toast.makeText(getActivity(),
//									getResources().getString(R.string.e400),
//									Toast.LENGTH_LONG).show();
//							break;
//						case 403:
//							Toast.makeText(getActivity(),
//									getResources().getString(R.string.e403),
//									Toast.LENGTH_LONG).show();
//							break;
//						case 404:
//							Toast.makeText(getActivity(),
//									getResources().getString(R.string.e404),
//									Toast.LENGTH_LONG).show();
//							break;
//						case 503:
//							Toast.makeText(getActivity(),
//									getResources().getString(R.string.e503),
//									Toast.LENGTH_LONG).show();
//							break;
//						default:
//							break;
//						}
//					}
//				});
//	}
	public void LoadDataFromServer() {
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(Global.BASE_URI + "/" + Global.URI_TRIP_GET_TRIP + "?id="
				+ Global.getPreference(getActivity(), Global.TRIP_TRIP_ID, ""),
				new AsyncHttpResponseHandler() {
					public void onSuccess(String response) {
						Log.e("DATA", response);
						executeWhenSendDataSuccess(response);

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
	private void executeWhenSendDataSuccess(String response) {
		JSONObject item;
		try {
			item = new JSONObject(response);
			String _id = item.optString("_id");
			String name = item.optString("name");
			String diemBatdau = item.optJSONObject("begin_location").optString(
					"name");
			String diemKetthuc = item.optJSONObject("end_location").optString(
					"name");
			String tgBatdau = item.optString("start_date");
			String tgKetthuc = item.optString("end_date");
			String admin = "";
			String checkJoin = "";
			
			if (!item.optString("created_by").equals("")) {
				admin = item.getJSONObject("created_by").optString("fullname");
				checkJoin = item.getJSONObject("created_by").optString(
						"username");
				
				Global.savePreference(getActivity(), Global.USER_CREATEBY_TRIP,
						item.getJSONObject("created_by").optString("_id"));
			}
			String chiphicanhan = item.optString("expense", "0");
			int chiphicanhans = Integer.parseInt(chiphicanhan);
			String thoigian_xuatphat = item.optString("gathering_time");
			String diadiem_xuatphat = item.optString("gathering_position");
			String note = item.optString("note");
			String image = item.optString("image");

			Lichtrinh dataTrip = new Lichtrinh(_id, name, diemBatdau,
					diemKetthuc, tgBatdau, tgKetthuc, "1", "1", "1", admin, "",
					"", chiphicanhans, 0, "", image, diadiem_xuatphat,
					thoigian_xuatphat, note);
			if (Global.getPreference(getActivity(), Global.USER_USERNAME, " ")
					.equalsIgnoreCase(checkJoin)) {
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
			Global.savePreference(getActivity(), Global.TRIP_MONEY, dataTrip.getChiphicanhan() + "");
			tvNotes.setText(dataTrip.getNote());
			File f = ImageUltiFunctions.getFileFromUri(Global.getURI(new MD5()
					.getMD5(dataTrip.getImage())));
			if (f != null) {
				Bitmap b = ImageUltiFunctions.decodeSampledBitmapFromFile(f,
						500, 500);
				ivTripBG.setImageBitmap(b);
			} else {
				ivTripBG.setImageResource(R.drawable.default_bg_detail);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public void joinTrip(String _idTrip){
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("id", _idTrip);
		client.post(Global.BASE_URI + "/" + Global.TRIP_REGISTER+"?access_token="+Global.getPreference(getActivity(), Global.ACCESS_TOKEN, " "),params,
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
	public void leaveTrip(String _idTrip){
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("id", _idTrip);
		client.post(Global.BASE_URI + "/" + Global.TRIP_LEAVE+"?access_token="+Global.getPreference(getActivity(), Global.ACCESS_TOKEN, " "),params,
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
