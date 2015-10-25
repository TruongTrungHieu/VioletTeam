package com.hou.fragment;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.hou.adapters.MemberAdapterForAdmin;
import com.hou.adapters.MemberStartAdapter;
import com.hou.app.Global;
import com.hou.dulibu.ChiTieu_Activity;
import com.hou.dulibu.R;
import com.hou.dulibu.UserSecureConfirmManager;
import com.hou.fragment.ListTripFragment.getImage;
import com.hou.model.LichtrinhMember;
import com.hou.model.Lichtrinh_User;
import com.hou.upload.MD5;
import com.hou.upload.imageOnServer;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class TripDetailMemberActivity extends Fragment {
	// final Activity context = this;

	ArrayList<LichtrinhMember> arrListMember = new ArrayList<LichtrinhMember>();
	ArrayList<LichtrinhMember> arrListUsers = new ArrayList<LichtrinhMember>();
	ListView lvMember = null;
	int trangthai;
	MemberStartAdapter adapter;
	MemberAdapterForAdmin adapterForAdmin;
	private boolean checkAdmin = false;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater
				.inflate(R.layout.trip_detail_member, container, false);
		findViewById(v);
		trangthai = 1;
		arrListMember = new ArrayList<LichtrinhMember>();
		arrListUsers = new ArrayList<LichtrinhMember>();
		adapter = new MemberStartAdapter(getActivity(),
				R.layout.trip_detail_member_after_start, arrListMember);
		adapterForAdmin = new MemberAdapterForAdmin(getActivity(),
				R.layout.trip_detail_member_after_start, arrListUsers);
		getTripMember(Global.getPreference(getActivity(), Global.TRIP_TRIP_ID,
				""));

		return v;
	}

	public void findViewById(View v) {
		lvMember = (ListView) v.findViewById(R.id.lv_member);
	}

	private void getTripMember(String tripId) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("id", tripId);
		params.put("access_token", Global.getPreference(getActivity(),
				Global.USER_ACCESS_TOKEN, ""));

		client.get(Global.BASE_URI + "/" + Global.URI_GETLISTMEMBER_PATH,
				params, new AsyncHttpResponseHandler() {
					public void onSuccess(String response) {
						Log.e("getTripServer", response);
						listMember(response);
						
						if (checkAdmin) {
							lvMember.setAdapter(adapterForAdmin);
						}else {
							lvMember.setAdapter(adapter);
						}
					}

					@Override
					public void onFailure(int statusCode, Throwable error,
							String content) {
						Log.e("load member false", content);

					}
				});
	}

	public class getImage extends AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				try {
					imageOnServer.downloadFileFromServer(
							(new MD5()).getMD5(params[0]), params[0]);
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

	}

	private void listMember(String response) {

		try {
			JSONArray arrObj = new JSONArray(response);
			Log.e("StringResponse", response);
			for (int i = 0; i < arrObj.length(); i++) {
				JSONObject memberTripJson = arrObj.getJSONObject(i);

				String _id = memberTripJson.optString("_id", "id");
				String fullname = memberTripJson.optString("fullname","fullname");
				String role = memberTripJson.optString("role","role");
				String avatar = memberTripJson.optString("avatar","ava");

				LichtrinhMember lu = new LichtrinhMember();
				lu.setMaUser(_id);
				lu.setTenUser(fullname);
				lu.setQuyen(role);

				getImage img = new getImage();
				img.execute(avatar);
				lu.setImage(avatar);
				arrListUsers.add(lu);
				arrListMember.add(lu);
				if (_id.equals(Global.getPreference(getActivity(), Global.USER_MAUSER, "ma_user")) && role.equals(Global.USER_ROLE_ADMIN)) {
					checkAdmin = true;
				}
				Log.d("memberLog", fullname);
			}
			for (int i = 0; i < arrListMember.size(); i++) {
				if (arrListMember.get(i).getQuyen()
						.equals(Global.USER_ROLE_USER)) {
					arrListMember.remove(i);
				}
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}