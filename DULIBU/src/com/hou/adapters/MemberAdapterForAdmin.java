package com.hou.adapters;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import org.apache.http.Header;
import org.apache.http.HttpResponse;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.hou.app.Global;
import com.hou.dulibu.R;
import com.hou.fragment.TripDetailMemberActivity;
import com.hou.model.Chitieu;
import com.hou.model.LichtrinhMember;
import com.hou.model.Lichtrinh_User;
import com.hou.ultis.CircularImageView;
import com.hou.ultis.ImageUltiFunctions;
import com.hou.upload.MD5;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

public class MemberAdapterForAdmin extends ArrayAdapter<LichtrinhMember> {
	Activity context = null;
	ArrayList<LichtrinhMember> myArray = null;
	int layoutId;
	CircularImageView iv_avatar;
	TextView tv_fullname;
	ImageButton imgRole;
	LichtrinhMember member;

	public MemberAdapterForAdmin(Activity context, int layoutId,
			ArrayList<LichtrinhMember> arr) {
		super(context, layoutId, arr);
		this.context = context;
		this.layoutId = layoutId;
		this.myArray = arr;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = context.getLayoutInflater();
		convertView = inflater.inflate(layoutId, null);

		iv_avatar = (CircularImageView) convertView
				.findViewById(R.id.iv_avarta);
		tv_fullname = (TextView) convertView.findViewById(R.id.tv_fullname);
		imgRole = (ImageButton) convertView.findViewById(R.id.imgRole);
		member = myArray.get(position);

		if (myArray.size() > 0 && position >= 0) {

			tv_fullname.setText(member.getTenUser());

			try {
				File f = ImageUltiFunctions.getFileFromUri(Global
						.getURI(new MD5().getMD5(member.getImage())));
				if (f != null) {
					Bitmap b = ImageUltiFunctions.decodeSampledBitmapFromFile(
							f, 500, 500);
					iv_avatar.setImageBitmap(b);
				} else {
					iv_avatar.setImageResource(R.drawable.trip1);
				}
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (member.getQuyen().equals(Global.USER_ROLE_USER)) {
				imgRole.setImageResource(R.drawable.icon_add_user);
				imgRole.setTag(position);

			}
			if (member.getQuyen().equals(Global.USER_ROLE_ADMIN)) {
				imgRole.setImageResource(R.drawable.icon_admin);
				imgRole.setEnabled(false);
			}
			if (member.getQuyen().equals(Global.USER_ROLE_DAN_DOAN)) {
				imgRole.setImageResource(R.drawable.icon_flag);
				imgRole.setEnabled(false);
			}
			if (member.getQuyen().equals(Global.USER_ROLE_THU_QUY)) {
				imgRole.setImageResource(R.drawable.icon_bill);
				imgRole.setEnabled(false);
			}
		}
		imgRole.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("click add user", "bạn vừa click!");
				AsyncHttpClient client = new AsyncHttpClient();
				RequestParams params = new RequestParams();
				params.put("id", Global.getPreference(context,
						Global.TRIP_TRIP_ID, "id"));
				params.put("user_id", member.getMaUser());
				params.put("role", Global.USER_ROLE_MEMBER);

				client.post(
						Global.BASE_URI
								+ "/"
								+ Global.TRIP_ROLE
								+ "?access_token="
								+ Global.getPreference(context,
										Global.USER_ACCESS_TOKEN, "access_token"),
						params, new AsyncHttpResponseHandler() {
							public void onSuccess(String response) {
								Log.e("change_role", response);

							}

							@Override
							public void onFailure(int statusCode,
									Throwable error, String content) {
								Log.e("load member false", content);

							}
						});
				imgRole.setImageDrawable(null);
				imgRole.setEnabled(false);
			}
		});

		return convertView;

	}

	public void ChangeRoleUser() {
		Log.e("CHeck", "Check success!");
	}

}
