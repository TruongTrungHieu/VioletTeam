package com.hou.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hou.dulibu.R;
import com.hou.fragment.TripDetailMemberActivity;
import com.hou.model.Chitieu;
import com.hou.model.Lichtrinh_User;

public class MemberAdapter extends ArrayAdapter<Lichtrinh_User> {

	Activity context = null;
	ArrayList<Lichtrinh_User> myArray = null;
	int layoutId;

	public MemberAdapter(Activity context, int layoutId,
			ArrayList<Lichtrinh_User> arr) {
		super(context, layoutId, arr);
		this.context = context;
		this.layoutId = layoutId;
		this.myArray = arr;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = context.getLayoutInflater();
		convertView = inflater.inflate(layoutId, null);

		if (myArray.size() > 0 && position >= 0) {

			final ImageView iv_avatar = (ImageView) convertView
					.findViewById(R.id.iv_avarta);
			final TextView tv_fullname = (TextView) convertView
					.findViewById(R.id.tv_fullname);
			final TextView tv_statut = (TextView) convertView
					.findViewById(R.id.tv_statut);
			final TextView tv_saveornot = (TextView) convertView
					.findViewById(R.id.tv_saveornot);

			final Lichtrinh_User member = myArray.get(position);

			tv_fullname.setText(member.getMaUser());
			tv_statut.setText(member.getTrangthai_ketnoi());
			tv_saveornot.setText(member.getTrangthai_antoan());
		}

		return convertView;

	}
}
