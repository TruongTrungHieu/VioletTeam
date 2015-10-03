package com.hou.adapters;

import java.util.ArrayList;


import com.hou.dulibu.R;
import com.hou.dulibu.R.id;
import com.hou.model.Chitieu;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class KhoanChiArrayAdapter extends ArrayAdapter<Chitieu> {

	Activity context = null;
	ArrayList<Chitieu> myArray = null;
	int layoutId;

	public KhoanChiArrayAdapter(Activity context, int layoutId,
			ArrayList<Chitieu> arr) {
		super(context, layoutId, arr);
		this.context = context;
		this.layoutId = layoutId;
		this.myArray = arr;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

//		LayoutInflater inflater = context.getLayoutInflater();
//		convertView = inflater.inflate(layoutId, null);
//
//		if (myArray.size() > 0 && position >= 0) {
//
//			final TextView txtTenKhoanChi = (TextView) convertView
//					.findViewById(R.id.txtTenKhoanChi);
//			final TextView txtSoTien = (TextView) convertView
//					.findViewById(R.id.txtSoTien);
//
//			final Chitieu kc = myArray.get(position);
//
//			txtTenKhoanChi.setText(kc.getTenChitieu());
//
//			txtSoTien.setText(kc.getSotien()+"");
//		}

		return convertView;

	}
}
