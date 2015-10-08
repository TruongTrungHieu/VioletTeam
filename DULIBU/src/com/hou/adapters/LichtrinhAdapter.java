package com.hou.adapters;

import java.util.ArrayList;

import com.hou.dulibu.R;
import com.hou.model.Lichtrinh;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LichtrinhAdapter extends ArrayAdapter<Lichtrinh>{

	Activity context = null;
	ArrayList<Lichtrinh> myArray = null;
	int layoutId;

	public LichtrinhAdapter(Activity context, int layoutId, ArrayList<Lichtrinh> arr) {
		super(context, layoutId, arr);
		this.context = context;
		this.layoutId = layoutId;
		this.myArray = arr;
	}
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = context.getLayoutInflater();
		convertView = inflater.inflate(layoutId, null);
		// 
		if (myArray.size() > 0 && position >= 0) {
			
			final TextView txtTripTitle = (TextView) convertView.findViewById(R.id.titleTrip);
			final TextView txtTripTimeBegin = (TextView) convertView.findViewById(R.id.tvTimeBeginTrip);
			final TextView txtTripTimeEnd = (TextView) convertView.findViewById(R.id.tvTimeEndTrip);
			//final TextView txtTripNumberMember = (TextView) convertView.findViewById(R.id.tvCountMember);
			final TextView txtBeginPoint = (TextView) convertView.findViewById(R.id.tvBeginPoint);
			final TextView txtEndPoint = (TextView) convertView.findViewById(R.id.tvEndPoint);
			
			final Lichtrinh sTrip = myArray.get(position);

			txtTripTitle.setText(sTrip.getTenLichtrinh().toString());
			txtTripTimeBegin.setText(sTrip.getTbBatdau().toString());
			txtTripTimeEnd.setText(sTrip.getTgKetthuc().toString());
			txtBeginPoint.setText(sTrip.getDiemBatdau().toString());
			txtEndPoint.setText(sTrip.getDiemKetthuc().toString());
			//txtTripNumberMember.setText(sTrip.getTripNumberMember().toString());
			
			final ImageView imgTrip = (ImageView) convertView.findViewById(R.id.imgTrip);
			
			Resources res = context.getResources();
			String imgName = "" + sTrip.getMaLichtrinh();
			int resID = res.getIdentifier(imgName, "drawable", context.getPackageName());
			Drawable drawable = res.getDrawable(resID);
			imgTrip.setImageDrawable(drawable);
		}

		return convertView;
	}
}