package com.hou.adapters;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import com.hou.app.Global;
import com.hou.dulibu.R;
import com.hou.model.Lichtrinh;
import com.hou.model.LichtrinhMember;
import com.hou.model.Lichtrinh_User;
import com.hou.ultis.CircularImageView;
import com.hou.ultis.ImageUltiFunctions;
import com.hou.upload.MD5;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MemberAdapter extends ArrayAdapter<LichtrinhMember> {
	Activity context = null;
	ArrayList<LichtrinhMember> myArray = null;
	int layoutId;

	public MemberAdapter (Activity context, int layoutId,
			ArrayList<LichtrinhMember> arr) {
		super(context, layoutId, arr);
		this.context = context;
		this.layoutId = layoutId;
		this.myArray = arr;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = context.getLayoutInflater();
		convertView = inflater.inflate(layoutId, null);

		if (myArray.size() > 0 && position >= 0) {

			final CircularImageView iv_avatar = (CircularImageView) convertView
					.findViewById(R.id.iv_avarta);
			final TextView tv_fullname = (TextView) convertView
					.findViewById(R.id.tv_fullname);
			final ImageButton imgRole = (ImageButton) convertView.findViewById(R.id.imgRole);
			final LichtrinhMember member = myArray.get(position);

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
//			if (member.getQuyen().equals(Global.USER_ROLE_USER)) {
//				imgRole.setImageResource(R.drawable.icon_add_user);
//			}
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

		return convertView;

	}
}
